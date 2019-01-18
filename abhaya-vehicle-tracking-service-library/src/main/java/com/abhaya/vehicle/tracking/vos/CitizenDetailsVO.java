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
public class CitizenDetailsVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private String mobileNumber;
	private Timestamp createdOn;
	private Timestamp modifiedOn;
	private String deviceId;
	private String deviceType;
}
