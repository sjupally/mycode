package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.VehicleDetails;

public interface VehicleDetailsRepository extends TableRepository<VehicleDetails, Long>, JpaSpecificationExecutor<VehicleDetails> 
{

	@Query("FROM VehicleDetails WHERE rcNumber=:rcNumber")
	public VehicleDetails getByRCNumber(String rcNumber);

	@Query("FROM VehicleDetails WHERE serialNumber=:serialNumber")
	public VehicleDetails getBySerialNumber(String serialNumber);

	@Query("FROM VehicleDetails WHERE state.id=:stateId")
	public List<VehicleDetails> findByStateId(Long stateId);

}
