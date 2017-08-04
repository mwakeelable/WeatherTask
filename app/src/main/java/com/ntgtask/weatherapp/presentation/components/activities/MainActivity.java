package com.ntgtask.weatherapp.presentation.components.activities;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ntgtask.weatherapp.R;
import com.ntgtask.weatherapp.data.entities.WeatherResponse;
import com.ntgtask.weatherapp.presentation.components.AppController;
import com.ntgtask.weatherapp.presentation.components.UnitConverter;
import com.ntgtask.weatherapp.presentation.presenter.MainPresenter;
import com.ntgtask.weatherapp.presentation.view.IMainView;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivity extends BaseActivity implements IMainView, LocationListener {
    ActionBar mActionBar;
    Toolbar mToolbar;
    TextView todayTemperature, todayDescription, todayWind, todayPressure, city;
    MainPresenter presenter;
    Location location;
    double latitude;
    double longitude;
    private String provider_info;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private static final long NO_UPDATE_REQUIRED_THRESHOLD = 14400000;
    private static final int PERMISSION_REQUEST_CODE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        todayTemperature = (TextView) findViewById(R.id.todayTemperature);
        todayDescription = (TextView) findViewById(R.id.todayDescription);
        todayWind = (TextView) findViewById(R.id.todayWind);
        todayPressure = (TextView) findViewById(R.id.todayPressure);
        city = (TextView) findViewById(R.id.city);
        //Iniltalize Presenter
        presenter = new MainPresenter(this);
        if (shouldUpdate())
            presenter.beginCheckGPS();
        else
            getSavedData();
//        presenter.refresh(latitude, longitude);

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE_LOCATION) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Access fine location permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Access fine location permission denied", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void checkGPSAvailable() {
        checkServices();
    }

    @Override
    public void onDataFetched(WeatherResponse weather) {
        updateUI(weather);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private boolean shouldUpdate() {
        long lastUpdate = AppController.getInstance().getLastTimeCheck();
        return lastUpdate < 0 || (Calendar.getInstance().getTimeInMillis() - lastUpdate) > NO_UPDATE_REQUIRED_THRESHOLD;
    }

    private void getCurrentLatLong() {
        double currentLat = getLatitude();
        double currentLong = getLongitude();
        Log.d(AppController.TAG, "Lat = " + String.valueOf(currentLat) + " & Long = " + String.valueOf(currentLong));
    }

    public void checkServices() {
        //Check Android Version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Check Location Permission not granted
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //Allow location permission message
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE_LOCATION);
                //user chose don't show again
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //Alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Please Access Location Permission");
                    builder.show();
                }
                //location permission is granted
            } else {
                LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                //if gps not enabled in device
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //message to enable gps
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("GPS not found");  // GPS not found
                    builder.setMessage("Want to enable?"); // Want to enable?
                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //open gps activity in android to enable it
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
                    builder.setNegativeButton("no", null);
                    builder.create().show();
                    return;
                    //android is over 6 and location permission enabled and gps enabled
                } else {
                    //get current location
                    provider_info = LocationManager.GPS_PROVIDER;
                    locationManager.requestLocationUpdates(
                            provider_info,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES,
                            this
                    );

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(provider_info);
                        updateGPSCoordinates();
                    }
                    getCurrentLatLong();
                    presenter.refresh(latitude, longitude);
                }
            }
        }
        //android is lower 6
        else {
            LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            //if gps not enabled in device
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //message to enable gps
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("GPS not found");  // GPS not found
                builder.setMessage("Want to enable?"); // Want to enable?
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //open gps activity in android to enable it
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                });
                builder.setNegativeButton("no", null);
                builder.create().show();
                return;

            }
            //android is lower than 6 and gps is enabled
            else {
                //get current location
                provider_info = LocationManager.GPS_PROVIDER;
                locationManager.requestLocationUpdates(
                        provider_info,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES,
                        this
                );

                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(provider_info);
                    updateGPSCoordinates();
                }
                getCurrentLatLong();
                presenter.refresh(latitude, longitude);
            }
        }
    }

    public void updateGPSCoordinates() {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkServices();
        presenter.refresh(latitude, longitude);
    }
}
