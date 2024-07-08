package com.example.getWeatherDataByIpAddressOfUser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class WeatherDto {
    private String client_ip;
    private String location;
    private String greeting;

}
