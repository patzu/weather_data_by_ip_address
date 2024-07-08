package com.example.getWeatherDataByIpAddressOfUser.exception;

public class WeatherDataException extends RuntimeException {
    public WeatherDataException(String message) {
        super(message);
    }

    public WeatherDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
