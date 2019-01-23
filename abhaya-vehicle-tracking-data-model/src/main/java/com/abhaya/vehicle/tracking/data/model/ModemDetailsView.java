package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

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
@Table(name = "modem_details_view")
public class ModemDetailsView implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "imei_number" ,unique = true)
	private String imeiNumber;

	@Column(name = "sim_number")
	private String simNumber;

	@Column(name = "imsi_number")
	private String imsiNumber;

	@Column(name = "id_address")
	private String ipAddress;

	@Column(name = "signal_strength")
	private String signalStrength;
	
	@Column(name = "version")
	private String version;
	
	@Column(name = "serial_number")
	private String serialNumber;
	
	@Column(name = "created_date" ,updatable = false)
	private Timestamp createdDate;
	
	@Column(name = "mobile_number")
	private String mobileNumber;
	
	@Column(name = "updated_date")
	private Timestamp updatedDate;
	
	@Column(name = "state_id")
	private Long stateId;

	@Column(name = "district_id")
	private Long districtId;

	@Column(name = "city_id")
	private Long cityId;
	
	@Column(name = "state_name")
	private String stateName;

	@Column(name = "district_name")
	private String districtName;

	@Column(name = "city_name")
	private String cityName;
	
}
