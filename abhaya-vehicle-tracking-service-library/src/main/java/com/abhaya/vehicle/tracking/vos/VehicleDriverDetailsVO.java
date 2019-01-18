package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleDriverDetailsVO implements Serializable 
{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String vehicleName;
	private String rcNumber;
	private Date registrationDate;
	private String ownerName;
	private String ownerContactNumber;
	private Date rcExpiryDate;
	private Boolean isOwner;
	private Boolean isDeviceMapped;
	private String driverName;
	private String dlNumber;
	private Date dlExpiryDate;
	private String driverContactNumber;
	private String gender;
	private String packetTime;
	private String packetDate;
	private String serialNumber;
	private String rfId;
	private byte[] image;
}
