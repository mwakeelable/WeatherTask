package com.ntgtask.weatherapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ntgtask.weatherapp.R;
import com.ntgtask.weatherapp.service.WeatherService;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        startService();
    }

    public void startService() {
        startService(new Intent(getBaseContext(), WeatherService.class));
    }
}
