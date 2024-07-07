package com.example.getWeatherDataByIpAddressOfUser.controller;


import com.example.getWeatherDataByIpAddressOfUser.model.ErrorResponseDto;
import com.example.getWeatherDataByIpAddressOfUser.model.WeatherDto;
import com.example.getWeatherDataByIpAddressOfUser.service.IpService;
import com.example.getWeatherDataByIpAddressOfUser.service.WeatherService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> getWeather(HttpServletRequest request) {
        try {

            // Get IP address
            // String ipAddress = ipService.getIpAddress();

            String ipAddress = getClientIp(request);

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
