package com.ntgtask.weatherapp.presentation.view;

import com.ntgtask.weatherapp.data.entities.WeatherResponse;

public interface IMainView {
    void checkGPSAvailable();

    void onDataFetched(WeatherResponse weather);
}
