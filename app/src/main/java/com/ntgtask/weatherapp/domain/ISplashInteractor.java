package com.ntgtask.weatherapp.domain;

import com.ntgtask.weatherapp.domain.interfaces.OnGPSAllowed;
import com.ntgtask.weatherapp.domain.interfaces.OnSplashTimeFinishedListener;

public interface ISplashInteractor {
    void validateGPSIsAllowed(OnGPSAllowed gpsAllowed);

    void validateTimeFinished(OnSplashTimeFinishedListener listener);
}
