package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.WatchVehicle;

public interface WatchVehicleRepository extends TableRepository<WatchVehicle, Long>,JpaSpecificationExecutor<WatchVehicle>
{

	@Query("FROM WatchVehicle WHERE vehicleDetails.rcNumber=:rcNumber")
	public WatchVehicle getByRCNumber(String rcNumber);
}
