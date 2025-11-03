package com.lala.agent.TravelAdvisor.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class TravelAgentConfiguration {

    /*
    Configure ChatClient with MessageChatMemoryAdvisor
    -Uses an in-memory repository
    -Keeps a window of the last 20 messages
     */

    @Bean
    @Primary
    public ChatClient openAiChatClient(ChatClient.Builder  builder){
        //in-memory repository (demo only)
        InMemoryChatMemoryRepository memoryRepository = new InMemoryChatMemoryRepository();
        //a window that keeps the last N message per conversation
        MessageWindowChatMemory memory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(memoryRepository)
                .maxMessages(20)
                .build();

        //Create the MesssageChatMemoryAdvisor
        MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder(memory).build();

        return builder.defaultAdvisors(memoryAdvisor).build();

    }

//    @Bean
//    public ChatClient ollamaChatClient(OllamaChatModel model){
//       return ChatClient.create(model);wi
//    }
}
