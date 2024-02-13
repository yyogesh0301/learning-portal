package com.yogesh.learningportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long>{
	List<Course> findByCategory(Category Category);	
}