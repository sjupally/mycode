package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.DriverOnBoarding;
import com.abhaya.vehicle.tracking.data.model.VehicleDetails;

public interface DriverOnBoardingRepository extends JpaRepository<DriverOnBoarding, Long>,JpaSpecificationExecutor<DriverOnBoarding>
{

	@Query("FROM DriverOnBoarding where vehicleDetails=:vehicleDetails and status=true")
	public DriverOnBoarding getByVehicleDetails(VehicleDetails vehicleDetails);

}
