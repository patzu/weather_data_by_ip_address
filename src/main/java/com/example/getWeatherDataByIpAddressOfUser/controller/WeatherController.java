package com.example.getWeatherDataByIpAddressOfUser.controller;


import com.example.getWeatherDataByIpAddressOfUser.dto.WeatherDto;
import com.example.getWeatherDataByIpAddressOfUser.exception.ClientIpException;
import com.example.getWeatherDataByIpAddressOfUser.exception.LocationFromIpException;
import com.example.getWeatherDataByIpAddressOfUser.service.IpService;
import com.example.getWeatherDataByIpAddressOfUser.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private final IpService ipService;
    private final WeatherService weatherService;

    public WeatherController(IpService ipService, WeatherService weatherService) {
        this.ipService = ipService;
        this.weatherService = weatherService;
    }

    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @GetMapping("api/weather")
    public ResponseEntity<?> getWeather(HttpServletRequest request) {


        String ipAddress = getClientIp(request);
        if (ipAddress == null || ipAddress.isEmpty()) {
            throw new ClientIpException("Ip address is null or empty!");
        }

        // Get location details
        JsonNode locationJson = ipService.getLocation(ipAddress);
        String city = locationJson.path("city").asText();

        double latitude = locationJson.path("latitude").asDouble();
        double longitude = locationJson.path("longitude").asDouble();

        // Get weather data
        JsonNode weatherJsonNode = weatherService.getWeather(latitude, longitude);
        if (weatherJsonNode == null || !weatherJsonNode.has("main") || !weatherJsonNode.path("main").has("temp")) {
            throw new LocationFromIpException("Could not retrieve weather data!");
        }

        double temperature = weatherJsonNode.path("main").path("temp").asDouble();

        WeatherDto weatherDto = WeatherDto.builder()
                .client_ip(ipAddress)
                .location(city)
                .greeting("Hello, the temperature is " + temperature + " degree celcius in " + city + "!")
                .build();
        return ResponseEntity.ok(weatherDto);

    }

    private String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-Forwarded-For");
            if (remoteAddr == null || remoteAddr.isEmpty()) {
                remoteAddr = request.getRemoteAddr();
            } else {
                // If there are multiple IP addresses in the X-Forwarded-For header, take the first one
                remoteAddr = remoteAddr.split(",")[0];
            }
        }
        return remoteAddr;
    }
}
