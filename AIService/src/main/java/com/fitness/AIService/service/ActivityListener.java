package com.fitness.AIService.service;

import com.fitness.AIService.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityListener
{
    @Value("${rabbitmq.queue.name}")
    private String queueName;

    private final ActivityAIService activityAIService;

    @RabbitListener(queues="activity.queue")
    public void processActivity(Activity activity)
    {
        log.info("Received activity for processing {}",activity.getId());
        String recommendation = activityAIService.generateRecommendation(activity);
        log.info("Generated Recommendation for activity {}: {}", activity.getId(), recommendation);
    }
}
