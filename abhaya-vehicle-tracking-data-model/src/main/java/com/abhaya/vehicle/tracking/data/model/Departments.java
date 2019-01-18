package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "departments")
@SequenceGenerator(name = "departments_seq", sequenceName = "departments_seq", allocationSize = 1)
public class Departments implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "departments_seq")
	private Long id;

	@Column(unique = true, nullable = false)
	private String name;
	private String description;
	
	@OneToMany(mappedBy = "departments", fetch = FetchType.LAZY)
	private Set<UserDetails> listOfUserDetails = new HashSet<UserDetails>();
}