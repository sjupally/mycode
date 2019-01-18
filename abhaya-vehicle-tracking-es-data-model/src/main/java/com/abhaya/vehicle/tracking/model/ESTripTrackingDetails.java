package com.abhaya.vehicle.tracking.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(indexName = "triptracking", type = "triptracking", shards = 1)
public class ESTripTrackingDetails implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	@Id
	private String id;
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
	private String packetTime;
	private String packetDate;
	private String imeiNumber;
	@Field(fielddata = true)
	private String serialNumber;
	private String tripId;
	private String location;
}
