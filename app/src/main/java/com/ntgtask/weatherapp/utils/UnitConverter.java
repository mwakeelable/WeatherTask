package com.ntgtask.weatherapp.utils;

public class UnitConverter {
    public static float convertTemperature(float temperature) {
        return UnitConverter.kelvinToCelsius(temperature);
    }

    public static float kelvinToCelsius(float kelvinTemp) {
        return kelvinTemp - 273.15f;
    }

    public static double convertWind(double wind) {
        return wind * 3.6;
    }
}
