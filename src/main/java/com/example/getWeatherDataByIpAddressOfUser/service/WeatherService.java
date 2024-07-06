package com.example.getWeatherDataByIpAddressOfUser.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Value("${openweathermap.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getWeather(double latitude, double longitude) {
        String weatherUrl = String.format(
                "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s",
                latitude, longitude, apiKey
        );
        return restTemplate.getForObject(weatherUrl, String.class);
    }
}
