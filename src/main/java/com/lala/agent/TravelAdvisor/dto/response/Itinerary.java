package com.lala.agent.TravelAdvisor.dto.response;

import java.util.List;

public record Itinerary(
    int day,
    List<String> activities,
    String food
) {}

