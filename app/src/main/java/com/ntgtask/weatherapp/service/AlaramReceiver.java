package com.ntgtask.weatherapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class AlaramReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, WeatherService.class);
        i.putExtra("foo", "bar");
        context.startService(i);
    }
}
