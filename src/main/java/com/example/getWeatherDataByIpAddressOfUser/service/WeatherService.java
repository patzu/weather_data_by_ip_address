package com.example.getWeatherDataByIpAddressOfUser.service;

import com.example.getWeatherDataByIpAddressOfUser.exception.WeatherDataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    public JsonNode getWeather(double latitude, double longitude) {
        String weatherUrl = String.format(
                "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s",
                latitude, longitude, apiKey
        );
        try {
            logger.info("Fetching weather data by latitude: {}, longitude: {}", latitude, longitude);
            String weatherResponse = restTemplate.getForObject(weatherUrl, String.class);
            return objectMapper.readTree(weatherResponse);
        } catch (JsonProcessingException e) {
            logger.error("Error processing weather data! {}", e.getMessage(), e);
            throw new WeatherDataException("Error processing weather data!", e);
        } catch (Exception e) {
            logger.error("Error fetching weather data! {}", e.getMessage(), e);
            throw new WeatherDataException("Error fetching weather data!", e);
        }
    }
}