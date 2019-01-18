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
@SequenceGenerator(name = "state_seq", sequenceName = "state_seq", allocationSize = 1)
@Table(name = "state")
public class State implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "state_seq")
    private Long id;
	
	@Column(nullable = false , unique = true)
	private String name;
	
	private String code;
	
	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private Set<Districts> districts = new HashSet<Districts>();
	
	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private Set<DriverDetails> drivers = new HashSet<DriverDetails>();
	
	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private Set<VehicleDetails> vehicles = new HashSet<VehicleDetails>();
	
	@OneToMany(mappedBy = "state", fetch = FetchType.LAZY)
	private Set<UserDetails> listOfUserDetails = new HashSet<UserDetails>();
}
