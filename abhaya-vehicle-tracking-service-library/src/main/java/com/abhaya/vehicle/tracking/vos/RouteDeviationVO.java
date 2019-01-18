package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteDeviationVO implements Serializable
{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String citizenMobileNumber;
	private String rcNumber;
	private String location;
	private Timestamp deviationTime;
	private String serialNumber;
	private String dlNumber;
	private String rfId;
	private String driverName;
	private String requestId;
	private String geoLocation;
	private String driverContactNumber;
	private String ownerName;
	private String ownerContactNumber;
	private String sLatLng;
	private String dLatLng;
	private String sLocation;
	private String dLocation;
	private Long stateId;
	private Long districtId;
	private Long cityId;
	private Long tripId;
}
