package com.spring.recommend_contents.api;

import com.spring.recommend_contents.dto.ChatGPTRequest;
import com.spring.recommend_contents.dto.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GPTService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api-key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public String getContentsByGPT(String category, String emotion, String reason) {


        String prompt = String.format(
            "다음 감정과 이유를 가진 사용자에게 추천할 10개의 %s를 추천해서 JSON 형식으로 응답해줘. \n" +
            "JSON 형식은 content_list 키 안에 값으로 10개의 리스트가 담겨져있고,\n" +
            "리스트 내부의 세부 항목은 name(이름), img(이미지 url) 로 구성하면 돼\n" +
            "감정: %s\n이유: %s", category, emotion, reason
        );

        ChatGPTRequest request = new ChatGPTRequest(
                model, List.of(new ChatGPTRequest.Message("user", prompt))
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<ChatGPTRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<ChatGPTResponse> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions", entity, ChatGPTResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return response.getBody().getChoices().get(0).getMessage().getContent();
        } else {
            return "추천 결과를 가져오는 데 실패했습니다.";
        }
    }
}
