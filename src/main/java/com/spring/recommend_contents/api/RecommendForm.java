package com.spring.recommend_contents.api;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RecommendForm {
    @NotEmpty
    private String category;

    @NotEmpty
    private String emotion;

    @NotEmpty
    private String reason;
}
