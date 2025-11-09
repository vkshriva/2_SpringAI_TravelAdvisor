package com.lala.agent.TravelAdvisor.dto.response;



import java.util.List;


public class Itinerary {
    private int day;
    private List<String> activities;
    private String food;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public List<String> getActivities() {
        return activities;
    }

    public void setActivities(List<String> activities) {
        this.activities = activities;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }
}
