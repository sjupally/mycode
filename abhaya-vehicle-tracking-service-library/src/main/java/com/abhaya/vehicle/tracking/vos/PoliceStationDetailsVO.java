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
public class PoliceStationDetailsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	
    private Long id;
	private String stationName;
	private String division;
	private String contactNumber;
	private String mobileNumber;
	private String emailId;
	private String address;
	private int jurisdiction;	// 0 -> non jurisdiction | 1-> jurisdiction | 2-> out of jurisdiction | 3-> police oiffices but not jurisdiction
	private String latitude;
	private String langitude;
	private String city;
	private Long pincode;
}
