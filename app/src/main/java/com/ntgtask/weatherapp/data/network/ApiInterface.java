package com.ntgtask.weatherapp.data.network;


import com.ntgtask.weatherapp.data.entities.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET(ApiEndPoint.GET_DATA)
    Call<WeatherResponse> getWeatherStatus(
            @Query("lat") double latitude,
            @Query("lon") double longitude,
            @Query("appid") String appID);
}
