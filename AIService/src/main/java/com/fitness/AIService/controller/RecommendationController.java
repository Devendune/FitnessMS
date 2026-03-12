package com.fitness.AIService.controller;

import com.fitness.AIService.model.Recommendation;
import com.fitness.AIService.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recommendations")
@AllArgsConstructor
public class RecommendationController
{
    private RecommendationService recommendationService;

    @PostMapping
    public ResponseEntity<Recommendation> createRecommendation(@RequestBody Recommendation recommendation) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recommendationService.createRecommendation(recommendation));
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getRecommendationsByUserId(@PathVariable String userId) {
        return ResponseEntity.ok(recommendationService.getRecommendationsByUserId(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendation> getRecommendationsByActivityId(@PathVariable String activityId) {
        return ResponseEntity.ok(recommendationService.getRecommendationsByActivityId(activityId));
    }
}
