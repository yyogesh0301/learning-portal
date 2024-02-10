package com.yogesh.learningportal.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrollmentRequestDto {
    private Long userId;
    private String userName;
    private Long courseId;
}

