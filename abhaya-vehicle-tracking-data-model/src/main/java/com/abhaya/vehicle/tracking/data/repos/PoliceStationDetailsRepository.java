package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.PoliceStationDetails;

public interface PoliceStationDetailsRepository extends TableRepository<PoliceStationDetails, Long>, JpaSpecificationExecutor<PoliceStationDetails> 
{

}
