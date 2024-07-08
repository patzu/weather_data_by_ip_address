package com.example.getWeatherDataByIpAddressOfUser.service;

import com.example.getWeatherDataByIpAddressOfUser.exception.LocationFromIpException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IpService {

    private static final Logger logger = LoggerFactory.getLogger(IpService.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;


    public IpService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public JsonNode getLocation(String ipAddress) {
        String locationUrl = "https://ipapi.co/" + ipAddress + "/json/";
        try {
            logger.info("Getting location from ip address {}", ipAddress);
            String response = restTemplate.getForObject(locationUrl, String.class);
            return objectMapper.readTree(response);
        } catch (JsonProcessingException e) {
            logger.error("Error processing data for ip address {}", ipAddress, e);
            throw new LocationFromIpException("Error processing data for ip address " + ipAddress, e);
        } catch (Exception e) {
            logger.error("Error fetching data from ip address {}", ipAddress);
            throw new LocationFromIpException("Error fetching data for ip address " + ipAddress, e);
        }
    }
}