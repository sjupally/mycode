package com.abhaya.vehicle.tracking.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.abhaya.vehicle.tracking.model.EsTripDetails;

public interface ESTripDetailsRepository extends ElasticsearchRepository<EsTripDetails, String> 
{
	List<EsTripDetails> findByRequestId(String requestId);
	List<EsTripDetails> findByRcNumber(String vehicleNumber);
	EsTripDetails findByRcNumberAndIsTripClosed(String vehicleNumber, Boolean isTripClosed);
}
