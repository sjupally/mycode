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
public class TripDetailsResource extends ResourceSupport 
{
	private Long tripId;
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
    private boolean shareRoute;
    private Boolean isTripExist;
}