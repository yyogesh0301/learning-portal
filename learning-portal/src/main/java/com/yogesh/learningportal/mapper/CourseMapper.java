//package com.yogesh.learningportal.mapper;
//
//import com.yogesh.learningportal.dto.CourseCreateDto;
//import com.yogesh.learningportal.entity.Course;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface CourseMapper {
//
//    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
//
//    @Mapping(source = "author.name", target = "authorName")
//    @Mapping(source = "category.name", target = "categoryName")
//    CourseCreateDto courseToDto(Course course);
//
//    @Mapping(source = "authorName", target = "author.name")
//    @Mapping(source = "categoryName", target = "category.name")
//    Course dtoToCourse(CourseCreateDto courseDto);
//}
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