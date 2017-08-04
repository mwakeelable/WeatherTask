package com.ntgtask.weatherapp.presentation.components;

public class UnitConverter {
    public static float convertTemperature(float temperature) {
        return UnitConverter.kelvinToCelsius(temperature);
    }

    public static float kelvinToCelsius(float kelvinTemp) {
        return kelvinTemp - 273.15f;
    }

    public static double convertWind(float wind) {
        return wind * 3.6;
    }
}
