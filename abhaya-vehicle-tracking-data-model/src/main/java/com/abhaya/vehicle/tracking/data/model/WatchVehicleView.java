package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@Entity
@Table(name = "watch_vehicle_view")
public class WatchVehicleView implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "vehicle_id")
	private String vehicleId;
	
	@Column(name = "rc_number")
	private String rcNumber;

	@Column(name = "serial_number")
	private String serialNumber;

	@Column(name = "imei_number")
	private String imeiNumber;

	@Column(name = "langitude")
	private String langitude;

	@Column(name = "latitude")
	private String latitude;
	
	private String location;
	
	@Column(name = "packet_date")
	private String packetDate;
	
	@Column(name = "packet_time")
	private String packetTime;
	
	@Column(name = "prev_latitude")
	private String prevLatitude;
	
	@Column(name = "prev_langitude")
	private String prevLangitude;
	
	@Column(name = "prev_packet_time")
	private String prevPacketTime;

	@Column(name = "prev_packet_date")
	private String prevPacketDate;
	
	@Column(name = "status")
	private String status;	

	@Column(name = "movement")
	private String movement;
	
	@Column(name = "battery_status")
	private String batteryStatus;
	
	@Column(name = "ignition_status")
	private String ignitionStatus;
	
	@Column(name = "engine_status")
	private String engineStatus;
	
	@Column(name = "tamper_status")
	private String tamperStatus;
	
	@Column(name = "panic_button_status")
	private String panicButtonStatus;

	@Column(name = "state_name")
	private String stateName;

	@Column(name = "district_name")
	private String districtName;

	@Column(name = "city_name")
	private String cityName;

	@Column(name = "state_id")
	private Long stateId;

	@Column(name = "district_id")
	private Long districtId;

	@Column(name = "city_id")
	private Long cityId;
	
}
