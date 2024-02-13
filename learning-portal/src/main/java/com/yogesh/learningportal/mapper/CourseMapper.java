
package com.yogesh.learningportal.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.yogesh.learningportal.dto.CourseRequestDto;
import com.yogesh.learningportal.dto.CourseResponseDto;
import com.yogesh.learningportal.entity.Course;

@Component
public class CourseMapper {

	private final ModelMapper modelMapper;

	public CourseMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public CourseRequestDto convertToDto(Course course) {
		return modelMapper.map(course, CourseRequestDto.class);
	}

	public Course convertToEntity(CourseRequestDto dto) {
		return modelMapper.map(dto, Course.class);
	}

	public CourseResponseDto convertToResponseDto(Course course) {
		return modelMapper.map(course, CourseResponseDto.class);
	}

}