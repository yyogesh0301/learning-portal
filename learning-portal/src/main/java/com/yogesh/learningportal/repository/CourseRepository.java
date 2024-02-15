package com.yogesh.learningportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	List<Course> findByCategory(Category Category);
	
	@Query(nativeQuery = true,value="select * from courses where author_id=:author_id")
	List<Course>getCoursesByAuthor(long author_id);
	
	@Query(nativeQuery = true,value = "SELECT * FROM courses  where category_id =:categoryId")
	List<Course>findByCategoryId(long categoryId);
	
	// Join Table @Query(nativeQuery = true,value="SELECT courses.course_id, courses.course_name, courses.category_id, categories.category_name FROM courses LEFT OUTER JOIN categories ON courses.category_id = categories.category_id order by course_id")
}