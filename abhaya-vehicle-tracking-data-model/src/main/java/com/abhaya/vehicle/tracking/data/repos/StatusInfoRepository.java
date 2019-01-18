package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.Districts;
import com.abhaya.vehicle.tracking.data.model.State;
import com.abhaya.vehicle.tracking.data.model.StatusInfo;

public interface StatusInfoRepository extends TableRepository<StatusInfo, Long>,JpaSpecificationExecutor<StatusInfo>
{

	@Query("FROM StatusInfo where vehicleDetails.state=:state and ignitionStatus='OFF' and serialNumber=:serialNumber order by id desc")
	List<StatusInfo> findStateWiseIgnitionStatus(State state, String serialNumber);

	@Query("FROM StatusInfo where vehicleDetails.districts=:districts and ignitionStatus='OFF' and serialNumber=:serialNumber order by id desc")
	List<StatusInfo> findDistrictsWiseIgnitionStatus(Districts districts, String serialNumber);

}
