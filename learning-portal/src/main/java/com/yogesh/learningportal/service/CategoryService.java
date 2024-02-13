package com.yogesh.learningportal.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yogesh.learningportal.dto.CourseResponseDto;
import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.mapper.CourseMapper;
import com.yogesh.learningportal.repository.CategoryRepository;
import com.yogesh.learningportal.repository.CourseRepository;

import lombok.AllArgsConstructor;

@Service
public class CategoryService{
	
	private final CategoryRepository categoryRepository;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CourseRepository courseRepository, CourseMapper courseMapper) {
        this.categoryRepository = categoryRepository;
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

	public List<Category> findAllCategories() {
		return categoryRepository.findAll();
	}

	public Category findCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}

	public Category addNewCategory(Category Category) {
		return categoryRepository.save(Category);
	}

	public Optional<Category> findCategoryById(Long id) {
		return categoryRepository.findById(id);
	}
	
	public ResponseEntity<List<CourseResponseDto>> getCoursesByCategoryId(Long categoryId) {
        List<Course> courses = categoryRepository.findByCategoryId(categoryId);
        List<CourseResponseDto> courseDtos = courses.stream()
                .map(courseMapper::convertToResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(courseDtos);
	}
}
