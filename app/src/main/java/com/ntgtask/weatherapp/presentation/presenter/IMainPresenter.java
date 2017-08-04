package com.ntgtask.weatherapp.presentation.presenter;

public interface IMainPresenter {
    void beginCheckGPS();

    void refresh(double latitude, double longitude);
}
