package com.abhaya.vehicle.tracking.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.abhaya.vehicle.tracking.model.ESTripTrackingDetails;

public interface ESTripTrackingDetailsRepository extends ElasticsearchRepository<ESTripTrackingDetails, String> 
{
   public Page<ESTripTrackingDetails> findByTripId(String tripd,Pageable pageable);

   public List<ESTripTrackingDetails> findByTripIdAndIdGreaterThan(String tripdId, String id);
}
