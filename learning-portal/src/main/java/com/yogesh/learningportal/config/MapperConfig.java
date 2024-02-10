package com.yogesh.learningportal.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.yogesh.learningportal.mapper.UserMapper;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public UserMapper userMapper(ModelMapper modelMapper) {
        return new UserMapper(modelMapper);
    }
}
