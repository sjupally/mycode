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
public class VehicleDriverDetailsResource extends ResourceSupport 
{
	private Long sourceId;
	private String vehicleName;
	private String rcNumber;
	private String registrationDate;
	private String ownerName;
	private String ownerContactNumber;
	private String rcExpiryDate;
	private Boolean isOwner;
	private Boolean isDeviceMapped;
	private String driverName;
	private String dlNumber;
	private String dlExpiryDate;
	private String driverContactNumber;
	private String gender;
	private String packetTime;
	private String packetDate;
	private String serialNumber;
	private String rfId;
	private byte[] image;
}
