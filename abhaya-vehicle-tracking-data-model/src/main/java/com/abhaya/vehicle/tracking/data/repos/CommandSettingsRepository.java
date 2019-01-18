package com.abhaya.vehicle.tracking.data.repos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.abhaya.vehicle.tracking.data.model.CommandSettings;

public interface CommandSettingsRepository extends TableRepository<CommandSettings, Long>,JpaSpecificationExecutor<CommandSettings>
{

}
