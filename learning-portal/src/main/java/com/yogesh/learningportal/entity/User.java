package com.yogesh.learningportal.entity;

import java.util.List;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
public class User {

	public enum Roles {
		ADMIN, LEARNER, AUTHOR
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long id;

	@Column(name = "user_name")
	private String name;

	@Column(name = "user_email", unique = true)
	private String email;

	@Column(name = "user_password")
	private String password;

	@Column(name = "user_role")
	@Enumerated(EnumType.STRING)
	private Roles role;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinTable(name = "user_enrolled_courses", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id", referencedColumnName = "course_id"))
	private List<Course> enrolledCourses;

	@ManyToMany(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE })
	@JoinTable(name = "user_favorite_courses", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "course_id"))
	private List<Course> favoriteCourses;

}