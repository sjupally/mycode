package com.abhaya.vehicle.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.repos.VehicleStatusInfoReportSQLRepository;
import com.abhaya.vehicle.tracking.events.ReadVehicleStatusInfoReportEvent;
import com.abhaya.vehicle.tracking.utils.VehicleStatusVO;

import lombok.extern.slf4j.Slf4j;

public interface VehicleStatusDetailsService
{

	public List<VehicleStatusVO> readVehicleStatusInfo(ReadVehicleStatusInfoReportEvent request);

	@Slf4j
	@Service
	public class impl implements VehicleStatusDetailsService
	{
		@Autowired
		private VehicleStatusInfoReportSQLRepository vehicleStatusInfoReportSQLRepository;
		@Override
		public List<VehicleStatusVO> readVehicleStatusInfo(ReadVehicleStatusInfoReportEvent request) {
			VehicleStatusVO vehicleStatusVO = VehicleStatusVO.builder()
					.cityId(request.getCityId())
					.districtId(request.getDistrictId())
					.stateId(request.getStateId())
					.date(request.getDate())
					.build();
			List<VehicleStatusVO> list = vehicleStatusInfoReportSQLRepository.getVehicleStatusInfo(vehicleStatusVO);
			return list;
		}
	}
}
