package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.DistressDetailsView;

public interface DistressDetailsViewRepository extends TableRepository<DistressDetailsView, Long>,JpaSpecificationExecutor<DistressDetailsView>
{

}
