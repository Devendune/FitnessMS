package com.activity.activityMS.service;

import com.activity.activityMS.dto.ActivityRequest;
import com.activity.activityMS.dto.ActivityResponse;
import com.activity.activityMS.model.Activity;
import com.activity.activityMS.repository.ActivityRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityService
{
    @Autowired
    private final ActivityRepository activityRepository;
    private final ModelMapper modelMapper;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponse trackActivity(ActivityRequest activityRequest)
     {
         /*
        boolean isValidUser=userValidationService.validateUser(activityRequest.getUserId());
        if(!isValidUser)
            throw new RuntimeException("Invalid user: "+activityRequest.getUserId());
        */

        Activity activity=modelMapper.map(activityRequest,Activity.class);
        Activity savedActivity=activityRepository.save(activity);

        try{
            rabbitTemplate.convertAndSend(exchange,routingKey,savedActivity);
            log.info("Activity message sent to RabbitMQ: {} with routing key: {}", exchange, routingKey);
        }catch(Exception e){
            log.error("Failed to send activity message to RabbitMQ: {}", e.getMessage(), e);
        }
        return modelMapper.map(savedActivity,ActivityResponse.class);
    }

    public List<ActivityResponse> getActivities(String userId)
    {
        List<Activity> activities=activityRepository.findByUserId(userId);
        return activities.stream()
                .map(activity->modelMapper.map(activity,ActivityResponse.class))
                .toList();
    }
}
