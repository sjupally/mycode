package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class AlertsVO implements Serializable
{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String alertType;
	private String alertName;
	private String serialNumber;
	private long citizenId;
	private Timestamp alertTime;
	private String latitude;
	private String langitude;
	private String comments;
	private Boolean isClosed;
	private String attendedBy;
}
