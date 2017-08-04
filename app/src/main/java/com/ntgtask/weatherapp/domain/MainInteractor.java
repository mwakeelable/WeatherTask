package com.ntgtask.weatherapp.domain;


import com.ntgtask.weatherapp.domain.interfaces.OnDataFetched;
import com.ntgtask.weatherapp.domain.interfaces.OnGPSAllowed;

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
