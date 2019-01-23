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
public class ModemDetailsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
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
