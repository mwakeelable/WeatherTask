package com.ntgtask.weatherapp.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.ntgtask.weatherapp.data.entities.WeatherResponse;
import com.ntgtask.weatherapp.data.network.ApiInterface;
import com.ntgtask.weatherapp.data.network.ApiUtils;
import com.ntgtask.weatherapp.presentation.components.AppController;
import com.ntgtask.weatherapp.presentation.components.Constants;

import java.util.Calendar;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherService extends IntentService {
    public WeatherService() {
        super("WeatherService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Broadcast", "Calls");
        HashMap<String, String> latLong = AppController.getInstance().getLatLong();
        double latitude, longitude;
        latitude = Double.parseDouble(latLong.get("lat"));
        longitude = Double.parseDouble(latLong.get("lon"));
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
}
