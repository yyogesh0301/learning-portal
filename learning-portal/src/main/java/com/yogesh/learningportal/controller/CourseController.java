package com.yogesh.learningportal.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yogesh.learningportal.dto.CourseCreateDto;
import com.yogesh.learningportal.dto.CourseDto;
import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.repository.CategoryRepository;
import com.yogesh.learningportal.repository.CourseRepository;
import com.yogesh.learningportal.service.CourseService;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public Optional<Course> getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }
    
    @PostMapping
    public Course addCourse(@RequestBody CourseCreateDto courseCreateDto) {
    	String categoryName = courseCreateDto.getCategoryName();
    	Category category = categoryRepository.findByName(categoryName);
    	
    	if (category == null) {
    		Category newCategory = new Category();
    		newCategory.setName(categoryName);
    		category = categoryRepository.save(newCategory);
    	}
    	
    	System.out.println(category.toString());
    	
    	Course course = new Course();
    	course.setName(courseCreateDto.getName());
    	course.setAuthor(courseCreateDto.getAuthor());
    	course.setDesc(courseCreateDto.getDesc());
    	course.setCategory(category);
    	
    	System.out.println(course.toString());
    	return courseService.addCourse(course);
    }
    
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }
    
}
