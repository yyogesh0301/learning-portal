package com.yogesh.learningportal.dto;

import lombok.Data;

@Data
public class CourseCreateDto {
	private String name;
	private String author;
	private String desc;
	private String categoryName;
}
