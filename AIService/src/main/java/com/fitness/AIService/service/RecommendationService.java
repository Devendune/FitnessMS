package com.fitness.AIService.service;

import com.fitness.AIService.model.Recommendation;
import com.fitness.AIService.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendationService
{
    private final RecommendationRepository recommendationRepository;

    public Recommendation createRecommendation(Recommendation recommendation) {
        return recommendationRepository.save(recommendation);
    }


    public List<Recommendation> getRecommendationsByUserId(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation getRecommendationsByActivityId(String activityId) {
        return recommendationRepository.findByActivityId(activityId);
    }
}
