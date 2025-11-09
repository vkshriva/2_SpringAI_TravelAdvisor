package com.lala.agent.TravelAdvisor.dto.response;

public class WeatherInfo {

  private String temperature;
  private String condition;
  private String humidity;
  private String windSpeed;

  public WeatherInfo(String temperature, String condition, String humidity, String windSpeed) {
    this.temperature = temperature;
    this.condition = condition;
    this.humidity = humidity;
    this.windSpeed = windSpeed;
  }

  @Override
  public String toString() {
    return "WeatherInfo{" +
            "temperature='" + temperature + '\'' +
            ", condition='" + condition + '\'' +
            ", humidity='" + humidity + '\'' +
            ", windSpeed='" + windSpeed + '\'' +
            '}';
  }

  public WeatherInfo() {
  }

  public String getTemperature() {
    return temperature;
  }

  public void setTemperature(String temperature) {
    this.temperature = temperature;
  }

  public String getCondition() {
    return condition;
  }

  public void setCondition(String condition) {
    this.condition = condition;
  }

  public String getHumidity() {
    return humidity;
  }

  public void setHumidity(String humidity) {
    this.humidity = humidity;
  }

  public String getWindSpeed() {
    return windSpeed;
  }

  public void setWindSpeed(String windSpeed) {
    this.windSpeed = windSpeed;
  }
}