package com.abhaya.vehicle.tracking.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@JsonInclude(Include.NON_NULL)
public class TravelTrackingResource extends ResourceSupport 
{
	
	private Long sourceId;
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
	private String createdDate;
	private String packetTime;
	private String packetDate;
	private String imeiNumber;
	private String serialNumber;
	private String location;
}
