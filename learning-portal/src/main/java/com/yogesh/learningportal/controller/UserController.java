package com.yogesh.learningportal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogesh.learningportal.dto.EnrollmentRequestDto;
import com.yogesh.learningportal.dto.FavouriteRequestDto;
import com.yogesh.learningportal.dto.UserRequestDto;
import com.yogesh.learningportal.dto.UserResponseDto;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.User;
import com.yogesh.learningportal.mapper.UserMapper;
import com.yogesh.learningportal.service.CourseService;
import com.yogesh.learningportal.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserController {
	private final UserService userService;
	private final CourseService courseService;
	private final UserMapper userMapper;

	@GetMapping
	public ResponseEntity<List<UserResponseDto>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		List<UserResponseDto> userDtos = users.stream().map(userMapper::convertToDto).toList();
		return ResponseEntity.ok(userDtos);
	}

	@PostMapping
	public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserRequestDto userDto) {

		User user = userService.addUser(userMapper.convertRequestToEntity(userDto));
		UserResponseDto userResponseDto = userMapper.convertToDto(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
		Optional<User> optionalUser = userService.findUserById(id);
		return optionalUser.map(user -> ResponseEntity.ok(userMapper.convertToDto(user)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> removeUser(@PathVariable Long id) {
		Optional<User> optionalUser = userService.findUserById(id);
		if (optionalUser.isPresent()) {
			try {
				userService.deleteUser(id);
				return ResponseEntity.ok("User and related data deleted successfully.");
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body("User Not Found Error Deleting" + e.getMessage());
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
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not enroll " + e.getMessage());
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
					.body(" Not able to unenroll An error occurred: " + e.getMessage());
		}
	}

	@DeleteMapping("/courses/unfavorite")
	public ResponseEntity<String> removeFavoriteCourse(@RequestBody EnrollmentRequestDto requestDto) {
		try {
			userService.removeFavoriteCourse(requestDto.getUserId(), requestDto.getCourseId());
			return ResponseEntity.ok("Favorite course removed successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to enroll ");
		}
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody User user) {
		String email = user.getEmail();
		String password = user.getPassword();

		Optional<User> userT = userService.login(email, password);
		if (userT.isPresent()) {
			return ResponseEntity.ok("Login succesfull");
		} else {
			return ResponseEntity.ok("Login failed");
		}

	}

}
