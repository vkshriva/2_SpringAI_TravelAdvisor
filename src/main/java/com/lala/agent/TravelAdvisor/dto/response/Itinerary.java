package com.lala.agent.TravelAdvisor.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class Itinerary {
    private int day;
    private List<String> activities;
    private String food;
}
