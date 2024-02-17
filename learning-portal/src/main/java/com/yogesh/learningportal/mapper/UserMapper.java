package com.yogesh.learningportal.mapper;

import org.mapstruct.Mapper;

import com.yogesh.learningportal.dto.UserRequestDto;
import com.yogesh.learningportal.dto.UserResponseDto;
import com.yogesh.learningportal.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserResponseDto convertToDto(User user);

	User convertToEntity(UserResponseDto dto);

	UserResponseDto convertRequestToResponseDto(UserRequestDto userRequestDto);

	User convertRequestToEntity(UserRequestDto userRequestDto);

}
