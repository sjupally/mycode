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
@JsonInclude(Include.ALWAYS)
public class StatusInfoResource extends ResourceSupport 
{
	private String ignitionStatus;
	private String vehicleStagnantStatus;
	private String iotDeviceDetachedStatus;
	private String panicButtonStatus;
	private String vehicleBatteryStatus;
	private String deviceBatteryStatus;
	private String engineStatus;
	private String vehicleIdealStatus;
	private String vehicleParkingStatus;
	private String devicePowerDisconnectStatus;
	private String deviceTamperStatus;
	private String panicButtonForEngineSwithOff;
	private String overSpeed;
	private String sleepMode;
	private String serialNumber;
	private String packetTime;
	private String packetDate;
	private String rcNumber;
	private String dlNumber;
	private String rfId;
	private Long statusId;
	private Long trackId;
}
