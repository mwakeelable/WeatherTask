package com.ntgtask.weatherapp.presentation.presenter;

import android.util.Log;

import com.ntgtask.weatherapp.data.entities.WeatherResponse;
import com.ntgtask.weatherapp.data.network.ApiInterface;
import com.ntgtask.weatherapp.data.network.ApiUtils;
import com.ntgtask.weatherapp.domain.IMainInteractor;
import com.ntgtask.weatherapp.domain.MainInteractor;
import com.ntgtask.weatherapp.domain.interfaces.OnDataFetched;
import com.ntgtask.weatherapp.domain.interfaces.OnGPSAllowed;
import com.ntgtask.weatherapp.presentation.components.AppController;
import com.ntgtask.weatherapp.presentation.components.Constants;
import com.ntgtask.weatherapp.presentation.view.IMainView;

import java.util.Calendar;

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
                Calendar now = Calendar.getInstance();
                AppController.getInstance().setLastTimeCheck(now.getTimeInMillis());
                AppController.getInstance().storeWeatherStatus(
                        weather.getMain().getTemp(),
                        weather.getWeather().get(0).getDescription(),
                        weather.getWind().getSpeed(),
                        weather.getMain().getPressure(),
                        weather.getName());
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
