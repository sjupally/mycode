package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.ModemDetails;

public interface ModemDetailsRepository extends TableRepository<ModemDetails, Long>, JpaSpecificationExecutor<ModemDetails> 
{

	@Query("FROM ModemDetails WHERE imeiNumber=:imeiNumber")
	public ModemDetails findByIMEINumber(String imeiNumber);

	@Query("FROM ModemDetails WHERE serialNumber=:serialNumber")
	public ModemDetails findBySerialNumber(String serialNumber);

}
