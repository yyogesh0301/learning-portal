package com.yogesh.learningportal.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.Registration;
import com.yogesh.learningportal.entity.User;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long>{
	
	public Registration findByUserAndCourse(User user,Course course);
	public List<Registration> findByUser(User user);
}