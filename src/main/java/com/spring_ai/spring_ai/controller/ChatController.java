package com.spring_ai.spring_ai.controller;

import com.spring_ai.spring_ai.dto.ChatDto;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.ChatClient.Builder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping
    public Map<String, String> chat(@RequestPart String message,
                    @RequestPart(value = "image", required = false) MultipartFile image){
        String response = chatClient.prompt()
                .user(u -> u.text(message)
                        .media(MimeTypeUtils.IMAGE_PNG, image.getResource()))
                .call()
                .content();

        return Map.of("generation", response);
    }

    @PostMapping("/generateStream")
    public Flux<String> generateStream(@RequestBody ChatDto chatDto) {
        return chatClient.prompt().user(chatDto.getMessage()).stream().content();
    }

    @PostMapping("/generateStreamWithImage")
    public Flux<String> generateStreamWithImage(@RequestPart String message,
                                                @RequestPart(value = "image", required = false) MultipartFile image) {
        return chatClient.prompt()
                .user(u -> u.text(message)
                        .media(MimeTypeUtils.IMAGE_PNG, image.getResource()))
                .stream()
                .content();
    }

}
