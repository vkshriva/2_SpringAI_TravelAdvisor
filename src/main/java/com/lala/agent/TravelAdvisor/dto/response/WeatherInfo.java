package com.lala.agent.TravelAdvisor.dto.response;
public record WeatherInfo(
    String temperature,
    String condition,
    String humidity,
    String windSpeed
) {}