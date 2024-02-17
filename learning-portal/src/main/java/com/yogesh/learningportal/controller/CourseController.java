package com.yogesh.learningportal.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogesh.learningportal.dto.CourseRequestDto;
import com.yogesh.learningportal.dto.CourseResponseDto;
import com.yogesh.learningportal.dto.UserResponseDto;
import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.User;
import com.yogesh.learningportal.mapper.CourseMapper;
import com.yogesh.learningportal.service.CategoryService;
import com.yogesh.learningportal.service.CourseService;
import com.yogesh.learningportal.service.UserService;

@RestController
@RequestMapping("api/courses")
public class CourseController {

	private CourseService courseService;
	private CategoryService categoryService;
	private UserService userService;
	private CourseMapper courseMapper;

	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

	public CourseController(CourseService courseService, CategoryService categoryService, UserService userService,
			CourseMapper courseMapper) {
		this.courseService = courseService;
		this.categoryService = categoryService;
		this.userService = userService;
		this.courseMapper = courseMapper;
	}

	@GetMapping
	public List<CourseResponseDto> getAllCourses() {
		List<Course> courses = courseService.getAllCourses();
		return courses.stream().map(courseMapper::convertToResponseDto).toList();
	}

	@GetMapping("/{id}")
	public CourseResponseDto getCourseById(@PathVariable Long id) {
		try {
			Optional<Course> courseOptional = courseService.getCourseById(id);
			if (courseOptional.isPresent()) {
				Course course = courseOptional.get();
				return courseMapper.convertToResponseDto(course);
			} else {
				return null;
			}

		} catch (NullPointerException error) {
			return null;
		}
	}

	@GetMapping("/author/{id}")
	public ResponseEntity<List<CourseResponseDto>> getCoursesByAuthors(@PathVariable Long id) {
		List<CourseResponseDto> dtos = courseService.getCoursesByAuthor(id);
		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/category/{id}")
	public ResponseEntity<List<CourseResponseDto>> getCoursesByCategory(@PathVariable Long id) {
		List<CourseResponseDto> dtos = courseService.getCoursesByCategory(id);
		return ResponseEntity.ok(dtos);
	}

	@GetMapping("/{course_id}/users")
	public ResponseEntity<List<UserResponseDto>> getEnrolledUsers(@PathVariable("course_id") Long courseId) {
		List<UserResponseDto> dtos = courseService.getEnrolledUsers(courseId);
		return ResponseEntity.ok(dtos);
	}

	@PostMapping
	public ResponseEntity<CourseResponseDto> addCourse(@RequestBody CourseRequestDto courseCreateDto) {
		String categoryName = courseCreateDto.getCategoryName();
		Category category = categoryService.findCategoryByName(categoryName);
		// If category doesn't exist, create a new one
		if (category == null) {
			category = new Category();
			category.setName(categoryName);
			category = categoryService.addNewCategory(category);
		}

		String authorName = courseCreateDto.getAuthorName();
		User author = userService.findByName(authorName);

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
