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
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@SequenceGenerator(name = "vehicle_details_seq", sequenceName = "vehicle_details_seq", allocationSize = 1)
@Table(name = "vehicle_details")
public class VehicleDetails extends AbstractAuditEntity  implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_details_seq" )
	private Long id;

	@Column(name = "vehicle_name" ,nullable = false)
	private String vehicleName;

	@Column(name = "rc_number" ,nullable = false,unique = true)
	private String rcNumber;

	@Column(name = "registration_date" ,nullable = false)
	private Date registrationDate;

	@Column(name = "rc_expiry_date" ,nullable = false)
	private Date rcExpiryDate;

	@Column(name = "is_owner" ,nullable = false)
	private Boolean isOwner;

	@Column(name = "is_device_mapped" ,nullable = false)
	private Boolean isDeviceMapped;

	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "created_date" ,updatable = false)
	private Timestamp createdDate;

	private String make;
	
	@Column(name = "device_mapped_date")
	private Timestamp deviceMappedDate;
	
	@OneToMany(mappedBy = "vehicleDetails", fetch = FetchType.LAZY)
	private Set<TripDetails> trips = new HashSet<TripDetails>();
	
	@OneToMany(mappedBy = "vehicleDetails", fetch = FetchType.LAZY)
	private Set<DriverDutyDetails> duties = new HashSet<DriverDutyDetails>();
	
	@OneToMany(mappedBy = "vehicleDetails", fetch = FetchType.LAZY)
	private Set<StatusInfo> status = new HashSet<StatusInfo>();
	
	@OneToMany(mappedBy = "vehicleDetails", fetch = FetchType.LAZY)
	private Set<WatchVehicle> watchVehicles = new HashSet<WatchVehicle>();
	
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
	@JoinColumn(name = "owner_id")
	private OwnerDetails ownerDetails;
	
}
