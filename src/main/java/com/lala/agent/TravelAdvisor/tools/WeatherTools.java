package com.lala.agent.TravelAdvisor.tools;

import com.lala.agent.TravelAdvisor.dto.response.WeatherInfo;
import com.lala.agent.TravelAdvisor.service.WeatherService;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherTools {
    @Autowired
    private WeatherService weatherService;
    @Tool(name="get_weather_info", description="Get current weather information for a specified city and return structured weather data" )
    public WeatherInfo getWeatherInfo(String city) {

        WeatherInfo weatherInfo =  weatherService.getWeatherInfo(city);
        System.out.println(weatherInfo.toString());
        return weatherInfo;
    }


}
