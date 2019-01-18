package com.abhaya.vehicle.tracking.services;

import java.util.List;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.model.ESTripTrackingDetails;
import com.abhaya.vehicle.tracking.model.EsTripDetails;
import com.abhaya.vehicle.tracking.repository.ESTripDetailsRepository;
import com.abhaya.vehicle.tracking.repository.ESTripTrackingDetailsRepository;

@Service
public class ESTripDetailsService 
{
	@Autowired private ESTripDetailsRepository esTripDetailsRepository;
	@Autowired private ESTripTrackingDetailsRepository trackingDetailsRepository; 

	public Page<EsTripDetails> getDocuments(String searchValue,Pageable pageable)
	{
		return esTripDetailsRepository.search(QueryBuilders.queryStringQuery(searchValue),pageable);
	}
	public Page<EsTripDetails> getAllTrips(Pageable pageable)
	{
		return esTripDetailsRepository.findAll(pageable);
	}
	public Page<ESTripTrackingDetails> searchTracks(String searchValue, Pageable pageable) 
	{
		return trackingDetailsRepository.search(QueryBuilders.queryStringQuery(searchValue),pageable);
	}
	public Page<ESTripTrackingDetails> getAllTracks(Pageable pageable) 
	{
		return trackingDetailsRepository.findAll(pageable);
	}
	public List<ESTripTrackingDetails> findByTripIdAndTrackId(Long tripId,Long trackId) 
	{
		return trackingDetailsRepository.findByTripIdAndIdGreaterThan(tripId.toString(),trackId.toString());
	}
	public Page<ESTripTrackingDetails> getLiveVehicles() 
	{
		  SearchQuery searchQuery = new NativeSearchQueryBuilder()
			.withQuery(QueryBuilders.matchAllQuery())
			.addAggregation(AggregationBuilders.terms("keyword").field("serialNumber"))
			.build();
		return trackingDetailsRepository.search(searchQuery);
	}
}
