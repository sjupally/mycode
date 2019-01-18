package com.abhaya.vehicle.tracking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.components.ComboComponent;
import com.abhaya.vehicle.tracking.data.model.Privilege;
import com.abhaya.vehicle.tracking.data.model.Roles;
import com.abhaya.vehicle.tracking.data.repos.PrivilegeRepository;
import com.abhaya.vehicle.tracking.data.repos.RolesRepositary;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadCitiesSetEvent;
import com.abhaya.vehicle.tracking.util.NepheleValidationUtils;
import com.abhaya.vehicle.tracking.vos.ComboDataVO;
import com.abhaya.vehicle.tracking.vos.PrivilagesVO;
import com.abhaya.vehicle.tracking.vos.RolesVO;

@Service
public class RolesService implements ComboComponent
{
	
	@Autowired private RolesRepositary repository;
	@Autowired private PrivilegeRepository privilegeRepository;

	@Override
	public List<ComboDataVO> getData4Combo(String... extraParams) 
	{
		List<Roles> dbContent = repository.findAll();
		return dbContent.stream().map(p -> new ComboDataVO(p.getId(), p.getName(), null)).collect(Collectors.toList());
	}

	public boolean save(RolesVO rolesVO) 
	{
		List<Privilege> privileges = new ArrayList<>();
		Roles roles = null;
		if (!StringUtils.isEmpty(rolesVO.getId()))
		{
			roles = repository.getOne(rolesVO.getId());
		}
		else
		{
			roles = new Roles();
		}
		roles.setName(rolesVO.getName());
		if (rolesVO.getPrivilege() != null && rolesVO.getPrivilege().length > 0)
		{
			for (Long privilegeId : rolesVO.getPrivilege())
			{
				Privilege  privilege  = privilegeRepository.getOne(privilegeId);
				privileges.add(privilege);
			}
		}
		roles.setPrivileges(privileges);
		repository.save(roles);
		return true;
	}

	public PageReadEvent<RolesVO> readData(ReadCitiesSetEvent request) 
	{
		Page<Roles> dbContent = repository.findAll(request.getPageable());
		List<RolesVO> content = new ArrayList<>();
		for (Roles record : NepheleValidationUtils.nullSafe(dbContent)) 
		{
			List<Privilege> privileges = record.getPrivileges();
			List<PrivilagesVO> privilagesVOs = new ArrayList<>();
			for (Privilege privilege : privileges)
			{
				PrivilagesVO privilagesVO = new PrivilagesVO();
				privilagesVO.setId(privilege.getId());
				privilagesVO.setName(privilege.getName());
				privilagesVOs.add(privilagesVO);
			}
			RolesVO details = RolesVO.builder()
				.name(record.getName())
				.id(record.getId())
				.privileges(privilagesVOs)
				.build();
			content.add(details); 
		}
		Page<RolesVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
		return new PageReadEvent<>(page);
	}
}
