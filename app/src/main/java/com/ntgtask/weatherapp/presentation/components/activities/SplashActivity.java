package com.ntgtask.weatherapp.presentation.components.activities;

import android.Manifest;
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
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.ntgtask.weatherapp.R;
import com.ntgtask.weatherapp.presentation.components.AppController;
import com.ntgtask.weatherapp.presentation.presenter.SplashPresenter;
import com.ntgtask.weatherapp.presentation.view.ISplashView;

public class SplashActivity extends BaseActivity implements ISplashView, LocationListener {
    SplashPresenter presenter;
    private String provider_info;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;
    private static final int PERMISSION_REQUEST_CODE_LOCATION = 1;
    Location location;
    double latitude;
    double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenter(this);
        presenter.onSplashTimeFinished();
        presenter.beginCheckGPS();
    }

    @Override
    public void checkGPSAvailable() {
        checkServices();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.splash_activity;
    }

    public void checkServices() {
        //Check Android Version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Check Location Permission not granted
            if (ContextCompat.checkSelfPermission(SplashActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                //Allow location permission message
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE_LOCATION);
                //user chose don't show again
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    //Alert dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
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
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra("lat",latitude);
                    intent.putExtra("long",longitude);
                    startActivity(intent);
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
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("long",longitude);
                startActivity(intent);
            }
        }
    }

    public void updateGPSCoordinates() {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }
    }

    private void getCurrentLatLong() {
        double currentLat = getLatitude();
        double currentLong = getLongitude();
        AppController.getInstance().storeLatLong(String.valueOf(currentLat), String.valueOf(currentLong));
        Log.d(AppController.TAG, "Lat = " + String.valueOf(currentLat) + " & Long = " + String.valueOf(currentLong));
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE_LOCATION) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
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
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra("lat",latitude);
                intent.putExtra("long",longitude);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Access fine location permission denied", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
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
}
