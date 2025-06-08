package com.spring.recommend_contents.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChatGPTRequest {
    private String model;
    private List<Message> messages;

    @Data
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }
}
