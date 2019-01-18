package com.abhaya.vehicle.tracking.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.abhaya.vehicle.tracking.model.ESRouteDeviation;

public interface ESRouteDeviationRepository extends ElasticsearchRepository<ESRouteDeviation, String> 
{

}
