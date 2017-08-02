package com.ntgtask.weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ntgtask.weatherapp.core.SessionManager;

public abstract class BaseActivity extends AppCompatActivity {
    public SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        session = new SessionManager(this);
    }

    protected abstract int getLayoutResourceId();

    public void openActivity(Class<?> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
