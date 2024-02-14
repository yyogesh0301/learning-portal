package com.yogesh.learningportal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yogesh.learningportal.dto.CourseRequestDto;
import com.yogesh.learningportal.dto.CourseResponseDto;
import com.yogesh.learningportal.dto.EnrollmentRequestDto;
import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.User;
import com.yogesh.learningportal.mapper.CourseMapper;
import com.yogesh.learningportal.repository.CategoryRepository;
import com.yogesh.learningportal.repository.CourseRepository;
import com.yogesh.learningportal.repository.UserRepository;
import com.yogesh.learningportal.service.CategoryService;
import com.yogesh.learningportal.service.CourseService;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/courses")
public class CourseController {

	private CourseService courseService;
	private CategoryRepository categoryRepository;
	private CourseRepository courseRepository;
	private UserRepository userRepository;
	private CourseMapper courseMapper;

	public CourseController(CourseService courseService, CategoryRepository categoryRepository, CourseRepository courseRepository,
			UserRepository userRepository,CourseMapper courseMapper) {
		this.courseService=courseService;
		this.categoryRepository = categoryRepository;
		this.courseRepository=courseRepository;
		this.userRepository=userRepository;
		this.courseMapper=courseMapper;
	}
	
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	@GetMapping
	public List<CourseResponseDto> getAllCourses() {
		List<Course> courses = courseService.getAllCourses();
		return courses.stream().map(courseMapper::convertToResponseDto).collect(Collectors.toList());
	}
	

	@GetMapping("/{id}")
	public CourseResponseDto getCourseById(@PathVariable Long id) {
		Optional<Course> courseOptional = courseService.getCourseById(id);
		return courseOptional.map(courseMapper::convertToResponseDto).orElse(null);
	}

	@PostMapping
	public ResponseEntity<CourseResponseDto> addCourse(@RequestBody CourseRequestDto courseCreateDto) {
		String categoryName = courseCreateDto.getCategoryName();
		Category category = categoryRepository.findByName(categoryName);

		// If category doesn't exist, create a new one
		if (category == null) {
			category = new Category();
			category.setName(categoryName);
			category = categoryRepository.save(category);
		}

		String authorName = courseCreateDto.getAuthorName();
		User author = userRepository.findByName(authorName);
		

		// Check if author exists
		if (author == null || author.getRole() != User.Roles.AUTHOR) {
			logger.info("Author Not Found");
		     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

		Course course = courseMapper.convertToEntity(courseCreateDto);
		course.setCategory(category);
		course.setAuthor(author);
		courseService.addCourse(course);
		CourseResponseDto courseResponseDto = courseMapper.convertToResponseDto(course);
		logger.info("Course Added Succesfully");
		return ResponseEntity.ok(courseResponseDto);
	}
	

}
