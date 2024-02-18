package com.yogesh.learningportal.dto;

import java.util.List;

import com.yogesh.learningportal.entity.User.Roles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

	private Long id;
	private String name;
	private String email;
	private Roles role;
	private String password;
	private List<CourseResponseDto> enrolledCourses;
	private List<CourseResponseDto> favoriteCourses;

}