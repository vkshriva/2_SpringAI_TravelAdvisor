package com.lala.agent.TravelAdvisor.controller;

import com.lala.agent.TravelAdvisor.dto.response.TravelResponse;
import com.lala.agent.TravelAdvisor.tools.WeatherTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TravelAgentController {

    private final ChatClient openAiChatClient;
    private final WeatherTools weatherTools;

    @Value("classpath:prompts/travel-plan-prompt.st")
    private Resource travelTemplate;

    public TravelAgentController(ChatClient openAiChatClient, WeatherTools weatherTools) {
        this.openAiChatClient = openAiChatClient;
        this.weatherTools = weatherTools;
    }


    @PostMapping("/recommend")
    public ResponseEntity<?> recommendTravelWithWeather(@RequestParam("city") String city, @RequestParam("days") int days) {
        try{
            PromptTemplate promptTemplate = new PromptTemplate(travelTemplate);
            Map<String, Object> variables = Map.of("city", city, "days", days);
            Prompt promptObj = promptTemplate.create(variables);
            TravelResponse response = openAiChatClient.prompt()
                    .system("You are a travel planning assistant which use weatherTools when asked for the weather details  and you are an API that return a single valid JSON object")
                    .tools(weatherTools)
                    .user(promptObj.getContents())
                    .call()
                    .entity(TravelResponse.class);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error generating travel plan: " + e.getMessage());
        }
    }

    @PostMapping("/recommend-without-weather")
    public ResponseEntity<?> recommendTravelWithoutWeather(@RequestParam("city") String city, @RequestParam("days") int days) {
        try{
            PromptTemplate promptTemplate = new PromptTemplate(travelTemplate);
            Map<String, Object> variables = Map.of("city", city, "days", days);
            Prompt promptObj = promptTemplate.create(variables);
            TravelResponse response = openAiChatClient.prompt()
                    .system("You are a travel planning assistant  and you are an API that return a single valid JSON object")
                    .user(promptObj.getContents())
                    .call()
                    .entity(TravelResponse.class);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error generating travel plan: " + e.getMessage());
        }
    }
}
