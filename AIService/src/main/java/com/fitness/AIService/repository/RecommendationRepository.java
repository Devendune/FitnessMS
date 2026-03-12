package com.fitness.AIService.repository;

import com.fitness.AIService.model.Recommendation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends MongoRepository<Recommendation, String>
{
    List<Recommendation> findByUserId(String userId);
    Recommendation findByActivityId(String activityId);
}
