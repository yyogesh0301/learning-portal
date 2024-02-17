
package com.yogesh.learningportal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.yogesh.learningportal.dto.CourseRequestDto;
import com.yogesh.learningportal.dto.CourseResponseDto;
import com.yogesh.learningportal.entity.Course;

@Mapper(componentModel = "spring")
public interface CourseMapper {

	CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

	CourseRequestDto convertToDto(Course course);

	Course convertToEntity(CourseRequestDto dto);

	default CourseResponseDto convertToResponseDto(Course course) {
		if (course == null) {
			return null;
		}
		CourseResponseDto dto = new CourseResponseDto();
		dto.setId(course.getId());
		dto.setName(course.getName());
		dto.setDesc(course.getDesc());
		dto.setAuthorName(course.getAuthor().getName());
		dto.setCategoryName(course.getCategory().getName());
		return dto;
	}

}
