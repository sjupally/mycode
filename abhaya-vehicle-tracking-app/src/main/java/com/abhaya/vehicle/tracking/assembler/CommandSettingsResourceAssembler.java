package com.abhaya.vehicle.tracking.assembler;

import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
import org.springframework.stereotype.Component;

import com.abhaya.vehicle.tracking.query.controller.CommandSettingsQueryController;
import com.abhaya.vehicle.tracking.resource.CommandSettingsResource;
import com.abhaya.vehicle.tracking.vos.CommandSettingsVO;

@Component
public class CommandSettingsResourceAssembler extends ResourceAssemblerSupport<CommandSettingsVO, CommandSettingsResource> 
{
	public CommandSettingsResourceAssembler() 
	{
		super(CommandSettingsQueryController.class, CommandSettingsResource.class);
	}

	@Override
	public CommandSettingsResource toResource(CommandSettingsVO entity) 
	{
		return CommandSettingsResource.builder()
				.commandId(entity.getId())
				.commandName(entity.getCommandName())
				.commandType(entity.getCommandType())
				.value(entity.getValue())
				.build();
	}
	public CommandSettingsVO fromResource(CommandSettingsResource resource) 
	{
		return CommandSettingsVO.builder()
				.commandName(resource.getCommandName())
				.commandType(resource.getCommandType())
				.value(resource.getValue())
				.id(resource.getCommandId())
				.build();
	}
}