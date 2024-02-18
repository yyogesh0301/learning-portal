package com.yogesh.learningportal.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {

	private final CategoryRepository categoryRepository;

	public List<Category> findAllCategories() {
		return categoryRepository.findAll();
	}

	public Category findCategoryByName(String name) {
		return categoryRepository.findByName(name);
	}

	public Category addNewCategory(Category category) {
		return categoryRepository.save(category);
	}

	public Optional<Category> findCategoryById(Long id) {
		return categoryRepository.findById(id);
	}

}
