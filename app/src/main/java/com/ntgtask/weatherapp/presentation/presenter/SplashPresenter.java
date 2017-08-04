package com.ntgtask.weatherapp.presentation.presenter;


import com.ntgtask.weatherapp.domain.interfaces.OnSplashTimeFinishedListener;
import com.ntgtask.weatherapp.presentation.view.ISplashView;
import com.ntgtask.weatherapp.domain.SplashInteractor;

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
