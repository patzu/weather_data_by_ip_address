package com.example.getWeatherDataByIpAddressOfUser.service;

import com.example.getWeatherDataByIpAddressOfUser.exception.LocationFromIpException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IpService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public IpService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public JsonNode getLocation(String ipAddress) {
        try {
            String locationUrl = "https://ipapi.co/" + ipAddress + "/json/";
            String response = restTemplate.getForObject(locationUrl, String.class);
            return objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            throw new LocationFromIpException("Error processing data for ip address " + ipAddress, e);
        } catch (Exception e) {
            throw new LocationFromIpException("Error fetching data for ip address " + ipAddress, e);
        }
    }
}
