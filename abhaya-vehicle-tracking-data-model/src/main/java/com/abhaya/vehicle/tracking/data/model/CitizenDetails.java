package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
@SequenceGenerator(name = "citizen_details_seq", sequenceName = "citizen_details_seq", allocationSize = 1)
@Table(name = "citizen_details")
public class CitizenDetails implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "citizen_details_seq" )
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "mobile_number", unique = true, nullable = true)
	private String mobileNumber;

	@Column(name = "created_on")
	private Timestamp createdOn;

	@Column(name = "modified_on")
	private Timestamp modifiedOn;

	@Column(name = "device_id")
	private String deviceId;

	@Column(name = "device_type")
	private String deviceType;
	
	@OneToMany(mappedBy = "citizenDetails", fetch = FetchType.LAZY)
	private Set<EmergencyContactNumbers> emergencyContacts = new HashSet<EmergencyContactNumbers>();
	
	@OneToMany(mappedBy = "citizenDetails", fetch = FetchType.LAZY)
	private Set<TripDetails> trips = new HashSet<TripDetails>();
	
	@OneToMany(mappedBy = "citizenDetails", fetch = FetchType.LAZY)
	private Set<Alerts> alerts = new HashSet<Alerts>();
}
