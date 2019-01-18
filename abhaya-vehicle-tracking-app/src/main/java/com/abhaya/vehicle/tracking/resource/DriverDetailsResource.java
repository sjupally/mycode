package com.abhaya.vehicle.tracking.resource;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverDetailsResource extends ResourceSupport 
{
	private Long driverId;
	private String driverName;
	private String dlNumber;
	private String dlExpiryDate;
	private Long contactNumber;
	private String createdDate;
	private String gender;
	private String rfId;
	private Long cityId;
	private Long districtId;
	private String cityName;
	private String districtName;
	private Long stateId;
	private String stateName;
	private byte[] image; 
	private MultipartFile file;
}
