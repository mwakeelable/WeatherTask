package com.ntgtask.weatherapp.ui.main;


import com.ntgtask.weatherapp.interfaces.OnDataFetched;
import com.ntgtask.weatherapp.interfaces.OnGPSAllowed;

public class MainInteractor implements IMainInteractor {

    @Override
    public void validateGPSIsAllowed(OnGPSAllowed gpsAllowed) {
        gpsAllowed.getCurrentLatLong();
    }

    @Override
    public void fillUiWithData(OnDataFetched dataFetched) {
        dataFetched.setDataFetched();
    }
}
