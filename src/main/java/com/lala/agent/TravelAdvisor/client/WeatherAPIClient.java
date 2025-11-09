package com.lala.agent.TravelAdvisor.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "weather-api-client", url = "${weather.api.baseUrl}")
public interface WeatherAPIClient {
    @GetMapping("/weather")
    Map<String, Object> getCurrentWeather(@RequestParam("q") String city, @RequestParam("appid") String apiKey, @RequestParam(value = "units", required = false) String units);

}
