package com.abhaya.vehicle.tracking.services;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.model.ESDriverDutyDetails;
import com.abhaya.vehicle.tracking.repository.ESDriverDutyDetailsRepository;

@Service
public class ESDriverDutyDetailsService 
{
	
	@Autowired private ESDriverDutyDetailsRepository esDriverDutyDetailsRepository;
	
	public Page<ESDriverDutyDetails> searchDuties(String searchValue, Pageable pageable) 
	{
		return esDriverDutyDetailsRepository.search(QueryBuilders.queryStringQuery(searchValue),pageable);
	}
	public Page<ESDriverDutyDetails> findAll(Pageable pageable)
	{
		return esDriverDutyDetailsRepository.findAll(pageable);
	}
}
