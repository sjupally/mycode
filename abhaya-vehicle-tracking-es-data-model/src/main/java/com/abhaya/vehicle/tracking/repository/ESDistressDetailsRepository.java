package com.abhaya.vehicle.tracking.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.abhaya.vehicle.tracking.model.ESDistressDetails;

public interface ESDistressDetailsRepository extends ElasticsearchRepository<ESDistressDetails, String> 
{

}
