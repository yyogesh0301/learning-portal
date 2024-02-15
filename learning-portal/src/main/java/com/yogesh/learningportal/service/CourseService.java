package com.yogesh.learningportal.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yogesh.learningportal.dto.CourseResponseDto;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.mapper.CourseMapper;
import com.yogesh.learningportal.repository.CategoryRepository;
import com.yogesh.learningportal.repository.CourseRepository;
import com.yogesh.learningportal.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
public class CourseService {

	private static final Logger logger=LoggerFactory.getLogger(CourseService.class);
	private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public CourseService(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }
	
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}

	public Optional<Course> getCourseById(Long id) {
		return courseRepository.findById(id);
	}

	public Course addCourse(Course course) {
		return courseRepository.save(course);
	}
	public List<CourseResponseDto> getCoursesByAuthor(long authorId) {
        List<Course> courses = courseRepository.getCoursesByAuthor(authorId);
        return courses.stream().map(courseMapper::convertToResponseDto).collect(Collectors.toList());
    }

    public List<CourseResponseDto> getCoursesByCategory(long categoryId) {
        List<Course> courses = courseRepository.findByCategoryId(categoryId);
        return courses.stream().map(courseMapper::convertToResponseDto).collect(Collectors.toList());
    }

}