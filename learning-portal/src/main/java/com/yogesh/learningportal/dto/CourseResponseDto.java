package com.yogesh.learningportal.dto;

import lombok.Data;

@Data
public class CourseResponseDto {
	private Long id;
	private String name;
	private String desc;
	private String categoryName;
	private String authorName;
}
