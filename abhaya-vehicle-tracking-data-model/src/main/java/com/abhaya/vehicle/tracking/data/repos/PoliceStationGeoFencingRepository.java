package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.PoliceStationGeoFencing;

public interface PoliceStationGeoFencingRepository extends TableRepository<PoliceStationGeoFencing, Long>, JpaSpecificationExecutor<PoliceStationGeoFencing>
{

}
