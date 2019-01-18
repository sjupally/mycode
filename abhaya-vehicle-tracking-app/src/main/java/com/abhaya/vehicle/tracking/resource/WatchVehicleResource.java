package com.abhaya.vehicle.tracking.resource;

import java.sql.Timestamp;

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
public class WatchVehicleResource extends ResourceSupport 
{
	private Long watchId;
	private Timestamp createdDate;
	private String vehicleId;
	private String rcNumber;
	private String serialNumber;
	private String imeiNumber;
	private String langitude;
	private String latitude;
	private String location;
	private String packetDate;
	private String packetTime;
	private String prevLatitude;
	private String prevLangitude;
	private String prevPacketTime;
	private String prevPacketDate;
	private String status;	
	private String movement;
	private String batteryStatus;
	private String ignitionStatus;
	private String engineStatus;
	private String tamperStatus;
	private String panicButtonStatus;
	private String stateName;
	private String districtName;
	private String cityName;
	private Long stateId;
	private Long districtId;
	private Long cityId;
}
