package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;

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
public class TripVO implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sourceLatLang;
    private String destiLatLang;
    private boolean isTripClosed;
    private String rcNumber;
    private String citizenMobileNumber;
    private boolean shareRoute;
}
