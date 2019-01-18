package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.RawPacketData;

public interface RawPacketDataRepository  extends TableRepository<RawPacketData, Long>, JpaSpecificationExecutor<RawPacketData> 
{

	@Query("FROM RawPacketData WHERE serialNumber=:serialNumber and packetDate=:packetDate")
	List<RawPacketData> getDataBySerialNumberAndDate(String serialNumber, String packetDate);

}

