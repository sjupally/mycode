package com.abhaya.vehicle.tracking.data.repos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.abhaya.vehicle.tracking.data.model.State;

public interface StateRepository extends TableRepository<State, Long>, JpaSpecificationExecutor<State>
{
	@Query("FROM State WHERE name=:name")
	public State getByName(String name);
	
	@Query("FROM State where id=:stateId")
	List<State> getByStateId(long stateId);
}
