package com.abhaya.vehicle.tracking.data.repos;

import com.abhaya.vehicle.tracking.data.model.CommandExecutionResponseHistory;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommandExecutionResponseHistoryRepository extends TableRepository<CommandExecutionResponseHistory, Long>,JpaSpecificationExecutor<CommandExecutionResponseHistory>
{

}
