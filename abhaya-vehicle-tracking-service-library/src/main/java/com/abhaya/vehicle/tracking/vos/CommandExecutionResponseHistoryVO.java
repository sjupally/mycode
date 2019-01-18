package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;
import java.sql.Timestamp;

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
public class CommandExecutionResponseHistoryVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String statisIpAddress;
	private String commandName;
	private String commandType;
	private String requestQuery; 
	private String responseQuery; 
	private String serialNumber;
	private String rcNumber;
	private String responseType;
	private Timestamp createdDate;
}
