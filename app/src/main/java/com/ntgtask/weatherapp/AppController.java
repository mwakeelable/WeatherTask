package com.ntgtask.weatherapp;

import android.app.Application;

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        startService(new Intent(getBaseContext(), WeatherService.class));
    }
}
