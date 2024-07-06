package com.example.getWeatherDataByIpAddressOfUser.controller;


import com.example.getWeatherDataByIpAddressOfUser.service.IpService;
import com.example.getWeatherDataByIpAddressOfUser.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private IpService ipService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("api/weather")
    public String getWeather() {
        try {
            // Get IP address
            String ipAddress = ipService.getIpAddress();

            // Get location details
            JsonNode locationJson = ipService.getLocation(ipAddress);
            double latitude = locationJson.get("latitude").asDouble();
            double longitude = locationJson.get("longitude").asDouble();

            // Get weather data
            return weatherService.getWeather(latitude, longitude);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
