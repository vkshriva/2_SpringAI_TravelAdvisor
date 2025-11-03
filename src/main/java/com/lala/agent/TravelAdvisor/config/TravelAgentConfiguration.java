package com.lala.agent.TravelAdvisor.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TravelAgentConfiguration {

//    @Bean
//    public ChatClient openAiChatClient(ChatClient.Builder builder){
//        ChatOptions chatOptions = ChatOptions.builder()
//                .model("gpt-4o-mini")
//                .build();
//        return builder.defaultOptions(chatOptions).build();
//    }

    @Bean
    public ChatClient openAiChatClient(OpenAiChatModel model){
        return ChatClient.create(model);
    }

    @Bean
    public ChatClient ollamaChatClient(OllamaChatModel model){
       return ChatClient.create(model);
    }
}
