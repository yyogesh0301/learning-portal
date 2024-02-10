package com.yogesh.learningportal.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RegistrationResponseDto {
    private Long id;
    private LocalDateTime registeredDate;
    private Long userId;
    private Long courseId;

    // Constructors, getters, and setters
}
