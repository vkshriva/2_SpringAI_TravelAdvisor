package com.lala.agent.TravelAdvisor.service;

import com.lala.agent.TravelAdvisor.client.WeatherAPIClient;
import com.lala.agent.TravelAdvisor.dto.response.WeatherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WeatherService {

    @Autowired
    private WeatherAPIClient weatherAPIClient;

    @Value("${weather.api.key:}")
    private String apiKey;

    public WeatherInfo getWeatherInfo(String city) {
        Map<String, Object> response = weatherAPIClient.getCurrentWeather(city, apiKey, "metric");
        if (response == null) {
            return new WeatherInfo("N/A", "N/A", "N/A", "N/A");
        }

        // Extract temperature from response.main.temp (no unit symbol)
        String temperature = "N/A";
        Object mainObj = response.get("main");
        if (mainObj instanceof Map<?, ?> mainMap) {
            Object tempObj = mainMap.get("temp");
            if (tempObj instanceof Number n) {
                temperature = String.format("%.2f", n.doubleValue());
            }
        }

        // Extract weather description from response.weather[0].description
        String condition = "N/A";
        Object weatherObj = response.get("weather");
        if (weatherObj instanceof List<?> weatherList && !weatherList.isEmpty()) {
            Object first = weatherList.get(0);
            if (first instanceof Map<?, ?> firstMap) {
                Object desc = firstMap.get("description");
                if (desc != null) {
                    condition = desc.toString();
                }
            }
        }

        // Extract humidity from response.main.humidity (no % symbol)
        String humidity = "N/A";
        if (mainObj instanceof Map<?, ?> mainMap2) {
            Object humObj = mainMap2.get("humidity");
            if (humObj instanceof Number hn) {
                humidity = String.format("%d", hn.intValue());
            } else if (humObj != null) {
                humidity = humObj.toString();
            }
        }

        // Extract wind speed from response.wind.speed (no unit symbol)
        String windSpeed = "N/A";
        Object windObj = response.get("wind");
        if (windObj instanceof Map<?, ?> windMap) {
            Object speedObj = windMap.get("speed");
            if (speedObj instanceof Number sn) {
                windSpeed = String.format("%.2f", sn.doubleValue());
            } else if (speedObj != null) {
                windSpeed = speedObj.toString();
            }
        }

        return new WeatherInfo(temperature, condition, humidity, windSpeed);
    }
}
