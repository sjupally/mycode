package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.UserDetails;

public interface UserDetailsRepository  extends TableRepository<UserDetails, Long>, JpaSpecificationExecutor<UserDetails> 
{

	UserDetails findByUsername(String username);

	@Query("FROM UserDetails where mobileNumber=:mobileNumber")
	UserDetails findByMobileNumber(Long mobileNumber);
	
}
