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
public class DeviceCommunicationVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String serialNumber;
	private String rcNumber;
	private Timestamp createdDate;
	private String packetDate;
	private String packetTime;
	private String latitude;
	private String langitude;
	private String location;
	private String imeiNumber;
	private String status;
	private String stateName;
	private String districtName;
	private String cityName;
	private Long stateId;
	private Long districtId;
	private Long cityId;
	private String prevPacketTime;
	private String prevPacketDate;
	private String prevLatitude;
	private String prevLangitude;
	private String movement;
	private String batteryStatus;
	private String ignitionStatus;
	private String engineStatus;
	private String tamperStatus;
	private String panicButtonStatus;
}
