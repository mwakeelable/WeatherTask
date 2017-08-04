package com.ntgtask.weatherapp.domain;

import com.ntgtask.weatherapp.domain.interfaces.OnSplashTimeFinishedListener;

public interface ISplashInteractor {
    void validateTimeFinished(OnSplashTimeFinishedListener listener);
}
