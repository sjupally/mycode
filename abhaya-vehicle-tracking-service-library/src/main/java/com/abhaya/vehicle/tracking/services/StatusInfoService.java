package com.abhaya.vehicle.tracking.services;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.model.StatusInfo;
import com.abhaya.vehicle.tracking.data.repos.DriverDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.StatusInfoRepository;
import com.abhaya.vehicle.tracking.data.repos.StatusInfoSpecifications;
import com.abhaya.vehicle.tracking.data.repos.VehicleDetailsRepository;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadStatusInfoSetEvent;
import com.abhaya.vehicle.tracking.util.CommonUtility;
import com.abhaya.vehicle.tracking.util.NepheleValidationUtils;
import com.abhaya.vehicle.tracking.utils.DateUtils;
import com.abhaya.vehicle.tracking.vos.StatusInfoVO;
import com.abhaya.vehicle.tracking.vos.VehicleStatusInfoVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StatusInfoService 
{
	@Autowired private VehicleDetailsRepository vehicleDetailsRepository;
	@Autowired private DriverDetailsRepository driverDetailsRepository;
	@Autowired private StatusInfoRepository repository;

	public void save(StatusInfoVO statusInfoVO) 
	{
		StatusInfo statusInfo = findVehicleStatus(statusInfoVO.getStatus().replace(" ", "").trim());
		statusInfo.setCreatedDate(DateUtils.getCurrentSystemTimestamp());
		statusInfo.setRfId(statusInfoVO.getRfId());
		statusInfo.setSerialNumber(statusInfoVO.getSerialNumber());
		statusInfo.setImeiNumber(statusInfoVO.getImeiNumber());
		statusInfo.setPacketDate(statusInfoVO.getPacketDate());
		statusInfo.setPacketTime(statusInfoVO.getPacketTime());
		statusInfo.setStatusParam(statusInfoVO.getStatus());
		statusInfo.setTrackId(statusInfoVO.getTrackId());
		statusInfo.setVehicleDetails(vehicleDetailsRepository.getBySerialNumber(statusInfoVO.getSerialNumber()));
		statusInfo.setDriverDetails(driverDetailsRepository.getByRFID(statusInfoVO.getRfId()));
		statusInfo.setTrackId(statusInfoVO.getTrackId());

		repository.save(statusInfo);
	}
	public PageReadEvent<VehicleStatusInfoVO> readData(ReadStatusInfoSetEvent request) 
	{
		StatusInfoSpecifications specifications = new StatusInfoSpecifications(request.getDlNumber(),request.getRcNumber(),request.getSerialNumber(),request.getRfId(),request.getVehicleId(),request.getTrackId());
		Page<StatusInfo> dbContent = repository.findAll(specifications,StatusInfoSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
		
		List<VehicleStatusInfoVO> content = new ArrayList<>();
		for (StatusInfo record : NepheleValidationUtils.nullSafe(dbContent)) 
		{
			ModelMapper mapper = new ModelMapper();
			VehicleStatusInfoVO vehicleStatusInfoVO = mapper.map(record, VehicleStatusInfoVO.class);
			vehicleStatusInfoVO.setRcNumber(record.getVehicleDetails() != null ? record.getVehicleDetails().getRcNumber() : null);
			vehicleStatusInfoVO.setDlNumber(record.getDriverDetails() != null ? record.getDriverDetails().getDlNumber() : null);
			content.add(vehicleStatusInfoVO);
		}
		return new PageReadEvent<>(new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0));
	}
	
	public  StatusInfo findVehicleStatus(String vehicleStatus) 
	{
		log.info("Status String ::: " + vehicleStatus);
		long hex = Long.parseLong(vehicleStatus,16);
		String statusBinary = Long.toBinaryString(hex);
		if (statusBinary.length() < 32)
		{
			statusBinary = CommonUtility.appendZeros(statusBinary, 32-statusBinary.length());
		}
		log.info("Status binary after appending zeros ::: " + statusBinary);
		
		StringBuffer sb = new StringBuffer(statusBinary);
		log.info("Status binary Reverse ::: " + sb.reverse());
		String binary = sb.reverse().toString();
		
		
		StatusInfo statusVO = new StatusInfo();
		for (int i = 0; i < binary.length(); i++)
		{
			switch (i)
			{
				case 0 : 
					  if (binary.charAt(i) == '1') statusVO.setIgnitionStatus("OFF");
					  else statusVO.setIgnitionStatus("ON");
					break;
				case 1 : 
					  if (binary.charAt(i) == '1') statusVO.setVehicleStagnantStatus("Vehicle Stagnant");
					  else statusVO.setVehicleStagnantStatus("Default");
					break;
				case 2 : 
					  if (binary.charAt(i) == '1') statusVO.setIotDeviceDetachedStatus("IOT Device Detached");
					  else statusVO.setIotDeviceDetachedStatus("Default");
					break;
				case 3 : 
					  if (binary.charAt(i) == '1') statusVO.setPanicButtonStatus("ON");
					  else statusVO.setPanicButtonStatus("OFF");
					break;
				case 4 : 
					  if (binary.charAt(i) == '1') statusVO.setVehicleBatteryStatus("Vehicle Battery Low");
					  else statusVO.setVehicleBatteryStatus("Default");
					break;
				case 5 : 
					  if (binary.charAt(i) == '1') statusVO.setDeviceBatteryStatus("Device Battery Low");
					  else statusVO.setDeviceBatteryStatus("Default");
					break;
				case 6 : 
					  if (binary.charAt(i) == '1') statusVO.setEngineStatus("ON");
					  else statusVO.setEngineStatus("OFF");
					break;
				case 7 : 
					  if (binary.charAt(i) == '1') statusVO.setVehicleIdealStatus("Vehicle Ideal");
					  else statusVO.setVehicleIdealStatus("Default");
					break;
				case 8 : 
					  if (binary.charAt(i) == '1') statusVO.setVehicleParkingStatus("Vehicle Parked");
					  else statusVO.setVehicleParkingStatus("Default");
					break;
				case 9 : 
					  if (binary.charAt(i) == '1') statusVO.setDevicePowerDisconnectStatus("Device Power Disconnect");
					  else statusVO.setDevicePowerDisconnectStatus("Default");
					break;
				case 10 : 
					  if (binary.charAt(i) == '1') statusVO.setDeviceTamperStatus("IOT Device Tamper");
					  else statusVO.setDeviceTamperStatus("Default");
					break;
				case 11 : 
					  if (binary.charAt(i) == '1') statusVO.setPanicButtonForEngineSwithOff("Panic Button for Engine Switch OFF");
					  else statusVO.setPanicButtonForEngineSwithOff("Default");
					break;
				case 12 : 
					  if (binary.charAt(i) == '1') statusVO.setOverSpeed("Over Speed");
					  else statusVO.setOverSpeed("Default");
					break;
				case 13 : 
					  if (binary.charAt(i) == '1') statusVO.setSleepMode("Sleep Mode");
					  else statusVO.setSleepMode("Default");
					break;
			}
		}
		return statusVO;
	}
}
