package com.ntgtask.weatherapp.domain;

import com.ntgtask.weatherapp.domain.interfaces.OnDataFetched;
import com.ntgtask.weatherapp.domain.interfaces.OnGPSAllowed;
import com.ntgtask.weatherapp.domain.interfaces.RepeatService;

public interface IMainInteractor {
    void fillUiWithData(OnDataFetched dataFetched);

    void repeatService(RepeatService repeatService);
}
