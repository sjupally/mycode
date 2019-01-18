package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.TripDetailsView;

public interface TripDetailsViewRepository extends TableRepository<TripDetailsView, Long>,JpaSpecificationExecutor<TripDetailsView>
{

}
