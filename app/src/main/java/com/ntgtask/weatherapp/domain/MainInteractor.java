package com.ntgtask.weatherapp.domain;


import com.ntgtask.weatherapp.domain.interfaces.OnDataFetched;
import com.ntgtask.weatherapp.domain.interfaces.OnGPSAllowed;
import com.ntgtask.weatherapp.domain.interfaces.RepeatService;

public class MainInteractor implements IMainInteractor {

    @Override
    public void fillUiWithData(OnDataFetched dataFetched) {
        dataFetched.setDataFetched();
    }

    @Override
    public void repeatService(RepeatService repeatService) {

    }
}
