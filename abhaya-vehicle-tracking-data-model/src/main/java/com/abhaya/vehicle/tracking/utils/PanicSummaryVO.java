package com.abhaya.vehicle.tracking.utils;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties (ignoreUnknown = true)
@ToString
public class PanicSummaryVO  implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String eventSource;
	private Boolean isClosed;	
	private Long count;
	private Long cityId;
	private Long districtId;
	private Long stateId;
	private String packetDate;
}
