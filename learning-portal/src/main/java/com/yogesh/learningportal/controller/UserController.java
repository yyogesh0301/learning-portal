package com.yogesh.learningportal.controller;

import com.yogesh.learningportal.mapper.UserMapper;
import com.yogesh.learningportal.repository.UserRepository;
import com.yogesh.learningportal.dto.EnrollmentRequestDto;
import com.yogesh.learningportal.dto.FavouriteRequestDto;
import com.yogesh.learningportal.dto.UserRequestDto;
import com.yogesh.learningportal.dto.UserResponseDto;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.User;
import com.yogesh.learningportal.service.CourseService;
import com.yogesh.learningportal.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {
	private final UserService userService;
	private final CourseService courseService;
	private final UserMapper userMapper;
	@Autowired
	private UserRepository userRepository;

	public UserController(UserService userService, UserMapper userMapper, CourseService courseService) {
		this.userService = userService;
		this.userMapper = userMapper;
		this.courseService = courseService;
	}

	@GetMapping("/authors")
	public List<User> getAuthors() {
		return userService.getAllAuthors();
	}

	@GetMapping
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		List<UserResponseDto> userDtos = users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
		return ResponseEntity.ok(userDtos);
	}

	@PostMapping
	public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userDto) {

		User user = userService.addUser(userMapper.convertRequestToEntity(userDto));
		UserResponseDto userResponseDto = userMapper.convertRequestToResponseDto(userDto);

		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
		Optional<User> optionalUser = userService.findUserById(id);
		return optionalUser.map(user -> ResponseEntity.ok(userMapper.convertToDto(user)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id) {
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isPresent()) {
			try {
				userService.deleteUser(id);
				return ResponseEntity.ok("User and related data deleted successfully.");
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("An error occurred: " + e.getMessage());
			}
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/courses/enroll")
	public ResponseEntity<String> enrollCourse(@RequestBody EnrollmentRequestDto enrollmentrequest) {

		long userId = enrollmentrequest.getUserId();
		long courseId = enrollmentrequest.getCourseId();
		try {
			// Fetch the user and course from the database
			Optional<User> userOptional = userService.findUserById(userId);
			Optional<Course> courseOptional = courseService.getCourseById(courseId);

			if (userOptional.isEmpty()) {
				return ResponseEntity.badRequest().body("User with ID " + userId + " not found.");
			}
			User user = userOptional.get();
			if (courseOptional.isEmpty()) {
				return ResponseEntity.badRequest().body("Course with ID " + courseId + " not found.");
			}

			Course course = courseOptional.get();

			// Check if the user is already enrolled in the course
			if (user.getEnrolledCourses().contains(course)) {
				return ResponseEntity.badRequest().body("User is already enrolled in the course.");
			}
			userService.enrollCourse(userId, courseId);
			return ResponseEntity.ok("Successfully enrolled in the course.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

	@PostMapping("/courses/favourite")
	public ResponseEntity<String> addFavouriteCourse(@RequestBody FavouriteRequestDto favouriteRequest) {
		long userId = favouriteRequest.getUserId();
		long courseId = favouriteRequest.getCourseId();
		try {
			userService.addFavouriteCourse(userId, courseId);
			return ResponseEntity.ok("Course successfully added to favorites.");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/courses/unenroll")
	public ResponseEntity<String> removeEnrollment(@RequestBody EnrollmentRequestDto requestDto) {
		Long userId = requestDto.getUserId();
		Long courseId = requestDto.getCourseId();
		try {
			userService.removeEnrollment(userId, courseId);
			return ResponseEntity.ok("Enrollment removed successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(" Not bale to unenroll An error occurred: " + e.getMessage());
		}
	}

	@DeleteMapping("/courses/unfavorite")
	public ResponseEntity<String> removeFavoriteCourse(@RequestBody EnrollmentRequestDto requestDto) {
		// Call the service method to remove the favorite course
		try {
			userService.removeFavoriteCourse(requestDto.getUserId(), requestDto.getCourseId());
			return ResponseEntity.ok("Favorite course removed successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
		}
	}

}
