package com.ntgtask.weatherapp.presentation.presenter;


import com.ntgtask.weatherapp.domain.SplashInteractor;
import com.ntgtask.weatherapp.domain.interfaces.OnGPSAllowed;
import com.ntgtask.weatherapp.domain.interfaces.OnSplashTimeFinishedListener;
import com.ntgtask.weatherapp.presentation.view.ISplashView;

public class SplashPresenter implements ISplashPresenter, OnSplashTimeFinishedListener, OnGPSAllowed {
    private ISplashView splashView;
    private SplashInteractor interactor;

    public SplashPresenter(ISplashView splashView) {
        this.splashView = splashView;
        this.interactor = new SplashInteractor();
    }

    @Override
    public void beginCheckGPS() {
        splashView.checkGPSAvailable();
    }

    @Override
    public void onSplashTimeFinished() {
        interactor.validateTimeFinished(this);
    }

    @Override
    public void goHome() {

    }

    @Override
    public void getCurrentLatLong() {
        interactor.validateGPSIsAllowed(this);
    }
}
