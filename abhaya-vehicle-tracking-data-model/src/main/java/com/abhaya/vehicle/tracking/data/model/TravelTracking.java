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
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "travel_tracking")
@SequenceGenerator(name = "travel_tracking_seq", sequenceName = "travel_tracking_seq", allocationSize = 1)
public class TravelTracking implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "travel_tracking_seq")
	
	private Long id;
	private String latitude;
	private String langitude;
	private String time;
	private String hdop;
	private String altitude;
	private String fix;
	private String cog;
	private String spkm;
	private String spkn;
	private String date;
	private String nsat;

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

	private String location;
		
	@ManyToOne
	@JoinColumn(name = "trip_id")
	private TripDetails tripDetails;
	
	
	
}
