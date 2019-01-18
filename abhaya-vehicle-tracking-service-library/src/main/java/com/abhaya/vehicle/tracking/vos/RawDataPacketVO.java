package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawDataPacketVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Timestamp createdDate;
	private String serialNumber;
	private String rawData;
	private String packetDate;
	private String packetTime;
	private String imeiNumber;
	private Long id;
}
