package com.fitness.AIService.service;

import com.fitness.AIService.model.Activity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityAIService
{
    private final GeminiService geminiService;

    public String generateRecommendation(Activity activity)
    {
        String prompt=createPromptFromActivity(activity);
        String aiResponse=geminiService.getAnswer(prompt);
        log.info("The response is "+aiResponse);
        return aiResponse;
    }

        private String createPromptFromActivity(Activity activity) {
                return String.format("""
                        Analyze this fitness activity and provide detailed recommendations in the following JSON format:
                        {
                            "analysis": {
                                "overall": "Overall analysis of the activity",
                                "pace": "Pace analysis and effectiveness",
                                "heartRate": "Heart rate and cardiovascular impact",
                                "caloriesBurned": "Calories burned estimate and comparison"
                            },
                            "improvements": [
                                {
                                    "area": "Area name",
                                    "recommendation": "Detailed recommendation"
                                }
                            ],
                            "suggestions": [
                                {
                                    "workout": "Workout name",
                                    "description": "Detailed workout description"
                                }
                            ],
                            "safety": [
                                "Safety point 1",
                                "Safety point 2"
                            ]
                        }

                        Activity Details:
                        - Type: %s
                        - Duration: %d minutes
                        - Calories Burned: %d
                        - Start Time: %s
                        - Additional Metrics: %s

                        Please provide actionable, personalized recommendations based on this activity.
                        """,
                        activity.getType(),
                        activity.getDuration(),
                        activity.getCaloriesBurned(),
                        activity.getStartTime(),
                        activity.getAdditionalMetrics() != null ? activity.getAdditionalMetrics().toString() : "None"
                );
        }
}
