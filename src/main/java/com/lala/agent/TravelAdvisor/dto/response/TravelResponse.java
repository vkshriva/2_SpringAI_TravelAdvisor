package com.lala.agent.TravelAdvisor.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class TravelResponse {
    private int days;
    private String city;
    private List<Itinerary> itinerary;
    private WeatherInfo weatherInfo;
}
