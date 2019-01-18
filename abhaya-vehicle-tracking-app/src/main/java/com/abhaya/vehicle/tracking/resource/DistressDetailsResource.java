package com.abhaya.vehicle.tracking.resource;

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
public class DistressDetailsResource extends ResourceSupport 
{
	private Long distressId;
	private String createdDate;
	private String driverName;
	private String dlNumber;
	private String driverMobileNumber;
	private String rfId;
	private String rcNumber;
	private String serialNumber;
	private String citizenMobileNumber;
	private String distressLocation;
	private String tripRequestId;
	private String packetTime;
	private String packetDate;
	private String tripRequestTime;
	private boolean isClosed;
	private Long tripId;
	private String eventType;
}
