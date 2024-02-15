package com.yogesh.learningportal.controller;

import com.yogesh.learningportal.dto.CourseResponseDto;
import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.mapper.CourseMapper;
import com.yogesh.learningportal.repository.CategoryRepository;
import com.yogesh.learningportal.repository.CourseRepository;
import com.yogesh.learningportal.service.CategoryService;
import com.yogesh.learningportal.service.CourseService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;
	private final CourseMapper courseMapper;
	private final CourseService courseService;
	

	public CategoryController(CategoryService categoryService,
			CourseMapper courseMapper,CourseService courseService) {
		this.categoryService = categoryService;
		this.courseMapper = courseMapper;
		this.courseService=courseService;
	}

	@GetMapping
	public ResponseEntity<List<Category>> getAllCategories() {
		List<Category> categories = categoryService.findAllCategories();
		return ResponseEntity.ok(categories);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
		return categoryService.findCategoryById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Category> addCategory(@RequestBody Category category) {
		Category newCategory = categoryService.addNewCategory(category);
		return ResponseEntity.status(HttpStatus.CREATED).body(newCategory);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
		category.setId(id);
		Category updatedCategory = categoryService.addNewCategory(category);
		return ResponseEntity.ok(updatedCategory);
	}

}
