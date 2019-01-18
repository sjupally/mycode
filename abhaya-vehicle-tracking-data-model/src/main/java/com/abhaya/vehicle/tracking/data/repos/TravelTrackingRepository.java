package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.TravelTracking;
import com.abhaya.vehicle.tracking.data.model.TripDetails;

public interface TravelTrackingRepository extends TableRepository<TravelTracking, Long>, JpaSpecificationExecutor<TravelTracking> 
{

	@Query("FROM TravelTracking WHERE serialNumber=:serialNumber order by id desc")
	public List<TravelTracking> getBySerialNumber(String serialNumber);
	
	@Query("FROM TravelTracking WHERE tripDetails=:tripDetails order by id desc")
	public List<TravelTracking> getByTrip(TripDetails tripDetails);

	@Query("FROM TravelTracking WHERE serialNumber=:serialNumber and packetDate=:currentDate order by id desc")
	public List<TravelTracking> getBySerialNumberAndDate(String serialNumber, String currentDate);
}
