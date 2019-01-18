package com.abhaya.vehicle.tracking.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties (ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@ToString
public class VehicleStatusVO
{
	private String statusType;
	private Long count;
	private Long cityId;
	private Long districtId;
	private Long stateId;
	private String date;
}
