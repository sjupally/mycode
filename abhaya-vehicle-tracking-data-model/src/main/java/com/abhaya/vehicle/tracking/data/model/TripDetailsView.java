package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "trip_details_view")
public class TripDetailsView implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "request_id")
	private String requestId;

	@Column(name = "source_lat_lang")
	private String sourceLatLang;

	@Column(name = "desti_lat_lang")
	private String destiLatLang;

	@Column(name = "request_time")
	private Timestamp requestTime;

	@Column(name = "close_time")
	private Timestamp closeTime;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "travel_mode")
	private String travelMode;

	@Column(name = "is_trip_closed")
	private boolean isTripClosed;
	
	@Column(name = "driver_name")
	private String driverName;

	@Column(name = "driving_licence_number")
	private String dlNumber;

	@Column(name = "driver_contact_number")
	private String driverContactNumber;

	@Column(name = "rf_id")
	private String rfId;

	@Column(name = "rc_number" )
	private String rcNumber;

	@Column(name = "citizen_mobile_number")
	private String citizenMobileNumber;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "state_id")
	private Long stateId;
	
	@Column(name = "city_id")
	private Long cityId;
	
	@Column(name = "district_id")
	private Long districtId;
	
	@Column(name = "source_location", nullable = true)
	private String sourceLocation;

	@Column(name = "desti_location", nullable = true)
	private String destiLocation;
	
	@Column(name = "driver_address", nullable = true)
	private String driverAddress;
	
	@Column(name = "owner_name", nullable = true)
	private String ownerName;
	
	@Column(name = "owner_contact_number", nullable = true)
	private String ownerContactNumber;
	
	@Column(name = "vehicle_address", nullable = true)
	private String vehicleAddress;
	
}
