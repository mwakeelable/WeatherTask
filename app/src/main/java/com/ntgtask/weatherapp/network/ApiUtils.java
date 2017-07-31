package com.ntgtask.weatherapp.network;

/**
 * Created by wakeel on 31/07/17.
 */

public class ApiUtils {
    public static ApiInterface getWeatherService() {
        return RetrofitClient.getWeather().create(ApiInterface.class);
    }
}
