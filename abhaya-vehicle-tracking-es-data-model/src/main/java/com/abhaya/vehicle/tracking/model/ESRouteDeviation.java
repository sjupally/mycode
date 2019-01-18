package com.abhaya.vehicle.tracking.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

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
@Document(indexName = "routedeviation", type = "routedeviation", shards = 1)
public class ESRouteDeviation implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String createdDate;
	private String driverName;
	private String dlNumber;
	private String driverContactNumber;
	private String rcNumber;
	private String ownerName;
	private String ownerContactNumber;
	private String rfId;
	private String serialNumber;
	private String deviationTime;
	private String location;
	private String geoLocation;
	private String tripId;
	private String triprequestId;
	private String source;
	private String destination;
	private String citizenMobileNumber;
}