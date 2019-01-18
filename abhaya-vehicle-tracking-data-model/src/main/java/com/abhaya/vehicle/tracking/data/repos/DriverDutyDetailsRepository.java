package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.DriverDetails;
import com.abhaya.vehicle.tracking.data.model.DriverDutyDetails;
import com.abhaya.vehicle.tracking.data.model.VehicleDetails;

public interface DriverDutyDetailsRepository extends TableRepository<DriverDutyDetails, Long>,JpaSpecificationExecutor<DriverDutyDetails>
{

	@Query("FROM DriverDutyDetails where vehicleDetails=:vehicleDetails order by id desc")
	public List<DriverDutyDetails> getByVehicleDetails(VehicleDetails vehicleDetails);

	@Query("FROM DriverDutyDetails where vehicleDetails=:vehicleDetails and driverDetails=:driverDetails")
	public DriverDutyDetails getByDriverNVehicle(DriverDetails driverDetails, VehicleDetails vehicleDetails);

	@Query("FROM DriverDutyDetails where vehicleDetails=:vehicleDetails and driverDetails=:driverDetails and packetDate=:packetDate")
	public DriverDutyDetails isDriverDutyExist(DriverDetails driverDetails, VehicleDetails vehicleDetails, String packetDate);

}
