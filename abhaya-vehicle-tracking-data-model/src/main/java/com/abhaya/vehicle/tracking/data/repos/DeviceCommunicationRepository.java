package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.DeviceCommunication;

public interface DeviceCommunicationRepository extends TableRepository<DeviceCommunication, Long>, JpaSpecificationExecutor<DeviceCommunication> 
{
	@Query("FROM DeviceCommunication WHERE serialNumber=:serialNumber")
	public DeviceCommunication getBySerialNumber(String serialNumber);

}
