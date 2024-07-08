package com.example.getWeatherDataByIpAddressOfUser.exception;

public class ClientIpException extends RuntimeException {
    public ClientIpException(String message) {
        super(message);
    }

    public ClientIpException(String message, Throwable cause) {
        super(message, cause);
    }
}
