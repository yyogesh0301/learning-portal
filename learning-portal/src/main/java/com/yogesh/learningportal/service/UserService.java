package com.yogesh.learningportal.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.slf4j.LoggerFactory;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.Registration;
import com.yogesh.learningportal.entity.User;
import com.yogesh.learningportal.repository.CourseRepository;
import com.yogesh.learningportal.repository.RegistrationRepository;
import com.yogesh.learningportal.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final RegistrationRepository registrationRepository;
    

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void enrollCourse(Long userId, Long courseId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);

        if (optionalUser.isPresent() && optionalCourse.isPresent()) {
            User user = optionalUser.get();
            Course course = optionalCourse.get();

            List<Course> enrolledCourses = user.getEnrolledCourses();
            boolean isEnrolled = enrolledCourses.stream().anyMatch(c -> c.getId()==courseId);
            
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
            boolean isEnrolled = user.getEnrolledCourses().stream()
                                      .anyMatch(course -> course.getId() == courseId);
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

     public List<Course> getAllFavourite(Long userId){
    	 Optional<User>optionalUser=userRepository.findById(userId);
    	 User user=optionalUser.get();
    	 return user.getFavoriteCourses();
     }
     
}