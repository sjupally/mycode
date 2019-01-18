package com.abhaya.vehicle.tracking.data.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "vehicle_live_status_view")
public class VehicleLiveStatusView 
{
	@Id
	private Long id;
	
	@Column(name = "rc_number")
	private String rcNumber;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	private String latitude;
	private String langitude;
	
	@Column(name = "state_id")
	private Long stateId;
	
	@Column(name = "city_id")
	private Long cityId;
	
	@Column(name = "district_id")
	private Long districtId;
}
