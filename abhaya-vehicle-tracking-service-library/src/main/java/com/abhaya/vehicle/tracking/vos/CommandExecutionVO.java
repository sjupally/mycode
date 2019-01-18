package com.abhaya.vehicle.tracking.vos;

import java.io.Serializable;

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
public class CommandExecutionVO implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String commandName;
	private String rcNumber;
	private Long commandId;
}
