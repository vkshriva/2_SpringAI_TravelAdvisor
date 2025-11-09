package com.lala.agent.TravelAdvisor.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AskController {
    @Autowired
    private ChatClient chatClient;

    @GetMapping("/ask")
    public ResponseEntity<String> getMessage(@RequestParam("message") String message){
        try {
            String reply = chatClient.prompt(message).call().content();
            return ResponseEntity.ok(reply);
        } catch (Exception e) {
            System.out.println("AI gives an Error +ex");
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

}
