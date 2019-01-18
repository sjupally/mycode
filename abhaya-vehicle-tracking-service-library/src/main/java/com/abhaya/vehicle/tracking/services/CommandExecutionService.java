package com.abhaya.vehicle.tracking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.data.model.CommandSettings;
import com.abhaya.vehicle.tracking.data.model.ModemDetails;
import com.abhaya.vehicle.tracking.data.model.VehicleDetails;
import com.abhaya.vehicle.tracking.data.repos.CommandSettingsRepository;
import com.abhaya.vehicle.tracking.data.repos.VehicleDetailsRepository;
import com.abhaya.vehicle.tracking.events.CreateCommandExecutionResponseHistoryEvent;
import com.abhaya.vehicle.tracking.util.ModemConnectService;
import com.abhaya.vehicle.tracking.vos.CommandExecutionResponseHistoryVO;
import com.abhaya.vehicle.tracking.vos.CommandExecutionVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

public interface CommandExecutionService 
{
	public void executeCommand(String command) throws Exception;

	@Slf4j
	@Service
	public class impl implements CommandExecutionService
	{
		@Autowired private VehicleDetailsRepository vehicleDetailsRepository;
		@Autowired private ModemDetailsService modemDetailsService;
		@Autowired private CommandSettingsRepository repository;
		@Autowired private ModemConnectService modemConnectService;
		@Autowired private CommandExecutionResponseHistoryService responseHistoryService; 

		@Override
		public void executeCommand(String command) throws Exception 
		{
			CommandExecutionVO executionVO = new ObjectMapper().readValue(command, CommandExecutionVO.class);
			VehicleDetails vehicleDetails = vehicleDetailsRepository.getByRCNumber(executionVO.getRcNumber());
			if (!StringUtils.isEmpty(vehicleDetails) && vehicleDetails.getIsDeviceMapped())
			{
				ModemDetails modemDetails = modemDetailsService.getModemBySerialNumber(vehicleDetails.getSerialNumber());
				CommandSettings commandSettings = repository.getOne(executionVO.getCommandId());
				String commandQuery = prepareCommand(commandSettings,vehicleDetails.getSerialNumber());
				String response  = modemConnectService.processModemCommand(commandQuery, modemDetails.getIpAddress());
				log.info("Final Response From IoT Device " + response);
				String responseType = "NA";
				if (response.contains("OTSI"))
				{
					responseType = parseResponse(response);
				}
				CommandExecutionResponseHistoryVO vo = CommandExecutionResponseHistoryVO.builder()
						.commandName(executionVO.getCommandName())
						.commandType(commandSettings.getCommandType())
						.rcNumber(vehicleDetails.getRcNumber())
						.requestQuery(commandQuery)
						.responseQuery(response)
						.responseType(responseType)
						.serialNumber(vehicleDetails.getSerialNumber())
						.statisIpAddress(modemDetails.getIpAddress())
						.build();
				CreateCommandExecutionResponseHistoryEvent event = new CreateCommandExecutionResponseHistoryEvent();
				event.setResponseHistoryVO(vo);
				responseHistoryService.save(event);
			}
		}

		private String parseResponse(String response) 
		{
			String[] values = response.split("$");
			return values[5];
		}

		private String prepareCommand(CommandSettings commandSettings, String serialNumber) 
		{
			StringBuilder builder = new StringBuilder();
			builder.append("OTSI$");
			builder.append(serialNumber);
			builder.append("$");
			builder.append(commandSettings.getCommandType()); // SET or GET
			builder.append("$");
			builder.append(commandSettings.getValue()); // APNCMD
			builder.append("$XXXXXXXXXXXXXX$00$");
			
			//byte[] query =  builder.toString().getBytes();
			//String crc = CRC16Modbus.getCrc16(query.length, query);

			builder.append("XX"); //crc
			builder.append("$!");

			log.info("Final Command Query " + builder.toString());
			return builder.toString();
		}
	}
}
