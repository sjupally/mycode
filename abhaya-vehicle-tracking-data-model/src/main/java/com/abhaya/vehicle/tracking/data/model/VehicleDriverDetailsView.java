package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Date;
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
@Table(name = "vehicle_driver_details_view")
public class VehicleDriverDetailsView implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	@Column(name = "vehicle_name")
	private String vehicleName;

	@Column(name = "rc_number")
	private String rcNumber;

	@Column(name = "registration_date")
	private Date registrationDate;

	@Column(name = "owner_name")
	private String ownerName;

	@Column(name = "owner_contact_number")
	private String ownerContactNumber;

	@Column(name = "rc_expiry_date")
	private Date rcExpiryDate;

	@Column(name = "is_owner")
	private Boolean isOwner;

	@Column(name = "is_device_mapped")
	private Boolean isDeviceMapped;

	@Column(name = "serial_number")
	private String serialNumber;

	@Column(name = "created_date")
	private Timestamp createdDate;

	@Column(name = "driver_name")
	private String driverName;

	@Column(name = "driving_licence_number")
	private String dlNumber;

	@Column(name = "dl_expiry_date")
	private Date dlExpiryDate;

	@Column(name = "driver_contact_number")
	private String driverContactNumber;

	private String gender;
	
	@Column(name = "packet_date")
	private String packetDate;
	
	@Column(name = "packet_time")
	private String packetTime;
	
	@Column(name = "rf_id")
	private String rfId;

	@Column(name = "image")
	private byte[] image;
	
	@Column(name = "state_id")
	private Long stateId;
	
	@Column(name = "district_id")
	private Long districtId;
	
	@Column(name = "city_id")
	private Long cityId;
}
