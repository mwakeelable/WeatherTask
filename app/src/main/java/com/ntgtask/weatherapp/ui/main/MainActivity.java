package com.ntgtask.weatherapp.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.ntgtask.weatherapp.R;
import com.ntgtask.weatherapp.service.WeatherService;
import com.ntgtask.weatherapp.ui.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        startService(new Intent(getBaseContext(), WeatherService.class));
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.main_activity;
    }

}
