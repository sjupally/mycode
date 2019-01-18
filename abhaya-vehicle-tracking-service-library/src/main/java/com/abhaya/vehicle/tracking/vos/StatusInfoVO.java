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
public class StatusInfoVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private Timestamp createdDate;
	private String packetTime;
	private String packetDate;
	private String imeiNumber;
	private String serialNumber;
	private Long tripId;
	private String statusId;
	private String status;
	private String rfId;
	private Long trackId;
}
