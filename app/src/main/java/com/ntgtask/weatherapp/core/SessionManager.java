package com.ntgtask.weatherapp.core;

import android.content.Context;
import android.content.SharedPreferences;

import com.ntgtask.weatherapp.AppController;


public class SessionManager {
    public SharedPreferences pref;
    public SharedPreferences.Editor editor;
    public Context mContext;
    public int PRIVATE_MODE = 0;
    public final String PREF_NAME = AppController.TAG;
    public final String KEY_LAST_TIME_CHECK = "lastTimeCheck";


    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLastTimeCheck(long time) {
        editor.putLong(KEY_LAST_TIME_CHECK, time);
        editor.commit();
    }

    public long getLastTimeCheck() {
        return pref.getLong(KEY_LAST_TIME_CHECK, 0);
    }
}
