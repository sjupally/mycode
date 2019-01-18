package com.abhaya.vehicle.tracking.data.repos;

import com.abhaya.vehicle.tracking.data.model.DeviceCommunicationView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DeviceCommunicationViewRepository extends JpaRepository<DeviceCommunicationView, Long>, JpaSpecificationExecutor<DeviceCommunicationView> {

}
