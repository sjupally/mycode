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
@SequenceGenerator(name = "owner_details_seq", sequenceName = "owner_details_seq" , allocationSize = 1)
@Table(name = "owner_details")
public class OwnerDetails implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_details_seq")
	private Long id;
	
	@Column(name = "owner_name" ,nullable = false)
	private String ownerName;

	@Column(name = "owner_contact_number" ,nullable = false)
	private String ownerContactNumber;
	
	@OneToMany(mappedBy = "ownerDetails", fetch = FetchType.LAZY)
	private Set<VehicleDetails> vehicles = new HashSet<VehicleDetails>();

}
