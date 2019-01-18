package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.State;
import com.abhaya.vehicle.tracking.data.model.TripDetails;
import com.abhaya.vehicle.tracking.data.model.VehicleDetails;

public interface TripDetailsRepository extends TableRepository<TripDetails, Long>, JpaSpecificationExecutor<TripDetails> 
{
	@Query("FROM TripDetails where imeiNumber=:imeiNumber and isTripClosed=false")
	public TripDetails getTripByIMEINumberAndStatus(String imeiNumber);

	@Query("FROM TripDetails where vehicleDetails.rcNumber=:vehicleNumber and isTripClosed=false")
	public TripDetails getTripByVehicleNumber(String vehicleNumber);
	
	@Query("FROM TripDetails where vehicleDetails.serialNumber=:serialNumber and isTripClosed=false")
	public TripDetails getTripBySerialNumber(String serialNumber);

	@Modifying(clearAutomatically = true)
	@Transactional
	@Query("update TripDetails  set isTripClosed=true,closeTime=now() where vehicleDetails =:vehicleDetails and isTripClosed=false")
	public void closeExistingTrip(VehicleDetails vehicleDetails);

	@Query("FROM TripDetails where citizenDetails.mobileNumber=:mobileNumber and isTripClosed=false")
	public TripDetails getActiveTripByCitizenNumber(String mobileNumber);
	
	@Query("FROM TripDetails where citizenDetails.mobileNumber=:mobileNumber and isTripClosed=false and vehicleDetails.rcNumber=:vehicleNumber")
	public TripDetails getActiveTripByCitizenNumberNVehicleNumber(String mobileNumber,String vehicleNumber);

	@Query("FROM TripDetails where vehicleDetails.state=:state and isTripClosed=false")
	public List<TripDetails> findStateWiseLiveVehicles(State state);
}
