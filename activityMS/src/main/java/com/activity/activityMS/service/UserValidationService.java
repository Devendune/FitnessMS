package com.activity.activityMS.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserValidationService
{
    private final WebClient userServiceWebClient;
    
    public boolean validateUser(String userId)
    {
        try{
            System.out.println("Validating user: " + userId);
            Boolean result = userServiceWebClient.get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            
            System.out.println("Validation result for " + userId + ": " + result);
            return result != null && result;
                
        }catch (WebClientResponseException e)
        {
            System.out.println("WebClientResponseException for user " + userId + ": " + e.getStatusCode());
            if(e.getStatusCode()== HttpStatus.NOT_FOUND)
            {
                throw new RuntimeException("User not found: "+userId);
            }
            if(e.getStatusCode()== HttpStatus.BAD_REQUEST)
            {
                throw new RuntimeException("Invalid request for user: "+userId);
            }
            return false;
        }
        catch (Exception e)
        {
            System.out.println("Exception validating user " + userId + ": " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error validating user: " + userId + " - " + e.getMessage());
        }
    }
}

