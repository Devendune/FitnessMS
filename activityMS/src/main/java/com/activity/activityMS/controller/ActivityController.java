package com.activity.activityMS.controller;

import com.activity.activityMS.dto.ActivityRequest;
import com.activity.activityMS.dto.ActivityResponse;
import com.activity.activityMS.service.ActivityService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@AllArgsConstructor
public class ActivityController {
    private ActivityService activityService;

    @PostMapping("/")
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest activityRequest) {
        return ResponseEntity.ok(activityService.trackActivity(activityRequest));
    }

    @GetMapping("/fetchActivities/{userId}")
    public ResponseEntity<List<ActivityResponse>> getActivities(@PathVariable String userId) {
        return ResponseEntity.ok(activityService.getActivities(userId));
    }


    
}

