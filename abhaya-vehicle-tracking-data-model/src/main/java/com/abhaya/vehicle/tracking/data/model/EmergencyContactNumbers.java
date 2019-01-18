package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@SequenceGenerator(name = "emergency_contact_numbers_seq", sequenceName = "emergency_contact_numbers_seq", allocationSize = 1)
@Table(name = "emergency_contact_numbers")
public class EmergencyContactNumbers implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emergency_contact_numbers_seq")
	private Long id;

	@Column(name = "emergency_contact_number")
	private String emergencyContactNumber;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "citizen_id")
	private CitizenDetails citizenDetails;
}
