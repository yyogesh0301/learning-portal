package com.yogesh.learningportal.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.yogesh.learningportal.dto.UserRequestDto;
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
	
	public UserResponseDto convertRequestToResponseDto(UserRequestDto userRequestDto) {
        return modelMapper.map(userRequestDto, UserResponseDto.class);
    }
	
	public User convertRequestToEntity(UserRequestDto userRequestDto) {
        return modelMapper.map(userRequestDto, User.class);
    }
	
}