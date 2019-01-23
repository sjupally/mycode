package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.ModemDetailsView;

public interface ModemDetailsViewRepository extends TableRepository<ModemDetailsView, Long>, JpaSpecificationExecutor<ModemDetailsView> 
{
	
}
