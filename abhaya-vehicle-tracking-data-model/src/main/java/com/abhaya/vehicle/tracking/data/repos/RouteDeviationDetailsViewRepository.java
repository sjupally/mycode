package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.RouteDeviationDetailsView;

public interface RouteDeviationDetailsViewRepository extends JpaRepository<RouteDeviationDetailsView, Long>,JpaSpecificationExecutor<RouteDeviationDetailsView>
{
	
	
}
