package com.abhaya.vehicle.tracking.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.abhaya.vehicle.tracking.model.ESDriverDutyDetails;

public interface ESDriverDutyDetailsRepository extends ElasticsearchRepository<ESDriverDutyDetails, String> 
{

	public ESDriverDutyDetails findByDlNumberAndRcNumberAndPacketDate(String dlNumber, String rcNumber, String packetDate);

}
