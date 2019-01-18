package com.abhaya.vehicle.tracking.resource;

import javax.validation.constraints.NotNull;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteDeviationResource extends ResourceSupport 
{
	private Long deviationId;
	@NotNull
	private String citizenMobileNumber;
	
	@NotNull
	private String rcNumber;
	private String location;
	private String deviationTime;
	private String serialNumber;
	private String dlNumber;
	private String rfId;
	private String driverName;
	private String tripRequestId;
	private String geoLocation;
	private String requestId;
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
