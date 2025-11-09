package com.lala.agent.TravelAdvisor.dto.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class TravelResponse {
    private int days;
    private String city;
    private List<Itinerary> itinerary;
    private WeatherInfo weatherInfo;

    public WeatherInfo getWeatherInfo() {
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Itinerary> getItinerary() {
        return itinerary;
    }

    public void setItinerary(List<Itinerary> itinerary) {
        this.itinerary = itinerary;
    }


}
