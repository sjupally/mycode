package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Date;

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
@SequenceGenerator(name = "driver_on_boarding_seq", sequenceName = "driver_on_boarding_seq"	,initialValue = 1)
@Table(name = "driver_on_boarding")
public class DriverOnBoarding implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "driver_on_boarding_seq")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehicle_id")
	private VehicleDetails vehicleDetails;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "driver_id")
	private DriverDetails driverDetails;
	
	@Column(name = "on_board_date")
	private Date onBoardDate;
	
	private Boolean status;
}
