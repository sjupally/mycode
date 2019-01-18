package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WatchVehicleVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
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
