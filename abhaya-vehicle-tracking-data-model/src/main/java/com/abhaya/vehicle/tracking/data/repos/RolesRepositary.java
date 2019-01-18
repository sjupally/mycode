package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.Roles;

public interface RolesRepositary extends TableRepository<Roles, Long>, JpaSpecificationExecutor<Roles>
{

}
