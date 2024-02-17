package com.yogesh.learningportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yogesh.learningportal.entity.Category;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.User;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	List<Course> findByCategory(Category category);

	@Query(nativeQuery = true, value = "select * from courses where author_id=:authorId")
	List<Course> getCoursesByAuthor(Long authorId);

	@Query(nativeQuery = true, value = "SELECT * FROM courses  where category_id =:categoryId")
	List<Course> findByCategoryId(Long categoryId);

	@Query("SELECT u FROM User u JOIN u.enrolledCourses c WHERE c.id = :courseId")
	List<User> getEnrolledUsers(@Param("courseId") Long courseId);

}