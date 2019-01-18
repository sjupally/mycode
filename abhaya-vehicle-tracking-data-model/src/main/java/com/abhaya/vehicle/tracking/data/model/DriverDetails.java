package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
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

import com.abhaya.vehicle.tracking.data.audit.model.AbstractAuditEntity;

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
@SequenceGenerator(name = "driver_details_seq", sequenceName = "driver_details_seq", allocationSize = 1)
@Table(name = "driver_details")
public class DriverDetails extends AbstractAuditEntity  implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_details_seq")
	private Long id;

	@Column(name = "driver_name", nullable = false)
	private String driverName;

	@Column(name = "driving_licence_number", nullable = false)
	private String dlNumber;

	@Column(name = "dl_expiry_date", nullable = false)
	private Date dlExpiryDate;

	@Column(name = "contact_number", nullable = false)
	private String contactNumber;

	private byte[] image;
	
	private String gender;
	
	@Column(name = "rf_id")
	private String rfId;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@OneToMany(mappedBy = "driverDetails", fetch = FetchType.LAZY)
	private Set<DriverDutyDetails> duties = new HashSet<DriverDutyDetails>();
	
	@OneToMany(mappedBy = "driverDetails", fetch = FetchType.LAZY)
	private Set<TripDetails> trips = new HashSet<TripDetails>();
	
	@OneToMany(mappedBy = "driverDetails", fetch = FetchType.LAZY)
	private Set<StatusInfo> status = new HashSet<StatusInfo>();
	
	
	@ManyToOne
	@JoinColumn(name = "state_id")
	private State state;
	
	@ManyToOne
	@JoinColumn(name = "district_id")
	private Districts districts;
	
	@ManyToOne
	@JoinColumn(name = "city_id")
	private City city;
}