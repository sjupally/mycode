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
@Table(name = "route_deviation")
@SequenceGenerator(name = "route_deviation_seq", sequenceName = "route_deviation_seq", allocationSize = 1)
public class RouteDeviation implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "route_deviation_seq")
	private Long id;
	
	@Column(name = "deviation_time")
	private Timestamp deviationTime;
	
	private String location;
	
	@Column(name = "created_date")
	private Timestamp createdDate;
	
	@Column(name = "geo_location")
	private String geoLocation;
	
	@ManyToOne
	@JoinColumn(name = "trip_id")
	private TripDetails tripDetails;
	
}