package com.ntgtask.weatherapp.ui.splash;

import android.os.Bundle;

import com.ntgtask.weatherapp.R;
import com.ntgtask.weatherapp.ui.BaseActivity;
import com.ntgtask.weatherapp.ui.main.MainActivity;

public class SplashActivity extends BaseActivity implements ISplashView {
    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashPresenter(this);
        presenter.onSplashTimeFinished();
    }

    @Override
    public void navigateToMain() {
        openActivity(MainActivity.class);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.splash_activity;
    }
}
