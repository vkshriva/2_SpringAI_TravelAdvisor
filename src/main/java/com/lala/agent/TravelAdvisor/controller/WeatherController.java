package com.lala.agent.TravelAdvisor.controller;

import com.lala.agent.TravelAdvisor.client.WeatherAPIClient;
import com.lala.agent.TravelAdvisor.dto.response.WeatherInfo;
import com.lala.agent.TravelAdvisor.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    @Autowired
    private  WeatherService weatherService;
    @GetMapping("/current")

    public ResponseEntity<?> getCurrentWeather(@RequestParam("city") String city) {
        try {
            WeatherInfo response = weatherService.getWeatherInfo(city);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}

