package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.VehicleDriverDetailsView;

public interface VehicleDriverDetailsViewRepository extends JpaRepository<VehicleDriverDetailsView, Long>,JpaSpecificationExecutor<VehicleDriverDetailsView>
{
	
	@Query("FROM VehicleDriverDetailsView WHERE rcNumber=:rcNumber")
	public VehicleDriverDetailsView getByRCNumber(String rcNumber);
}
