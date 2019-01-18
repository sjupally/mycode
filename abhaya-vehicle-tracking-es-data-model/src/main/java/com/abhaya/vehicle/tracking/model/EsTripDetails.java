package com.abhaya.vehicle.tracking.model;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "tripdetails", type = "tripdetails", shards = 1)
public class EsTripDetails implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String requestId;
	private String sourceLatLang;
	private String destiLatLang;
	private Timestamp requestTime;
	private Timestamp closeTime;
	private String remarks;
	private String travelMode;
	private boolean isTripClosed;
	private String driverName;
	private String dlNumber;
	private String driverContactNumber;
	private String rfId;
	private String rcNumber;
	private String citizenMobileNumber;
	private String serialNumber;
	private String sourceLocation;
	private String destiLocation;
	private String driverAddress;
	private String ownerName;
	private String ownerContactNumber;
	private String vehicleAddress;
}
