package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;

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
public class GPSDataVO implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private String latitude;
	private String langitude;
	private String utcTime;
	private String hdop;
	private String altitude;
	private String fix;
	private String cog;
	private String spkm;
	private String spkn;
	private String date;
	private String nsat;
	private String packetTime;
	private String packetDate;
	private String imeiNumber;
	private String serialNumber;

}
