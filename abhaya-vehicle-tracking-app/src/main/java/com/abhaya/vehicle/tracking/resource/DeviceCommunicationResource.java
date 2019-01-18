package com.abhaya.vehicle.tracking.resource;

import java.sql.Timestamp;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceCommunicationResource extends ResourceSupport
{
	private static final long serialVersionUID = 1L;
	private String serialNumber;
	private String rcNumber;
	private Timestamp createdDate;
	private String packetDate;
	private String packetTime;
	private String latitude;
	private String langitude;
	private String location;
	private String imeiNumber;
	private String status;
	private Long stateId;
	private String stateName;
	private Long districtId;
	private String districtName;
	private Long cityId;
	private String cityName;
	private String movement;
	private String batteryStatus;
	private String ignitionStatus;
	private String engineStatus;
	private String tamperStatus;
	private String panicButtonStatus;
	private String prevPacketTime;
	private String prevPacketDate;
	private String prevLatitude;
	private String prevLangitude;
}
