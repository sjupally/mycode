package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDetailsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String vehicleName;
	private String rcNumber;
	private Date registrationDate;
	private String ownerName;
	private String ownerContactNumber;
	private Timestamp createdDate;
	private String cityName;
	private String serialNumber;
	private Boolean isOwner;
	private String make;
	private Long cityId;
	private Long districtsId;
	private String districtsName;
	private Date rcExpiryDate;
	private Long stateId;
	private String stateName;
	private Timestamp deviceMappedDate;
	private boolean isDeviceMapped;
}
