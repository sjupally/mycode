package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.DriverDetails;

public interface DriverDetailsRepository extends TableRepository<DriverDetails, Long>, JpaSpecificationExecutor<DriverDetails> 
{

	@Query("FROM DriverDetails where dlNumber=:dlNumber")
	public DriverDetails getByDLNumber(String dlNumber);

	@Query("FROM DriverDetails where rfId=:driverRFId")
	public DriverDetails getByRFID(String driverRFId);

}
