package com.example.getWeatherDataByIpAddressOfUser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponseDto {
    private String message;
    private String detail;
}
