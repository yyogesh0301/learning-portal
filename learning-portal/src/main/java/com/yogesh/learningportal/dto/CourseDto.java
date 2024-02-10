package com.yogesh.learningportal.dto;

import com.yogesh.learningportal.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private Long id;
    private String name;
    private String author;
    private String desc;
    private Category category;
    
}
