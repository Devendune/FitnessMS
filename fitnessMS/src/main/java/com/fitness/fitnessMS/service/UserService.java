package com.fitness.fitnessMS.service;

import com.fitness.fitnessMS.dto.RegisterRequest;
import com.fitness.fitnessMS.model.User;
import com.fitness.fitnessMS.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.fitness.fitnessMS.model.UserResponse;

import lombok.AllArgsConstructor;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService
{
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    
    public Boolean validateUser(String userId) {
        return userRepository.existsById(userId);
    }
    
    public UserResponse getUserProfile(String userId)
    {
        Optional<User> user = userRepository.findById(userId);

        return modelMapper.map(user.get(), UserResponse.class);
    }

    public UserResponse register(RegisterRequest request)
    {
        if(userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists");

        User user = modelMapper.map(request, User.class);
        User savedUser = userRepository.save(user);
        
        return modelMapper.map(savedUser, UserResponse.class);
    }
}
