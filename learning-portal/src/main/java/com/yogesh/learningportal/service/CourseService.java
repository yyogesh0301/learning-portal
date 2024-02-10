package com.yogesh.learningportal.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yogesh.learningportal.dto.CourseDto;
import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.repository.CourseRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CourseService{

	private CourseRepository courseRepository;
	
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}
	
	public Optional<Course> getCourseById(Long id) {
		return courseRepository.findById(id);
	}

	public Course addCourse(Course course) {
		return courseRepository.save(course);
	}

	public List<Course> findCourseByCategory(Category category) {
		return courseRepository.findByCategory(category);
	}

	public Course findCourseByAuthor(String author) {
		return courseRepository.findByAuthor(author);
	}
	
    
	public void deleteCourse(Long id) {
		courseRepository.deleteById(id);
	}
}