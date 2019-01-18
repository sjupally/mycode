package com.abhaya.vehicle.tracking.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleURLParser 
{
	private String shortLink;
}
