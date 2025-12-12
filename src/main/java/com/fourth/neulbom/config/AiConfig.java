package com.fourth.neulbom.config;

import com.fourth.neulbom.mcp.ProblemTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfig {
    @Bean
    public ChatClient chatClient(OpenAiChatModel model, ProblemTools tools) {
        return ChatClient.builder(model)
                .defaultTools(tools)
                .build();
    }
}
