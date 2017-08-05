package com.ntgtask.weatherapp.presentation.view;

import com.ntgtask.weatherapp.data.entities.WeatherResponse;

public interface IMainView {
    void onDataFetched(WeatherResponse weather);

    void scheduleRepeatService();
}
