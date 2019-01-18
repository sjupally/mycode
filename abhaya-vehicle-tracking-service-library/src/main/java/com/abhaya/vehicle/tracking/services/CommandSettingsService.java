package com.abhaya.vehicle.tracking.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.components.ComboComponent;
import com.abhaya.vehicle.tracking.data.model.CommandSettings;
import com.abhaya.vehicle.tracking.data.repos.CommandSettingsRepository;
import com.abhaya.vehicle.tracking.data.repos.CommandSettingsSpecifications;
import com.abhaya.vehicle.tracking.events.CreateCommandSettingsEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadCommandSettingsSetEvent;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.vos.ComboDataVO;
import com.abhaya.vehicle.tracking.vos.CommandSettingsVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommandSettingsService implements ComboComponent
{
		
	@Autowired private CommandSettingsRepository repository;
	public PageReadEvent<CommandSettingsVO> readDate(ReadCommandSettingsSetEvent request) 
	{
		Page<CommandSettings> dbContent = repository.findAll(new CommandSettingsSpecifications(request.getCommandName()),CommandSettingsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
		List<CommandSettingsVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), CommandSettingsVO.class);
		Page<CommandSettingsVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
		return new PageReadEvent<>(page);
	}
	public void save(CreateCommandSettingsEvent event) 
	{
		try
		{
			CommandSettingsVO settingsVO = event.getCommandSettingsVO();
			CommandSettings settings = new CommandSettings();
			if (!StringUtils.isEmpty(settingsVO.getId()))
			{
				settings = repository.getOne(settingsVO.getId());
			}
			settings.setCommandName(settingsVO.getCommandName());
			settings.setCommandType(settingsVO.getCommandType());
			settings.setValue(settingsVO.getValue());
			
			repository.save(settings);
		}
		catch (Exception e) 
		{
			log.info("Exception while saving Settings " + e.getMessage(),e);
		}
	}
	@Override
	public List<ComboDataVO> getData4Combo(String... extraParams)
	{
		List<CommandSettings> dbContent = repository.findAll();
		return dbContent.stream().map(p -> new ComboDataVO(p.getId(), p.getCommandName(), null)).collect(Collectors.toList());
	}
}
