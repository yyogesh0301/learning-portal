package com.yogesh.learningportal.service;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Bytes;
import com.yogesh.learningportal.dto.UserResponseDto;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.User;
import com.yogesh.learningportal.repository.CourseRepository;
import com.yogesh.learningportal.repository.UserRepository;

@Service
public class UserService {
	private final CourseService courseService;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final CourseRepository courseRepository;

	public UserService(CourseService courseService, UserRepository userRepository, CourseRepository courseRepository) {
		this.courseService = courseService;
		this.userRepository = userRepository;
		this.courseRepository = courseRepository;
	}

	private static final byte[] FIXED_SALT = "YourFixedSaltHere".getBytes(StandardCharsets.UTF_8);

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public Optional<User> findUserById(Long id) {
		return userRepository.findById(id);
	}

	public User addUser(User user) {
		String password = user.getPassword();

		HashCode hash = hashPassword(password);

		String hashedPassword = hash.toString();
		user.setPassword(hashedPassword);

		return userRepository.save(user);
	}

	public User findByName(String name) {
		return userRepository.findByName(name);
	}

	public void deleteUser(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			List<Course> tempCourses = courseRepository.getCoursesByAuthor(userId);

			user.getEnrolledCourses().clear();
			user.getFavoriteCourses().clear();
			userRepository.save(user);

			for (Course it : tempCourses) {
				List<UserResponseDto> unenrollUser = courseService.getEnrolledUsers(it.getId());

				for (UserResponseDto userDetails : unenrollUser) {
					removeEnrollment(userDetails.getId(), it.getId());
					removeFavoriteCourse(userDetails.getId(), it.getId());
				}
				courseRepository.delete(it);
			}

			userRepository.delete(user);
			logger.info("User with ID {} has successfully Removed", userId);
		} else {
			throw new IllegalArgumentException("User Not found: " + userId);
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
			logger.info("User {} favourite course with {} has been successfully added", userId, courseId);
		} else {
			throw new IllegalArgumentException("User not found for ID: " + userId);
		}
	}

	public List<Course> getAllFavourite(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return user.getFavoriteCourses();
		} else {
			return Collections.emptyList();
		}

	}

	public void removeEnrollment(Long userId, Long courseId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();

			// Remove the course from the enrolled courses list
			user.getEnrolledCourses().removeIf(course -> course.getId() == courseId);
			user.getFavoriteCourses().removeIf(course -> course.getId() == courseId);

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

	public Optional<User> login(String email, String password) {
		User user = userRepository.findByEmail(email);
		if (user != null) {
			HashCode hashedPassword = hashPassword(password);
			if (hashedPassword.toString().equals(user.getPassword())) {
				return Optional.of(user);
			}
		}
		return Optional.empty();
	}

	private HashCode hashPassword(String password) {
		HashFunction hashFunction = Hashing.sha256();
		return hashFunction.hashBytes(Bytes.concat(password.getBytes(StandardCharsets.UTF_8), FIXED_SALT));
	}
}
