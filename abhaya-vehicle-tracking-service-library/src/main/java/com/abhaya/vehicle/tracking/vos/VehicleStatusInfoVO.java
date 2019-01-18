package com.abhaya.vehicle.tracking.vos;

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
public class VehicleStatusInfoVO 
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
	private Long id;
	private Long trackId;
	
}
