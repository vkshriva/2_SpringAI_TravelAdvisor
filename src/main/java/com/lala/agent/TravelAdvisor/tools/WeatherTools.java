package com.lala.agent.TravelAdvisor.tools;

import com.lala.agent.TravelAdvisor.dto.response.WeatherInfo;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class WeatherTools {
    @Tool(name="get_weather_info", description="Get current weather information for a specified city and return structured weather data" )
    public WeatherInfo getWeatherInfo(String city) {
        // Mocked weather data for demonstration purposes
        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setTemperature("25°C");
        weatherInfo.setCondition("Sunny");
        weatherInfo.setHumidity("60%");
        weatherInfo.setWindSpeed("19 km/h");
        return weatherInfo;
    }


}
