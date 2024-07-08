package com.example.getWeatherDataByIpAddressOfUser.exception;

import com.example.getWeatherDataByIpAddressOfUser.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ClientIpException.class)
    public ResponseEntity<ErrorResponse> handleClientIpException(ClientIpException ex) {
        logger.error("Error in identifying client ip: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(LocationFromIpException.class)
    public ResponseEntity<ErrorResponse> handleLocationFromIpException(LocationFromIpException ex) {
        logger.error("Error identifying location from ip address: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(WeatherDataException.class)
    public ResponseEntity<ErrorResponse> handleWeatherDataException(WeatherDataException ex) {
        logger.error("Error in weather data: {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage(), LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        logger.error("An unexpected error occurred! : {}", ex.getMessage(), ex);
        ErrorResponse errorResponse = ErrorResponse.builder().timeStamp(LocalDateTime.now()).message(ex.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}