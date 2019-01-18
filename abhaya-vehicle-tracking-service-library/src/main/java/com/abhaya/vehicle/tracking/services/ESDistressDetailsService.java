package com.abhaya.vehicle.tracking.services;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.model.ESDistressDetails;
import com.abhaya.vehicle.tracking.repository.ESDistressDetailsRepository;

@Service
public class ESDistressDetailsService 
{
	@Autowired private ESDistressDetailsRepository esDistressDetailsRepository;
	
	public Page<ESDistressDetails> searchDistress(String searchValue, Pageable pageable) 
	{
		return esDistressDetailsRepository.search(QueryBuilders.queryStringQuery(searchValue),pageable);
	}
	public Page<ESDistressDetails> findAll(Pageable pageable)
	{
		return esDistressDetailsRepository.findAll(pageable);
	}

}
