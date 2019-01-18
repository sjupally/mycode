package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.CitizenDetails;

public interface CitizenDetailsRepository extends TableRepository<CitizenDetails, Long>, JpaSpecificationExecutor<CitizenDetails> 
{

	@Query("FROM CitizenDetails WHERE mobileNumber=:mobileNumber")
	public CitizenDetails getCitizenByMobileNumber(String mobileNumber);

	@Query("FROM CitizenDetails WHERE deviceId=:deviceId")
	public CitizenDetails checkCitizenByDeviceId(String deviceId);

}
