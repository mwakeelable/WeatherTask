package com.ntgtask.weatherapp.ui.main;

import com.ntgtask.weatherapp.model.WeatherResponse;

public interface IMainView {
    void checkGPSAvailable();

    void onDataFetched(WeatherResponse weather);
}
