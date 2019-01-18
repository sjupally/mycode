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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@SequenceGenerator(name = "city_seq", sequenceName = "city_seq",  allocationSize = 1)
@Table(name = "city")
public class City implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_seq")
	private Long id;

	@Column(nullable = false)
	private String name;

	private String code;
	
	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
	private Set<DriverDetails> drivers = new HashSet<DriverDetails>();
	
	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
	private Set<VehicleDetails> vehicles = new HashSet<VehicleDetails>();
	
	@OneToMany(mappedBy = "city", fetch = FetchType.LAZY)
	private Set<UserDetails> listOfUserDetails = new HashSet<UserDetails>();

	@ManyToOne
	@JoinColumn(name = "state_id")
	private State state;
	
	@ManyToOne
	@JoinColumn(name = "district_id")
	private Districts districts;
	
	
}