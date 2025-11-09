package com.lala.agent.TravelAdvisor.dto.response;

import lombok.Data;

@Data
public class WeatherInfo {
  private String temperature;
  private String condition;
  private String humidity;
  private String windSpeed;
}