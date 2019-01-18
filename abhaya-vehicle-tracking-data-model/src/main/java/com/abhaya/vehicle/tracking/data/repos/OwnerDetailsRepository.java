package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.OwnerDetails;

public interface OwnerDetailsRepository extends TableRepository<OwnerDetails, Long>,JpaSpecificationExecutor<OwnerDetails>
{

	@Query("FROM OwnerDetails where ownerContactNumber=:ownerContactNumber")
	public OwnerDetails getByContactNumber(String ownerContactNumber);

}
