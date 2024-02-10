package com.yogesh.learningportal.controller;


import com.yogesh.learningportal.mapper.UserMapper;

import com.yogesh.learningportal.dto.CourseDto;
import com.yogesh.learningportal.dto.UserResponseDto;
import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.Registration;
import com.yogesh.learningportal.entity.User;
import com.yogesh.learningportal.service.CourseService;
import com.yogesh.learningportal.service.RegistrationService;
import com.yogesh.learningportal.service.UserService;
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
    private final RegistrationService registrationService;

    public UserController(UserService userService,UserMapper userMapper,CourseService courseService,RegistrationService registrationService) {
        this.userService = userService;
		this.userMapper = userMapper;
		this.courseService=courseService;
		this.registrationService=registrationService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserResponseDto> userDtos = users.stream()
                .map(userMapper::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findUserById(id);
        return optionalUser.map(user -> ResponseEntity.ok(userMapper.convertToDto(user)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User Deleted");
    }

    @PostMapping("{userId}/courses/{courseId}/enroll")
    public ResponseEntity<String> enrollCourse(@PathVariable Long userId, @PathVariable Long courseId) {
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

            Course course = courseOptional.get(); // Extract the Course from Optional<Course>

            // Check if the user is already enrolled in the course
            if (user.getEnrolledCourses().contains(course)) {
                return ResponseEntity.badRequest().body("User is already enrolled in the course.");
            }
             userService.enrollCourse(userId, courseId);            // Create a new registration
            Registration registration = new Registration();
            registration.setUser(user);
            registration.setCourse(course);

            // Save the registration
            registrationService.saveRegistration(registration);

            return ResponseEntity.ok("Successfully enrolled in the course.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("An error occurred: " + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody UserResponseDto userDto) {
        User user = userService.addUser(userMapper.convertToEntity(userDto));
        UserResponseDto responseDto = userMapper.convertToDto(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PostMapping("{userId}/courses/{courseId}/favourite")
    public ResponseEntity<String> addFavouriteCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        try {
            userService.addFavouriteCourse(userId, courseId);
            return ResponseEntity.ok("Course successfully added to favorites.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    

   
}
