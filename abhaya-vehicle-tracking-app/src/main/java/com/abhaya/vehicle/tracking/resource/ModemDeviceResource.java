package com.abhaya.vehicle.tracking.resource;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@NoArgsConstructor
@Builder
@AllArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
@JsonInclude(Include.ALWAYS)
public class ModemDeviceResource extends ResourceSupport 
{
	private Long modemDeviceId;
	private String imeiNumber;
	private String simNumber;
	private String imsiNumber;
	private String ipAddress;
	private String signalStrength;
	private String version;
	private String serialNumber;
	private String createdDate;
	private String mobileNumber;
	private Long stateId;
	private Long districtId;
	private Long cityId;
	private String stateName;
	private String districtName;
	private String cityName;
}
