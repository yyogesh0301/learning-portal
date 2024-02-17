package com.yogesh.learningportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yogesh.learningportal.dto.CourseResponseDto;
import com.yogesh.learningportal.dto.UserResponseDto;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.User;
import com.yogesh.learningportal.mapper.CourseMapper;
import com.yogesh.learningportal.mapper.UserMapper;
import com.yogesh.learningportal.repository.CourseRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CourseService {

	private CourseRepository courseRepository;
	private CourseMapper courseMapper;
	private UserMapper userMapper;

	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	public Optional<Course> getCourseById(Long id) {
		return courseRepository.findById(id);
	}

	public Course addCourse(Course course) {
		return courseRepository.save(course);
	}

	public List<CourseResponseDto> getCoursesByAuthor(Long authorId) {
		List<Course> courses = courseRepository.getCoursesByAuthor(authorId);
		return courses.stream().map(courseMapper::convertToResponseDto).toList();
	}

	public List<CourseResponseDto> getCoursesByCategory(Long categoryId) {
		List<Course> courses = courseRepository.findByCategoryId(categoryId);
		return courses.stream().map(courseMapper::convertToResponseDto).toList();
	}

	public List<UserResponseDto> getEnrolledUsers(Long courseId) {
		List<User> enrolledUsers = courseRepository.getEnrolledUsers(courseId);
		return enrolledUsers.stream().map(userMapper::convertToDto).toList();
	}

}