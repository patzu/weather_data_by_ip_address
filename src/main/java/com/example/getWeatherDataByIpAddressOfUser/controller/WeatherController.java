package com.example.getWeatherDataByIpAddressOfUser.controller;


import com.example.getWeatherDataByIpAddressOfUser.model.ErrorResponseDto;
import com.example.getWeatherDataByIpAddressOfUser.model.WeatherDto;
import com.example.getWeatherDataByIpAddressOfUser.service.IpService;
import com.example.getWeatherDataByIpAddressOfUser.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @Autowired
    private IpService ipService;

    @Autowired
    private WeatherService weatherService;

    @GetMapping("api/weather")
    public ResponseEntity<?> getWeather() {
        try {

            // Get IP address
            String ipAddress = ipService.getIpAddress();

            // Get location details
            JsonNode locationJson = ipService.getLocation(ipAddress);
            String city = locationJson.get("city").asText();

            double latitude = locationJson.get("latitude").asDouble();
            double longitude = locationJson.get("longitude").asDouble();

            // Get weather data
            JsonNode weatherJsonNode = weatherService.getWeather(latitude, longitude);
            double temperature = weatherJsonNode.path("main").path("temp").asDouble();

            WeatherDto weatherDto = WeatherDto
                    .builder()
                    .client_ip(ipAddress)
                    .location(city)
                    .greeting("Hello, the temperature is " + temperature + " degree celcius in " + city + "!")
                    .build();
            return ResponseEntity.ok(weatherDto);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorResponseDto errorResponseDto = ErrorResponseDto.builder().message("Internal server error.")
                    .detail(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDto);
        }
    }
}
