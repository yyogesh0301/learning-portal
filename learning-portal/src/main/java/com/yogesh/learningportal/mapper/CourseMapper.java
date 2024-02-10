//package com.yogesh.learningportal.mapper;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.factory.Mappers;
//
//import com.yogesh.learningportal.dto.CourseCreateDto;
//import com.yogesh.learningportal.entity.Course;
//
//@Mapper
//public interface CourseMapper {
//    
//    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);
//
//    @Mapping(target = "id", ignore = true) 
//    @Mapping(target = "category", ignore = true) 
//    Course dtoToEntity(CourseCreateDto courseCreateDto);
//
//}
