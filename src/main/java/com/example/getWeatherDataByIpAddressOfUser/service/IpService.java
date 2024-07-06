package com.example.getWeatherDataByIpAddressOfUser.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IpService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getIpAddress() throws Exception {
        String ipUrl = "https://api.ipify.org?format=json";
        String response = restTemplate.getForObject(ipUrl, String.class);
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("ip").asText();
    }

    public JsonNode getLocation(String ipAddress) throws Exception {
        String locationUrl = "https://ipapi.co/" + ipAddress + "/json/";
        String response = restTemplate.getForObject(locationUrl, String.class);
        return objectMapper.readTree(response);
    }
}
