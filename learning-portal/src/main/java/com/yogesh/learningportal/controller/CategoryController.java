package com.yogesh.learningportal.controller;

import com.yogesh.learningportal.dto.CourseResponseDto;
import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.mapper.CourseMapper;
import com.yogesh.learningportal.repository.CategoryRepository;
import com.yogesh.learningportal.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

	private final CategoryService categoryService;
	private final CategoryRepository categoryRepository;
	private final CourseMapper courseMapper;

	public CategoryController(CategoryService categoryService, CategoryRepository categoryRepository,
			CourseMapper courseMapper) {
		this.categoryService = categoryService;
		this.categoryRepository = categoryRepository;
		this.courseMapper = courseMapper;
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

	@GetMapping("/{category_id}/courses")
	public ResponseEntity<List<CourseResponseDto>> getCoursesByCategoryId(
			@PathVariable("category_id") Long categoryId) {
		return categoryService.getCoursesByCategoryId(categoryId);
	}
}
