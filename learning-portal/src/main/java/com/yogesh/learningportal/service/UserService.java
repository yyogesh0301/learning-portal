package com.yogesh.learningportal.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.User;
import com.yogesh.learningportal.repository.CourseRepository;
import com.yogesh.learningportal.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	public User addUser(User user) {
		return userRepository.save(user);
	}
    
	public List<User>getAllAuthors(){
		return userRepository.findAuthors();
	}
	public void deleteUser(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.getEnrolledCourses().clear();
			user.getFavoriteCourses().clear(); 
			userRepository.delete(user);
			logger.info("User with ID {} has successfully Removed", userId);
		} else {
			throw new IllegalArgumentException("User with ID " + userId + " not found.");
		}
	}
	public void enrollCourse(Long userId, Long courseId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		Optional<Course> optionalCourse = courseRepository.findById(courseId);

		if (optionalUser.isPresent() && optionalCourse.isPresent()) {
			User user = optionalUser.get();
			Course course = optionalCourse.get();

			List<Course> enrolledCourses = user.getEnrolledCourses();
			boolean isEnrolled = enrolledCourses.stream().anyMatch(c -> c.getId() == courseId);

			if (!isEnrolled) {
				user.getEnrolledCourses().add(course);
				userRepository.save(user);
				logger.info("User with ID {} has successfully enrolled in course with ID {}", userId, courseId);
			} else {
				logger.warn("User with ID {} is already enrolled in course with ID {}", userId, courseId);
			}
		} else {
			logger.error("User or course not found for IDs: {}, {}", userId, courseId);
		}
	}

	public void addFavouriteCourse(Long userId, Long courseId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			// Check if the user is enrolled in the course
			boolean isEnrolled = user.getEnrolledCourses().stream().anyMatch(course -> course.getId() == courseId);
			if (!isEnrolled) {
				throw new IllegalArgumentException("User needs to be enrolled in the course first.");
			}
			boolean isCourseAlreadyFavorite = user.getFavoriteCourses().stream()
					.anyMatch(course -> course.getId() == courseId);
			if (!isCourseAlreadyFavorite) {
				Course course = new Course();
				course.setId(courseId);
				user.getFavoriteCourses().add(course);
				userRepository.save(user);
			}
		} else {
			throw new IllegalArgumentException("User not found for ID: " + userId);
		}
	}

	public List<Course> getAllFavourite(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.get();
		return user.getFavoriteCourses();
	}

	public void removeEnrollment(Long userId, Long courseId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

			// Remove the course from the enrolled courses list
			user.getEnrolledCourses().removeIf(course -> course.getId() == courseId);

			// Save the updated user entity
			userRepository.save(user);
			logger.info("User with ID {} has successfully unenrolled in course with ID {}", userId, courseId);
		} else {
			throw new IllegalArgumentException("User with ID " + userId + " not found.");
		}
	}

	public void removeFavoriteCourse(Long userId, Long courseId) {
		Optional<User> optionalUser = userRepository.findById(userId);

		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

			// Remove the course from the user's favorite courses list
			user.getFavoriteCourses().removeIf(course -> course.getId() == courseId);

			userRepository.save(user);
			logger.info("User with ID {} has successfully removed favorite course with ID {}", userId, courseId);
		} else {
			throw new IllegalArgumentException("User with ID " + userId + " not found.");
		}
	}



}
