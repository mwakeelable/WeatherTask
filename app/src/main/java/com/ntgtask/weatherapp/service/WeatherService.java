package com.ntgtask.weatherapp.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.ntgtask.weatherapp.R;
import com.ntgtask.weatherapp.core.SessionManager;
import com.ntgtask.weatherapp.model.Weather;
import com.ntgtask.weatherapp.model.WeatherResponse;
import com.ntgtask.weatherapp.network.ApiInterface;
import com.ntgtask.weatherapp.network.ApiUtils;
import com.ntgtask.weatherapp.ui.main.MainActivity;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherService extends Service {
    ApiInterface apiInterface = ApiUtils.getWeatherService();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getWeatherStatus();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void getWeatherStatus() {
        final SessionManager sessionManager = new SessionManager(getApplicationContext());
        apiInterface.getWeatherStatus(31.2001, 29.9187, getResources().getString(R.string.API_KEY)).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                Weather weather = new Weather();
                for (int i = 0; i < response.body().getWeather().size(); i++) {
                    weather.setDescription(response.body().getWeather().get(i).getDescription());
                }
                Log.d("Weather", weather.getDescription());
                Calendar now = Calendar.getInstance();
                sessionManager.setLastTimeCheck(now.getTimeInMillis());
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }
}
