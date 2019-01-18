package com.abhaya.vehicle.tracking.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TripSummaryVO implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String type;
	private Long count;
	private Long tripCount;
	private Boolean isClosed;
	private String districtName;
	private Long districtId;
}
