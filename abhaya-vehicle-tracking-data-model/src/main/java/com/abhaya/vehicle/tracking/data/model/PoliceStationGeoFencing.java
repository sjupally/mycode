package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "police_station_geo_fencing")
@SequenceGenerator(name = "police_station_geo_fencing_seq", sequenceName = "police_station_geo_fencing_seq", initialValue = 1)
public class PoliceStationGeoFencing implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "police_station_geo_fencing_seq")
	private Long id;

	@Column(name = "latitude", nullable = false)
	private String latitude;

	@Column(name = "langitude", nullable = false)
	private String langitude;

	@Column(name = "comments")
	private String comments;

	@OneToOne(fetch =  FetchType.LAZY)
	@JoinColumn(name = "police_station_id")
	private PoliceStationDetails stationDetails;
}
