package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.DistressDetails;
import com.abhaya.vehicle.tracking.data.model.TripDetails;

public interface DistressDetailsRepository extends TableRepository<DistressDetails, Long>,JpaSpecificationExecutor<DistressDetails>
{

	@Query("FROM DistressDetails where tripDetails=:tripDetails")
	DistressDetails getByTrip(TripDetails tripDetails);
	
	@Query("FROM DistressDetails where tripDetails=:tripDetails and eventType=:eventType and isClosed=false")
	DistressDetails getByTripAndEventType(TripDetails tripDetails, String eventType);
	
}
