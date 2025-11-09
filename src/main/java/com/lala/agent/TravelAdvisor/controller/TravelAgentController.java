package com.lala.agent.TravelAdvisor.controller;

import com.lala.agent.TravelAdvisor.dto.response.TravelResponse;
import com.lala.agent.TravelAdvisor.tools.WeatherTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class TravelAgentController {

    private static final Logger log = LoggerFactory.getLogger(TravelAgentController.class);

    private final ChatClient openAiChatClient;
    private final WeatherTools weatherTools;

    @Value("classpath:prompts/travel-plan-prompt.st")
    private Resource travelTemplate;

    public TravelAgentController(ChatClient openAiChatClient, WeatherTools weatherTools) {
        this.openAiChatClient = openAiChatClient;
        this.weatherTools = weatherTools;
    }


    @PostMapping("/recommend")
    public ResponseEntity<?> recommendTravelWithWeather(
                                                         @RequestParam("city") String city,
                                                         @RequestParam("days") int days) {
        try{
            PromptTemplate promptTemplate = new PromptTemplate(travelTemplate);
            Map<String, Object> variables = Map.of("city", city, "days", days);
            Prompt promptObj = promptTemplate.create(variables);
            TravelResponse response = openAiChatClient.prompt()
                    .system("You are a travel planning assistant which use weatherTools when asked for the weather details")
                    .tools(weatherTools)
                    .user(promptObj.getContents())
                    .call()
                    .entity(TravelResponse.class);

            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (Exception e){
            log.error("Error in /recommend", e);
            return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/recommend-without-weather")
    public ResponseEntity<?> recommendTravelWithoutWeather(@RequestHeader(value = "Accept", required = false) String acceptHeader,
                                                            @RequestParam("city") String city,
                                                            @RequestParam("days") int days) {
        log.debug("/recommend-without-weather called; Accept={}", acceptHeader);
        try{
            PromptTemplate promptTemplate = new PromptTemplate(travelTemplate);
            Map<String, Object> variables = Map.of("city", city, "days", days);
            Prompt promptObj = promptTemplate.create(variables);
            TravelResponse response = openAiChatClient.prompt()
                    .system("You are a travel planning assistant  and you are an API that return a single valid JSON object")
                    .user(promptObj.getContents())
                    .call()
                    .entity(TravelResponse.class);
            if (response == null) {
                return ResponseEntity.status(502).contentType(MediaType.APPLICATION_JSON).body(Map.of("error", "Upstream model returned no content"));
            }
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(response);
        } catch (Exception e){
            log.error("Error in /recommend-without-weather", e);
            return ResponseEntity.status(500).contentType(MediaType.APPLICATION_JSON).body(Map.of("error", e.getMessage()));
        }
    }
}
