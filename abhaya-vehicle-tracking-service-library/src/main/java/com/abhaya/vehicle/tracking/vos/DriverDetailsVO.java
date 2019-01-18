package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

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
public class DriverDetailsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String driverName;
	private String dlNumber;
	private Date dlExpiryDate;
	private Long contactNumber;
	private byte[] image;
	private Timestamp createdDate;
	private String gender;
	private String rfId;
	private Long cityId;
	private Long districtsId;
	private String cityName;
	private String districtsName;
	private Long stateId;
	private String stateName;
}
