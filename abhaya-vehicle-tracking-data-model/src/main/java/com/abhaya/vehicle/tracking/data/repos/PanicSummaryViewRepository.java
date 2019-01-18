package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.PanicSummaryView;

public interface PanicSummaryViewRepository extends JpaRepository<PanicSummaryView, Long>,JpaSpecificationExecutor<PanicSummaryView>
{
	
	
}
