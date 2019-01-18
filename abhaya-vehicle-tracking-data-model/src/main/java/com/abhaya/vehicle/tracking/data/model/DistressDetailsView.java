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
@Table(name = "distress_details_view")
public class DistressDetailsView implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "distress_location")
	private String distressLocation;

	@Column(name = "packet_time")
	private String packetTime;

	@Column(name = "packet_date")
	private String packetDate;

	@Column(name = "is_closed")
	private boolean isClosed;

	@Column(name = "trip_request_id")
	private String tripRequestId;

	@Column(name = "serial_number")
	private String serialNumber;

	@Column(name = "rc_number")
	private String rcNumber;

	@Column(name = "dl_number")
	private String dlNumber;

	@Column(name = "driver_name")
	private String driverName;
	
	@Column(name = "driver_mobile_number")
	private String driverMobileNumber;

	@Column(name = "rf_id")
	private String rfId;

	@Column(name = "citizen_mobile_number")
	private String citizenMobileNumber;

	@Column(name = "trip_request_time")
	private Timestamp tripRequestTime;
	
	@Column(name = "trip_id")
	private Long tripId;
	
	@Column(name = "state_id")
	private Long stateId;
	
	@Column(name = "district_id")
	private Long districtId;
	
	@Column(name = "city_id")
	private Long cityId;
	
	@Column(name = "event_type")
	private String eventType;
}
