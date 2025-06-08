package com.spring.recommend_contents.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.recommend_contents.user.UserSignUpForm;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RequiredArgsConstructor
@Controller
public class RecommendationController {

    private final GPTService gptService;

    @PostMapping("/recommend-list")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> recommend(@RequestBody RecommendForm recommendForm) {
//        String category = recommendForm.getCategory();
//        String emotion = recommendForm.getEmotion();
//        String reason = recommendForm.getReason();
//
//        if (emotion == null || emotion.isEmpty() || reason == null || reason.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//
//        String aiResponse = gptService.getContentsByGPT(category, emotion, reason);

        String aiResponse = "{\n" +
                "  \"content_list\": [\n" +
                "    {\n" +
                "      \"name\": \"인터스텔라 (Interstellar)\",\n" +
                "      \"img\": \"https://upload.wikimedia.org/wikipedia/en/b/bc/Interstellar_film_poster.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"어바웃 타임 (About Time)\",\n" +
                "      \"img\": \"https://upload.wikimedia.org/wikipedia/en/7/7c/About_Time_%282013_film%29_Poster.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"라라랜드 (La La Land)\",\n" +
                "      \"img\": \"https://upload.wikimedia.org/wikipedia/en/a/ab/La_La_Land_%28film%29.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"이터널 선샤인 (Eternal Sunshine of the Spotless Mind)\",\n" +
                "      \"img\": \"https://upload.wikimedia.org/wikipedia/en/a/a4/Eternal_Sunshine_of_the_Spotless_Mind.png\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"인사이드 아웃 (Inside Out)\",\n" +
                "      \"img\": \"https://upload.wikimedia.org/wikipedia/en/0/0a/Inside_Out_%282015_film%29_poster.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"비긴 어게인 (Begin Again)\",\n" +
                "      \"img\": \"https://upload.wikimedia.org/wikipedia/en/b/bd/Begin_Again_film_poster_2014.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"500일의 썸머 (500 Days of Summer)\",\n" +
                "      \"img\": \"https://upload.wikimedia.org/wikipedia/en/d/d1/Five_hundred_days_of_summer.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"나의 소녀시대 (Our Times)\",\n" +
                "      \"img\": \"https://upload.wikimedia.org/wikipedia/en/f/f3/Our_Times%2C_Movie_Poster.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"히치하이커 (The Hitchhiker’s Guide to the Galaxy)\",\n" +
                "      \"img\": \"https://upload.wikimedia.org/wikipedia/en/7/7a/Hitchhikers_guide_to_the_galaxy.jpg\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"미 비포 유 (Me Before You)\",\n" +
                "      \"img\": \"https://upload.wikimedia.org/wikipedia/en/f/fd/Me_Before_You_%28film%29.jpg\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // 생성형 AI 처리 로직 (OpenAI API 등 연동 가능)
//        List<String> contentsList = parseGptResponse(aiResponse);
        List<Map<String, String>> contentsList = parseGptResponse(aiResponse);

        Map<String, Object> response = new HashMap<>();
        response.put("contents", contentsList);
        return ResponseEntity.ok(response);
    }

    private List<Map<String, String>> parseGptResponse(String response) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> resultMap = objectMapper.readValue(response, Map.class);
            List<Map<String, String>> list = (List<Map<String, String>>) resultMap.get("content_list");

            return list;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
//    private List<String> parseGptResponse(String response) {
//        List<String> list = Arrays.stream(response.split("\n"))
//                .map(String::trim)
//                .filter(line -> !line.isEmpty())
//                .collect(Collectors.toList());
//        return list;
//    }
}
