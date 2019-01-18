package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.command.controller.CommandExecutionController;
import com.abhaya.vehicle.tracking.resource.CommandExecutionResource;
import com.abhaya.vehicle.tracking.vos.CommandExecutionVO;

@Component
public class CommandExecutionResourceAssembler extends ResourceAssemblerSupport<CommandExecutionVO, CommandExecutionResource> 
{
	public CommandExecutionResourceAssembler() 
	{
		super(CommandExecutionController.class, CommandExecutionResource.class);
	}

	@Override
	public CommandExecutionResource toResource(CommandExecutionVO record) 
	{
		return CommandExecutionResource.builder()
				
			.build();
	}
	
	public CommandExecutionVO fromResource(CommandExecutionResource resoure) 
	{
		return CommandExecutionVO.builder()
				.commandId(resoure.getCommandId())
				.rcNumber(resoure.getRcNumber())
				.commandName(resoure.getCommandName())
			.build();
	}
}