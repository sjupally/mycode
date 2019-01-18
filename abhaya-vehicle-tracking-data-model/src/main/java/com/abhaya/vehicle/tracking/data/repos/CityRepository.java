package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.City;

public interface CityRepository extends TableRepository<City, Long>, JpaSpecificationExecutor<City>
{

	@Query("FROM City where state.id=:stateId")
	public List<City> findByStateId(Long stateId);

	
	@Query("FROM City where districts.id=:distrctId")
	public List<City> findByDistrictId(long distrctId);

}
