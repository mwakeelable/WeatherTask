package com.ntgtask.weatherapp.ui.main;

import android.util.Log;

import com.ntgtask.weatherapp.interfaces.OnDataFetched;
import com.ntgtask.weatherapp.interfaces.OnGPSAllowed;
import com.ntgtask.weatherapp.model.Weather;
import com.ntgtask.weatherapp.model.WeatherResponse;
import com.ntgtask.weatherapp.network.ApiInterface;
import com.ntgtask.weatherapp.network.ApiUtils;
import com.ntgtask.weatherapp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements IMainPresenter, OnGPSAllowed, OnDataFetched {
    private IMainView mainView;
    private IMainInteractor mainInteractor;

    public MainPresenter(IMainView view) {
        this.mainView = view;
        this.mainInteractor = new MainInteractor();
    }

    @Override
    public void getCurrentLatLong() {
        mainView.checkGPSAvailable();
    }

    @Override
    public void beginCheckGPS() {
        mainInteractor.validateGPSIsAllowed(this);
    }

    @Override
    public void refresh(double latitude, double longitude) {
        ApiInterface apiInterface = ApiUtils.getWeatherService();
        apiInterface.getWeatherStatus(latitude, longitude, Constants.ApiKey).enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                WeatherResponse weather = new WeatherResponse();
                for (int i = 0; i < response.body().getWeather().size(); i++) {
                    weather.setWeather(response.body().getWeather());
                    weather.setMain(response.body().getMain());
                    weather.setWind(response.body().getWind());
                    weather.setName(response.body().getName());
                }
                mainView.onDataFetched(weather);
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    @Override
    public void setDataFetched() {
        mainInteractor.fillUiWithData(this);
    }
}
