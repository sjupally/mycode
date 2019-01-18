package com.abhaya.vehicle.tracking.resource;

import org.springframework.hateoas.ResourceSupport;

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
public class VehicleDetailsResource extends ResourceSupport
{
	private Long vehicleId;
	private String vehicleName;
	private String rcNumber;
	private String registrationDate;
	private String ownerName;
	private String ownerContactNumber;
	private String createdDate;
	private String cityName;
	private String serialNumber;
	private Boolean isOwner;
	private Boolean isDeviceMapped;
	private Long cityId;
	private Long districtId;
	private String districtName;
	private String make;
	private String rcExpiryDate;
	private Long stateId;
	private String stateName;
	private String deviceMappedDate;
}
