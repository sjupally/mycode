package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
@Table(name = "trip_details")
@SequenceGenerator(name = "trip_details_seq", sequenceName = "trip_details_seq", allocationSize = 1)
public class TripDetails implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trip_details_seq")
	private Long id;

	@Column(name = "request_id", nullable = false)
	private String requestId;

	@Column(name = "source_lat_lang", nullable = false)
	private String sourceLatLang;

	@Column(name = "desti_lat_lang", nullable = false)
	private String destiLatLang;

	@Column(name = "source_location", nullable = true)
	private String sourceLocation;

	@Column(name = "desti_location", nullable = true)
	private String destiLocation;

	@Column(name = "request_time", nullable = false)
	private Timestamp requestTime;

	@Column(name = "close_time")
	private Timestamp closeTime;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "travel_mode")
	private String travelMode;

	@Column(name = "is_trip_closed" ,nullable = false)
	private boolean isTripClosed;
	
	@OneToMany(mappedBy = "tripDetails", fetch = FetchType.LAZY)
	private Set<TravelTracking> tracks = new HashSet<TravelTracking>();
	
	@OneToMany(mappedBy = "tripDetails", fetch = FetchType.LAZY)
	private Set<RouteDeviation> deviations = new HashSet<RouteDeviation>();
	
	@OneToMany(mappedBy = "tripDetails", fetch = FetchType.LAZY)
	private Set<DistressDetails> panics = new HashSet<DistressDetails>();
	

	@ManyToOne
	@JoinColumn(name = "driver_id" ,nullable = false)
	private DriverDetails driverDetails;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id" ,nullable = false)
	private VehicleDetails vehicleDetails;
	
	@ManyToOne
	@JoinColumn(name = "citizen_id" ,nullable = false)
	private CitizenDetails citizenDetails;
	
}
