package com.ntgtask.weatherapp.ui.main;

public interface IMainPresenter {
    void beginCheckGPS();

    void refresh(double latitude, double longitude);
}
