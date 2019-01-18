package com.abhaya.vehicle.tracking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.model.CommandExecutionResponseHistory;
import com.abhaya.vehicle.tracking.data.repos.CommandExecutionResponseHistoryRepository;
import com.abhaya.vehicle.tracking.events.CreateCommandExecutionResponseHistoryEvent;
import com.abhaya.vehicle.tracking.vos.CommandExecutionResponseHistoryVO;

public interface CommandExecutionResponseHistoryService 
{
	public void save(CreateCommandExecutionResponseHistoryEvent event);

	@Service
	public class impl implements CommandExecutionResponseHistoryService
	{
		@Autowired private CommandExecutionResponseHistoryRepository repository;

		@Override
		public void save(CreateCommandExecutionResponseHistoryEvent event) 
		{
			CommandExecutionResponseHistoryVO executionVO = event.getResponseHistoryVO();
			CommandExecutionResponseHistory responseHistory = CommandExecutionResponseHistory.builder()
					.commandName(executionVO.getCommandName())
					.commandType(executionVO.getCommandType())
					.rcNumber(executionVO.getRcNumber())
					.requestQuery(executionVO.getRequestQuery())
					.responseQuery(executionVO.getResponseQuery())
					.responseType(executionVO.getResponseType())
					.serialNumber(executionVO.getSerialNumber())
					.statisIpAddress(executionVO.getStatisIpAddress())
					.build();
			repository.save(responseHistory);
		}
	}
}
