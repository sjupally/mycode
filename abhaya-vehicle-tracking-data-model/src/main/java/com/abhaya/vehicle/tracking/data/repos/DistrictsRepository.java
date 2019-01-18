package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.Districts;

public interface DistrictsRepository extends TableRepository<Districts, Long>,JpaSpecificationExecutor<Districts>
{

	@Query("FROM Districts where state.id=:stateId")
	List<Districts> findByStateId(long stateId);

}
