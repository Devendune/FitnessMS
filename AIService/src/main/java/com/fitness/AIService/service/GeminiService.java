package com.fitness.AIService.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Map;

@Service
@RequiredArgsConstructor

public class GeminiService
{
    private final WebClient.Builder webClientBuilder;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    public String getAnswer(String question)
    {
        Map<String, Object> requestBody = Map.of(
            "contents", java.util.List.of(
                Map.of("parts", java.util.List.of(Map.of("text", question)))
            )
        );

        JsonNode response = webClientBuilder.build()
            .post()
            .uri(geminiApiUrl + "?key=" + geminiApiKey)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(JsonNode.class)
            .block();

        if (response == null) {
            return "No response from Gemini";
        }
        return response.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

    }

}
