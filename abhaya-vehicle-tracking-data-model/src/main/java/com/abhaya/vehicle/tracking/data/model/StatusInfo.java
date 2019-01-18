package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
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
@Table(name = "status_info")
@SequenceGenerator(name = "status_info_seq", sequenceName = "status_info_seq", allocationSize = 1)
public class StatusInfo implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "status_info_seq")
	private Long id;
	
	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "packet_time")
	private String packetTime;

	@Column(name = "packet_date")
	private String packetDate;

	@Column(name = "imei_number")
	private String imeiNumber;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "rf_id")
	private String rfId;

	@Column(name = "status_param")
	private String statusParam;
	
	@Column(name = "ignition_status")
	private String ignitionStatus;
	
	@Column(name = "vehicle_stagnant_status")
	private String vehicleStagnantStatus;
	
	@Column(name = "iot_device_detached_status")
	private String iotDeviceDetachedStatus;
	
	@Column(name = "panic_button_status")
	private String panicButtonStatus;
	
	@Column(name = "vehicle_battery_status")
	private String vehicleBatteryStatus;
	
	@Column(name = "device_battery_status")
	private String deviceBatteryStatus;
	
	@Column(name = "engine_status")
	private String engineStatus;
	
	@Column(name = "vehicle_ideal_status")
	private String vehicleIdealStatus;
	
	@Column(name = "vehicle_parking_status")
	private String vehicleParkingStatus;
	
	@Column(name = "device_power_disconnect_status")
	private String devicePowerDisconnectStatus;
	
	@Column(name = "device_tamper_status")
	private String deviceTamperStatus;
	
	@Column(name = "panic_button_for_engine_swith_off")
	private String panicButtonForEngineSwithOff;
	
	@Column(name = "over_speed")
	private String overSpeed;
	
	@Column(name = "sleep_mode")
	private String sleepMode;

	@Column(name = "track_id")
	private Long trackId;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private VehicleDetails vehicleDetails;
	
	@ManyToOne
	@JoinColumn(name = "driver_id")
	private DriverDetails driverDetails;
	
}
