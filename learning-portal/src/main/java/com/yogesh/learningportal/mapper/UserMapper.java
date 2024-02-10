package com.yogesh.learningportal.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.yogesh.learningportal.dto.UserResponseDto;
import com.yogesh.learningportal.entity.User;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponseDto convertToDto(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    public User convertToEntity(UserResponseDto dto) {
        return modelMapper.map(dto, User.class);
    }
}
//import com.yogesh.learningportal.dto.UserResponseDto;
//import com.yogesh.learningportal.entity.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.MappingTarget;
//import org.mapstruct.factory.Mappers;
//
//@Mapper
//public interface UserMapper {
//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
//
//    @Mapping(source = "enrolledCourses", target = "enrolledCourses", ignore = true) 
//    @Mapping(source = "favoriteCourses", target = "favoriteCourses", ignore = true) 
//    UserResponseDto convertToDto(User user);
//
//    User convertToEntity(UserResponseDto userDto);
//
//    @Mapping(source = "enrolledCourses", target = "enrolledCourses", ignore = true) 
//    @Mapping(source = "favoriteCourses", target = "favoriteCourses", ignore = true) // Ignore mapping to prevent recursive mapping
//    void updateFromDto(UserResponseDto userDto, @MappingTarget User user);
//}
