package com.yogesh.learningportal.dto;

import lombok.Data;

@Data
public class CourseRequestDto {
	private String name;
	private String authorName;
	private String desc;
	private String categoryName;
}
