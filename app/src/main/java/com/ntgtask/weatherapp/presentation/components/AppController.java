package com.ntgtask.weatherapp.presentation.components;

import android.app.Application;
import android.content.SharedPreferences;

import java.util.HashMap;

public class AppController extends Application {
    public static final String TAG = AppController.class.getSimpleName();
    private static AppController mInstance;
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public int PRIVATE_MODE = 0;
    public final String PREF_NAME = TAG;
    public final String KEY_LAST_TIME_CHECK = "lastTimeCheck";
    public final String KEY_TEMP = "temp";
    public final String KEY_DESC = "desc";
    public final String KEY_WIND = "wind";
    public final String KEY_PRESSURE = "pressure";
    public final String KEY_NAME = "name";

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        pref = getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLastTimeCheck(long time) {
        editor.putLong(KEY_LAST_TIME_CHECK, time);
        editor.commit();
    }

    public void storeWeatherStatus(float temp, String desc, float wind, float pressure, String name) {
        editor.putFloat(KEY_TEMP, temp);
        editor.putString(KEY_DESC, desc);
        editor.putFloat(KEY_WIND, wind);
        editor.putFloat(KEY_PRESSURE, pressure);
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    public HashMap<String, String> getWeatherStatus() {
        HashMap<String, String> weather = new HashMap<>();
        weather.put(KEY_TEMP, String.valueOf(pref.getFloat(KEY_TEMP, 0)));
        weather.put(KEY_DESC, pref.getString(KEY_DESC, ""));
        weather.put(KEY_WIND, String.valueOf(pref.getFloat(KEY_WIND, 0)));
        weather.put(KEY_PRESSURE, String.valueOf(pref.getFloat(KEY_PRESSURE, 0)));
        weather.put(KEY_NAME, pref.getString(KEY_NAME, ""));
        return  weather;
    }

    public long getLastTimeCheck() {
        return pref.getLong(KEY_LAST_TIME_CHECK, -1);
    }
}
