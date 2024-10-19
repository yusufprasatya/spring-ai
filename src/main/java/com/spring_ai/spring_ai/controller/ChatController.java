package com.spring_ai.spring_ai.controller;

import com.spring_ai.spring_ai.dto.ChatDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private ChatClient chatClient;

    public ChatController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping
    public Map chat(@RequestBody ChatDto chatDto){
        String response = chatClient.prompt().user(chatDto.getMessage()).call().content();

        return Map.of("generation", response);
    }

    @PostMapping("/generateStream")
    public Flux<String> generateStream(@RequestBody ChatDto chatDto) {
        return chatClient.prompt().user(chatDto.getMessage()).stream().content();
    }

}
