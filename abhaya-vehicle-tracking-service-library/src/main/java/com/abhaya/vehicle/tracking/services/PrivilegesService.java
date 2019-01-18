package com.abhaya.vehicle.tracking.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.components.ComboComponent;
import com.abhaya.vehicle.tracking.data.model.Privilege;
import com.abhaya.vehicle.tracking.data.repos.PrivilegeRepository;
import com.abhaya.vehicle.tracking.vos.ComboDataVO;
import com.abhaya.vehicle.tracking.vos.PrivilagesVO;

@Service
public class PrivilegesService implements ComboComponent
{
	
	@Autowired private PrivilegeRepository repository;

	@Override
	public List<ComboDataVO> getData4Combo(String... extraParams) 
	{
		List<Privilege> dbContent = repository.findAll();
		return dbContent.stream().map(p -> new ComboDataVO(p.getId(), p.getName(), null)).collect(Collectors.toList());
	}
	
	public boolean save(PrivilagesVO privilagesVO) 
	{
		Privilege privileges = null;
		if (!StringUtils.isEmpty(privilagesVO.getId()))
		{
			privileges = repository.getOne(privilagesVO.getId());
		}
		else
		{
			privileges = new Privilege();
		}
		privileges.setName(privilagesVO.getName());
		repository.save(privileges);
		return true;
	}
}
