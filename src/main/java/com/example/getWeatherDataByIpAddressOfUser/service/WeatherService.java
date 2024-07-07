package com.example.getWeatherDataByIpAddressOfUser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public WeatherService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Value("${openweathermap.api.key}")
    private String apiKey;


    public JsonNode getWeather(double latitude, double longitude) throws JsonProcessingException {
        String weatherUrl = String.format(
                "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s",
                latitude, longitude, apiKey
        );
        String weatherResponse = restTemplate.getForObject(weatherUrl, String.class);
        return objectMapper.readTree(weatherResponse);
    }
}
