package com.yogesh.learningportal.dto;

import java.util.List;

import com.yogesh.learningportal.entity.User.Roles;

import lombok.Data;

@Data

public class UserRequestDto {

	private Long id;
	private String name;
	private String email;
	private Roles role;
	private String password;
	private List<CourseResponseDto> enrolledCourses;
	private List<CourseResponseDto> favoriteCourses;

	public UserRequestDto(Long id, String name, String email, Roles role, String password,
			List<CourseResponseDto> enrolledCourses, List<CourseResponseDto> favoriteCourses) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.password = password;
		this.enrolledCourses = enrolledCourses;
		this.favoriteCourses = favoriteCourses;

	}

	public UserRequestDto() {

	}

}