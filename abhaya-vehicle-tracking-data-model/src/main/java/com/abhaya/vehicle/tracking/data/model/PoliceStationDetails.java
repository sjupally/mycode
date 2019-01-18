package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.abhaya.vehicle.tracking.utils.DateUtils;

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
@SequenceGenerator(name = "police_station_details_seq", sequenceName = "police_station_details_seq", initialValue = 1)
@Table(name = "police_station_details")
public class PoliceStationDetails implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(generator = "police_station_details_seq")
	private Long id;

	@Column(name = "station_name", nullable = false, unique = true)
	private String stationName;

	@Column(name = "division")
	private String division;

	@Column(name = "contact_number", nullable = false)
	private String contactNumber;

	@Column(name = "mobile_number", nullable = false, unique = true)
	private String mobileNumber;

	@Column(name = "email_id")
	private String emailId;

	@Column(name = "address", nullable = false)
	private String address;

	@Column(name = "latitude")
	private String latitude;

	@Column(name = "langitude")
	private String langitude;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "city_id")
	private City city;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "district_id")
	private Districts districts;

	@Column(name = "pincode", nullable = false)
	private Long pincode;

	@Column(name = "created_on", updatable = false)
	private Timestamp createdOn = DateUtils.getCurrentSystemTimestamp();
}
