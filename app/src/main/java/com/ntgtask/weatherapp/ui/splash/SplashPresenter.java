package com.ntgtask.weatherapp.ui.splash;


import com.ntgtask.weatherapp.interfaces.OnSplashTimeFinishedListener;

public class SplashPresenter implements ISplashPresenter, OnSplashTimeFinishedListener {
    private ISplashView splashView;
    private SplashInteractor interactor;

    public SplashPresenter(ISplashView splashView) {
        this.splashView = splashView;
        this.interactor = new SplashInteractor();
    }

    @Override
    public void onSplashTimeFinished() {
        interactor.validateTimeFinished(this);
    }

    @Override
    public void goHome() {
        splashView.navigateToMain();
    }
}
