package com.yogesh.learningportal.dto;

import java.util.List;

import com.yogesh.learningportal.entity.User.Roles;

import lombok.Data;

@Data
public class UserResponseDto {

    private Long id;
    private String name;
    private String email;
    private Roles role;
    private List<CourseDto> enrolledCourses;
    private List<CourseDto> favoriteCourses;
	public UserResponseDto(Long id, String name, String email,Roles role, List<CourseDto> enrolledCourses,
			List<CourseDto> favoriteCourses) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role=role;
		this.enrolledCourses = enrolledCourses;
		this.favoriteCourses = favoriteCourses;
	}
	
	public UserResponseDto() {
		
    }
	
    
}