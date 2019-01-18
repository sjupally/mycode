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
@Table(name = "route_deviation_details_view")
public class RouteDeviationDetailsView implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	
	@Column(name = "deviation_time")
	private Timestamp deviationTime;
	
	private String location;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "geo_location")
	private String geoLocation;
	
	@Column(name = "trip_id")
	private Long tripId;
	
	@Column(name = "request_id")
	private String requestId;
	
	@Column(name = "source_lat_lang")
	private String sLatLng;
	
	@Column(name = "desti_lat_lang")
	private String dLatLng;
	
	@Column(name = "source_location")
	private String sLocation;
	
	@Column(name = "desti_location")
	private String dLocation;
	
	@Column(name = "rc_number")
	private String rcNumber;

	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "state_id")
	private Long stateId;
	
	@Column(name = "district_id")
	private Long districtId;
	
	@Column(name = "city_id")
	private Long cityId;
	
	@Column(name = "driver_name")
	private String driverName;

	@Column(name = "dl_num")
	private String dlNumber;

	@Column(name = "driver_contact_number")
	private String driverContactNumber;
	
	@Column(name = "rf_id")
	private String rfId;
	
	@Column(name = "owner_name")
	private String ownerName;
	
	@Column(name = "owner_contact_number")
	private String ownerContactNumber;

	@Column(name = "citizen_contact_number")
	private String citizenContactNumber;
}
