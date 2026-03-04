package com.fitness.fitnessMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest
{
    @NotBlank(message="Name is required")
    private String name;

    @NotBlank(message="Email is required")
    private String email;

    @NotBlank(message="Password is required")
    @Size(min=6,message="Password should contain atleast 6 characters")
    private String password;

    private String firstName;
    private String lastName;
}
