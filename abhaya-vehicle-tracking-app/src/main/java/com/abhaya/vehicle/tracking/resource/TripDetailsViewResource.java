package com.abhaya.vehicle.tracking.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
public class TripDetailsViewResource extends ResourceSupport 
{
	private Long tripId;
	private String requestId;
	private String sourceLatLang;
	private String destiLatLang;
	private String requestTime;
	private String closeTime;
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