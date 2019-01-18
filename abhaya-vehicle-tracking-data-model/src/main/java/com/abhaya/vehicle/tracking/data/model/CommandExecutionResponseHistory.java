package com.abhaya.vehicle.tracking.data.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Entity
@Table(name = "command_execution_response_history")
@SequenceGenerator(name = "command_execution_response_history_seq", sequenceName = "command_execution_response_history_seq", allocationSize = 1)
public class CommandExecutionResponseHistory implements Serializable 
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "command_execution_response_history_seq")
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
