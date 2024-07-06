
# Weather App

This project is a Spring Boot application that provides weather information based on the user's IP address.
It fetches the user's IP address, determines their location, and then retrieves the weather data for that location using
the OpenWeather One Call API 3.0.

## Features

- Fetches the user's IP address using the [ipify](https://www.ipify.org/) API.
- Determines the user's location using the [ipapi](https://ipapi.co/) service.
- Retrieves current weather data for the user's location using the OpenWeather One Call API 3.0.
- Handles errors and provides meaningful responses for common HTTP errors such as 401 (Unauthorized) and 404 (Not Found).

## Prerequisites

- Java 8 or higher
- Maven
- An OpenWeather One Call API 3.0 subscription and API key

## Getting Started

### Clone the Repository

```bash
git@github.com:patzu/weather_data_by_ip_address.git
cd weather-app
```

### Configure API Keys

Update the `application.properties` file located in `src/main/resources` with your OpenWeather API key:

```properties
openweathermap.api.key=YOUR_ONE_CALL_API_KEY
```

### Build and Run the Application

```bash
mvn clean install
mvn spring-boot:run
```

### Test the Endpoint

You can test the endpoint using an HTTP client like Postman or the IntelliJ IDEA HTTP client. Make a GET request to the following URL:

```
http://localhost:8080/weather
```

## Project Structure

- **Controller**
  - `WeatherController`: Handles HTTP requests and returns weather data.

- **Service**
  - `IpService`: Fetches the user's IP address and location.
  - `WeatherService`: Retrieves weather data based on the user's location.

## Security Configuration

For debugging purposes, you can disable security by updating the `SecurityConfig` class. 
Ensure to revert these changes after debugging to secure your endpoints.

### Example Security Configuration

```java
package com.example.weatherapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/weather").permitAll()
            .anyRequest().authenticated();
    }
}
```

## Dependencies

- Spring Boot
- Spring Web
- Spring Security
- Jackson Databind
- RestTemplate

## Error Handling

The application handles common HTTP errors such as:
- **401 Unauthorized**: Ensures that the correct API key and subscription plan are used.
- **404 Not Found**: Checks if the endpoint URL is correct.
- **429 Too Many Requests**: Manages API call limits.
