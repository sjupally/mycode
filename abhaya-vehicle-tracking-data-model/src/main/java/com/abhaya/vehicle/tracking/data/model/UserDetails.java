package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user_details")
@SequenceGenerator(name = "user_details_seq", sequenceName = "user_details_seq", allocationSize = 1)
public class UserDetails implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_details_seq")
	private Long id;

	@Column(name = "first_name", nullable = false)
	private String firstName;

	@Column(name = "last_name", nullable = false)
	private String lastName;

	@Column(name = "user_name", nullable = false, unique = true ,updatable = false)
	private String username;

	@Column(name = "password", nullable = false, updatable = false)
	private String password;

	@Column(name = "email_id", nullable = false)
	private String emailId;

	@Column(name = "mobile_number", nullable = false, unique = true)
	private Long mobileNumber;

	@Column(name = "is_enabled", nullable = false)
	private Boolean isEnabled;

	@Column(name = "last_password_reset_date", updatable = true)
	private Date lastPasswordResetDate;
	
	@Column(name = "user_level", updatable = true)
	private int userLevel;

	@Column(name = "created_date" ,updatable = false)
	private Timestamp createdDate;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "USER_AUTHORITY", joinColumns = {
			@JoinColumn(name = "USER_ID", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "ROLE_ID", referencedColumnName = "id") })
	private List<Roles> roles;
	
	
	@ManyToOne
	@JoinColumn(name = "state_id")
	private State state;
	
	@ManyToOne
	@JoinColumn(name = "district_id")
	private Districts districts;
	
	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Departments departments;
}
