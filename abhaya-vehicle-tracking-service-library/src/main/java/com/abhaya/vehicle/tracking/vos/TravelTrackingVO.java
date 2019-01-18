package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class TravelTrackingVO implements Serializable 
{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String latitude;
	private String langitude;
	private String time;
	private String hdop;
	private String altitude;
	private String fix;
	private String cog;
	private String spkm;
	private String spkn;
	private String date;
	private String nsat;
	private Timestamp createdDate;
	private String packetTime;
	private String packetDate;
	private String imeiNumber;
	private String serialNumber;
	private String location;

}
