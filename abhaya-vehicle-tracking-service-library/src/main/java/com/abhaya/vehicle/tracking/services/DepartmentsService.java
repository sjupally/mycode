package com.abhaya.vehicle.tracking.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.components.ComboComponent;
import com.abhaya.vehicle.tracking.data.model.Departments;
import com.abhaya.vehicle.tracking.data.repos.DepartmentsRepository;
import com.abhaya.vehicle.tracking.vos.ComboDataVO;

@Service
public class DepartmentsService implements ComboComponent
{
	@Autowired private DepartmentsRepository repository;

	@Override
	public List<ComboDataVO> getData4Combo(String... extraParams) 
	{
		List<Departments> dbContent = repository.findAll();
		List<ComboDataVO> content = dbContent.stream().map(p -> new ComboDataVO(p.getId(), p.getName(), p.getDescription())).collect(Collectors.toList());
		return content;
	}
}
