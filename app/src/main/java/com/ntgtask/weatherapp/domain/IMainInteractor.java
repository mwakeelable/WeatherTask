package com.ntgtask.weatherapp.domain;

import com.ntgtask.weatherapp.domain.interfaces.OnDataFetched;
import com.ntgtask.weatherapp.domain.interfaces.OnGPSAllowed;

public interface IMainInteractor {
    void validateGPSIsAllowed(OnGPSAllowed gpsAllowed);

    void fillUiWithData(OnDataFetched dataFetched);
}
