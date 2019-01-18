package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.WatchVehicleView;

public interface WatchVehicleViewRepository extends TableRepository<WatchVehicleView, Long>,JpaSpecificationExecutor<WatchVehicleView>
{

}
