package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.CitizenDetails;
import com.abhaya.vehicle.tracking.data.model.EmergencyContactNumbers;

public interface EmergencyContactNumbersRepository extends TableRepository<EmergencyContactNumbers, Long>, JpaSpecificationExecutor<EmergencyContactNumbers> 
{

	@Query("FROM EmergencyContactNumbers where citizenDetails=:citizenDetails and emergencyContactNumber=:emergencyContactNumber")
	public  EmergencyContactNumbers getByCitizenAndNumber(CitizenDetails citizenDetails, String emergencyContactNumber);

	@Query("FROM EmergencyContactNumbers where citizenDetails=:citizenDetails")
	public List<EmergencyContactNumbers> deleteExistingByCitizen(CitizenDetails citizenDetails);

	@Query("FROM EmergencyContactNumbers where citizenDetails.mobileNumber=:citizenMobileNumber")
	public List<EmergencyContactNumbers> getByCitizenMobileNumber(String citizenMobileNumber);
}
