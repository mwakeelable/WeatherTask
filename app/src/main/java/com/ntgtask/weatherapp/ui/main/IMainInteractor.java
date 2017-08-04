package com.ntgtask.weatherapp.ui.main;

import com.ntgtask.weatherapp.interfaces.OnDataFetched;
import com.ntgtask.weatherapp.interfaces.OnGPSAllowed;

public interface IMainInteractor {
    void validateGPSIsAllowed(OnGPSAllowed gpsAllowed);

    void fillUiWithData(OnDataFetched dataFetched);
}
