package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.RouteDeviation;
import com.abhaya.vehicle.tracking.data.model.State;
import com.abhaya.vehicle.tracking.data.model.TripDetails;

public interface RouteDeviationRepository extends JpaRepository<RouteDeviation, Long>,JpaSpecificationExecutor<RouteDeviation>
{
	@Query("FROM RouteDeviation where tripDetails.isTripClosed=false and tripDetails.vehicleDetails.state=:state")
	List<RouteDeviation> findStateWiseDeviatedVehicles(State state);
	
	
	@Query("FROM RouteDeviation where tripDetails=:tripDetails")
	RouteDeviation findByTrip(TripDetails tripDetails);
}
