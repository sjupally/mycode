package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
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
public class DistressDetailsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Timestamp createdDate;
	private String driverName;
	private String dlNumber;
	private String driverMobileNumber;
	private String rfId;
	private String rcNumber;
	private String serialNumber;
	private String citizenMobileNumber;
	private String distressLocation;
	private String tripRequestId;
	private String packetTime;
	private String packetDate;
	private Timestamp tripRequestTime;
	private boolean isClosed;
	private Long tripId;
	private String eventType;
}
