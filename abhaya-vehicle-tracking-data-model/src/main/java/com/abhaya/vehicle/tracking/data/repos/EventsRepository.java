package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.Events;

public interface EventsRepository extends TableRepository<Events, Long>,JpaSpecificationExecutor<Events>
{

}
