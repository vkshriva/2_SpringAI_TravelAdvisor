package com.lala.agent.TravelAdvisor.dto.response;
import java.util.List;

public record  TravelResponse(int days, String city, List<Itinerary> itinerary, WeatherInfo weatherInfo){}
