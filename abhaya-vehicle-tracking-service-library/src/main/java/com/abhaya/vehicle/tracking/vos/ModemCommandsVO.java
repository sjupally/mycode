package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;

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
@ToString
public class ModemCommandsVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String statisIpAddress;
	private String description;
	private String commandName;
	private String command; 
	private String request; 
	private String response; 
	private byte[] value;
	private String serialNumber;
	private Long taskExecId;
	private String mobileNumber;
}
