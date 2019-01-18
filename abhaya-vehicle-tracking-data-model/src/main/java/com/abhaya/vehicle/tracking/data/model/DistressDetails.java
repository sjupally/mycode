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
@Table(name = "distress_details")
@SequenceGenerator(name = "distress_details_seq", sequenceName = "distress_details_seq", allocationSize = 1)
public class DistressDetails implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "distress_details_seq")
	private Long id;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "distress_location")
	private String distressLocation;

	@Column(name = "packet_time")
	private String packetTime;

	@Column(name = "packet_date")
	private String packetDate;
	
	@Column(name = "is_closed")
	private boolean isClosed;
	

    @Column(name = "event_type")
	private String eventType;

	@ManyToOne
	@JoinColumn(name = "trip_id" ,nullable = false)
	private TripDetails tripDetails;
}