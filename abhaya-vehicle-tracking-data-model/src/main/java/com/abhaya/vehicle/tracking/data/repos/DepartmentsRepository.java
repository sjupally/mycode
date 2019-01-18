package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.Departments;

public interface DepartmentsRepository extends TableRepository<Departments, Long>,JpaSpecificationExecutor<Departments>
{

}
