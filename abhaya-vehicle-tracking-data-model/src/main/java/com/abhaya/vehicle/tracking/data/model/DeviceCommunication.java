package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@SequenceGenerator(name = "device_communication_seq", sequenceName = "device_communication_seq", allocationSize = 1)
@Table(name = "device_communication")
public class DeviceCommunication implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "device_communication_seq" )
	private Long id;

	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "created_date" ,updatable = false)
	private Timestamp createdDate;

	@Column(name = "packet_time")
	private String packetTime;

	@Column(name = "packet_date")
	private String packetDate;
	
	private String latitude;
	
	private String langitude;
	
	private String location;
	
	@Column(name = "prev_packet_time")
	private String prevPacketTime;

	@Column(name = "prev_packet_date")
	private String prevPacketDate;
	
	@Column(name = "prev_latitude")
	private String prevLatitude;
	
	@Column(name = "prev_langitude")
	private String prevLangitude;
	
	@Column(name = "imei_number")
	private String imeiNumber;
	
	private String status;
	
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

}
