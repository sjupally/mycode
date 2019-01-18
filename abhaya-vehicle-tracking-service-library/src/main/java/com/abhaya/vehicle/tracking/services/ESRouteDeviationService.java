package com.abhaya.vehicle.tracking.services;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.model.ESRouteDeviation;
import com.abhaya.vehicle.tracking.repository.ESRouteDeviationRepository;

@Service
public class ESRouteDeviationService 
{
	@Autowired private ESRouteDeviationRepository esRouteDeviationRepository;
	public Page<ESRouteDeviation> searchDeviations(String searchValue, Pageable pageable) 
	{
		return esRouteDeviationRepository.search(QueryBuilders.queryStringQuery(searchValue),pageable);
	}
	public Page<ESRouteDeviation> findAll(Pageable pageable)
	{
		return esRouteDeviationRepository.findAll(pageable);
	}
}
