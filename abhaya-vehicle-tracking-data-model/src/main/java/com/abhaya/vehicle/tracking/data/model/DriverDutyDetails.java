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
@SequenceGenerator(name = "driver_duty_details_seq", sequenceName = "driver_duty_details_seq", allocationSize = 1)
@Table(name = "driver_duty_details")
public class DriverDutyDetails implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "driver_duty_details_seq")
	private Long id;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "packet_time")
	private String packetTime;

	@Column(name = "packet_date")
	private String packetDate;
	
	
	@ManyToOne
	@JoinColumn(name = "driver_id",nullable = false)
	private DriverDetails driverDetails;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id",nullable = false)
	private VehicleDetails vehicleDetails;
	
}
