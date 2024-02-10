package com.yogesh.learningportal.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yogesh.learningportal.dto.CourseDto;
import com.yogesh.learningportal.dto.RegistrationResponseDto;
import com.yogesh.learningportal.entity.Course;
import com.yogesh.learningportal.entity.Registration;
import com.yogesh.learningportal.entity.User;
import com.yogesh.learningportal.repository.RegistrationRepository;
import com.yogesh.learningportal.service.CourseService;


import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class RegistrationService{
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

	private CourseService courseService;
	private RegistrationRepository registrationRepository;
	
	public List<Registration> findAllRegistrations() {
		logger.info("Fetching all registrations.");
		return registrationRepository.findAll();
	}

	public Optional<Registration> findRegistrationById(Long id) {
		logger.info("Fetching registration by id.");
		return registrationRepository.findById(id);
	}

	public void saveRegistration(Registration Registration) {
		logger.info("Saving registration to RegistrationRepository.");
		registrationRepository.save(Registration);
	}

	public boolean checkRegistrationByUserAndCourse(User User, Course Course) {
		logger.info("Check registration by user and course.");
		Registration Registration = registrationRepository.findByUserAndCourse(User,Course);
		if(Registration == null)
		{
			logger.info("Registration not found.");
			return false;
		}
		else
		{
			logger.info(" - Registration found.");
			return true;
		}
	}


	public List<Registration> findRegistrationByUser(User User) {
		logger.info(" - Fetching registration by user.");
		return registrationRepository.findByUser(User);
	}

	public boolean checkRegistrationByUser(User User) {
		logger.info("Checking registration by user.");
		List<Registration> registrations = registrationRepository.findByUser(User);
		if(registrations.isEmpty())
		{
			logger.info(" Registration not found.");
			return false;
		}
		else
		{
			logger.info(" Registration found.");
			return true;
		}
	}


	public void removeRegistration(Registration Registration) {
		registrationRepository.delete(Registration);	
	}
	
}