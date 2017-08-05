package com.ntgtask.weatherapp.domain;

import android.os.Handler;

import com.ntgtask.weatherapp.domain.interfaces.OnGPSAllowed;
import com.ntgtask.weatherapp.domain.interfaces.OnSplashTimeFinishedListener;

public class SplashInteractor implements ISplashInteractor {
    int SPLASH_TIME_OUT = 1000;

    @Override
    public void validateGPSIsAllowed(OnGPSAllowed gpsAllowed) {
        gpsAllowed.getCurrentLatLong();
    }

    @Override
    public void validateTimeFinished(final OnSplashTimeFinishedListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listener.goHome();
            }
        }, SPLASH_TIME_OUT);
    }
}
