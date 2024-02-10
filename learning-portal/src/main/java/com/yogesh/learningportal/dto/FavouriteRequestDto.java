package com.yogesh.learningportal.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavouriteRequestDto {
    private Long userId;
    private Long courseId;
    
}

