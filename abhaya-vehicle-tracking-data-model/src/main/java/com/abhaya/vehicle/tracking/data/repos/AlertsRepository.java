package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.Alerts;

public interface AlertsRepository extends TableRepository<Alerts, Long>,JpaSpecificationExecutor<Alerts>
{

}
