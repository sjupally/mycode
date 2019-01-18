package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class TripDetailsVO implements Serializable
{

	private static final long serialVersionUID = 1L;
	private Long id;
    private String requestId;
    private String sourceLatLang;
    private String destiLatLang;
    private String sourceLocation;
    private String destiLocation;
    private Timestamp requestTime;
    private Timestamp closeTime;
    private String remarks;
    private String travelMode;
    private boolean isTripClosed;
    private String dlNumber;
    private String rcNumber;
    private String citizenMobileNumber;
    private Long ownerContactNumber;
    private String ownerName;
    private String registrationDate;
    private String vehicleName;
    private String deviceId;
    private String deviceType;
    private String serialNumber;
    private boolean shareRoute;
    private Boolean isTripExist;
}
