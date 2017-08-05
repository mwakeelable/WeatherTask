package com.ntgtask.weatherapp.presentation.components.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.ntgtask.weatherapp.R;
import com.ntgtask.weatherapp.data.entities.WeatherResponse;
import com.ntgtask.weatherapp.presentation.components.AppController;
import com.ntgtask.weatherapp.presentation.components.UnitConverter;
import com.ntgtask.weatherapp.presentation.presenter.MainPresenter;
import com.ntgtask.weatherapp.presentation.view.IMainView;
import com.ntgtask.weatherapp.service.AlaramReceiver;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends BaseActivity implements IMainView {
    ActionBar mActionBar;
    Toolbar mToolbar;
    TextView todayTemperature, todayDescription, todayWind, todayPressure, city;
    MainPresenter presenter;
    double latitude;
    double longitude;
    private static final long NO_UPDATE_REQUIRED_THRESHOLD = 14400000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            latitude = extras.getDouble("lat");
            longitude = extras.getDouble("long");
        }
        todayTemperature = (TextView) findViewById(R.id.todayTemperature);
        todayDescription = (TextView) findViewById(R.id.todayDescription);
        todayWind = (TextView) findViewById(R.id.todayWind);
        todayPressure = (TextView) findViewById(R.id.todayPressure);
        city = (TextView) findViewById(R.id.city);
        presenter = new MainPresenter(this);
        if (AppController.getInstance().getLastTimeCheck() == -1)
            presenter.fetchWeatherStatus(latitude, longitude);
        else
            getSavedData();
    }

    public void scheduleRepeat() {
        Intent intent = new Intent(getApplicationContext(), AlaramReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlaramReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = AppController.getInstance().getLastTimeCheck();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                firstMillis,
                NO_UPDATE_REQUIRED_THRESHOLD,
                pIntent);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.main_activity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void onDataFetched(WeatherResponse weather) {
        updateUI(weather);
    }

    @Override
    public void scheduleRepeatService() {
        scheduleRepeat();
    }

    private void updateUI(WeatherResponse weather) {
        city.setText(weather.getName());
        float temperature = UnitConverter.convertTemperature(Float.parseFloat(String.valueOf(weather.getMain().getTemp())));
        todayTemperature.setText(new DecimalFormat("0.#").format(temperature) + "°C");
        todayDescription.setText(weather.getWeather().get(0).getDescription());
        todayWind.setText("Wind: " + String.valueOf(UnitConverter.convertWind(weather.getWind().getSpeed())) + " m/s");
        todayPressure.setText("Pressure: " + String.valueOf(weather.getMain().getPressure()) + " hPa");
    }

    private void getSavedData() {
        HashMap<String, String> weatherData = AppController.getInstance().getWeatherStatus();
        city.setText(weatherData.get("name"));
        float temperature = UnitConverter.convertTemperature(Float.parseFloat(weatherData.get("temp")));
        todayTemperature.setText(new DecimalFormat("0.#").format(temperature) + "°C");
        todayDescription.setText(weatherData.get("desc"));
        todayWind.setText("Wind: " + String.valueOf(UnitConverter.convertWind(Float.parseFloat(weatherData.get("wind")))) + " m/s");
        todayPressure.setText("Pressure: " + weatherData.get("pressure") + " hPa");
    }
}
