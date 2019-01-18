package com.abhaya.vehicle.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.data.model.DriverDetails;
import com.abhaya.vehicle.tracking.data.model.DriverDutyDetails;
import com.abhaya.vehicle.tracking.data.model.VehicleDetails;
import com.abhaya.vehicle.tracking.data.model.VehicleDriverDetailsView;
import com.abhaya.vehicle.tracking.data.repos.DriverAuthReportSQLRepository;
import com.abhaya.vehicle.tracking.data.repos.DriverDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.DriverDutyDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.VehicleDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.VehicleDriverDetailsSpecifications;
import com.abhaya.vehicle.tracking.data.repos.VehicleDriverDetailsViewRepository;
import com.abhaya.vehicle.tracking.events.CreateDriverDutyEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDriverAuthReportEvent;
import com.abhaya.vehicle.tracking.events.ReadDriverDutyDetailsSetEvent;
import com.abhaya.vehicle.tracking.model.ESDriverDutyDetails;
import com.abhaya.vehicle.tracking.repository.ESDriverDutyDetailsRepository;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.utils.DriverAuthVO;
import com.abhaya.vehicle.tracking.vos.DriverRFIDVO;
import com.abhaya.vehicle.tracking.vos.VehicleDriverDetailsVO;

import lombok.extern.slf4j.Slf4j;

public interface DriverDutyDetailsService 
{

	public void save(CreateDriverDutyEvent event);
	public PageReadEvent<VehicleDriverDetailsVO> readData(ReadDriverDutyDetailsSetEvent request);
	
	public List<DriverAuthVO> readDriverAuthData(ReadDriverAuthReportEvent request);
	
	@Slf4j
	@Service
	public class impl implements DriverDutyDetailsService
	{
		@Autowired private DriverDutyDetailsRepository repository;
		@Autowired private DriverDetailsRepository driverDetailsRepository;
		@Autowired private VehicleDetailsRepository vehicleDetailsRepository;
		@Autowired private VehicleDriverDetailsViewRepository vehicleDriverDetailsViewRepository;
		@Autowired private ESDriverDutyDetailsRepository esDriverDutyDetailsRepository;
		
		@Autowired private DriverAuthReportSQLRepository driverAuthReportSQLRepository;

		@Override
		public void save(CreateDriverDutyEvent event) 
		{
			try
			{
				log.info(" Driver Duty Details save :: " + event.getDriverRFIDVO());
				DriverRFIDVO driverRFIDVO = event.getDriverRFIDVO();
				DriverDetails driverDetails = driverDetailsRepository.getByRFID(driverRFIDVO.getDriverRFId());
				VehicleDetails vehicleDetails = vehicleDetailsRepository.getBySerialNumber(driverRFIDVO.getSerialNumber());

				DriverDutyDetails driverDutyDetails = repository.isDriverDutyExist(driverDetails,vehicleDetails,driverRFIDVO.getPacketDate());
			    if (StringUtils.isEmpty(driverDutyDetails))
			    {
			    	driverDutyDetails = new DriverDutyDetails();
					driverDutyDetails.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
					driverDutyDetails.setDriverDetails(driverDetails);
					driverDutyDetails.setVehicleDetails(vehicleDetails);
					driverDutyDetails.setPacketDate(driverRFIDVO.getPacketDate());
					driverDutyDetails.setPacketTime(driverRFIDVO.getPacketTime());
					driverDutyDetails = repository.save(driverDutyDetails);
			    }
			    ESDriverDutyDetails esDriverDutyDetails = esDriverDutyDetailsRepository.findByDlNumberAndRcNumberAndPacketDate(driverDetails.getDlNumber(),vehicleDetails.getRcNumber(),driverRFIDVO.getPacketDate());
			    if (StringUtils.isEmpty(esDriverDutyDetails))
			    {
			    	esDriverDutyDetails =  ESDriverDutyDetails.builder()
			    			.createdDate(DateUitls.getCurrentTimeInStringFormat())
			    			.dlExpiryDate(DateUitls.getStringFromSqlDate(driverDetails.getDlExpiryDate(), "dd-MM-yyyy"))
			    			.dlNumber(driverDetails.getDlNumber())
			    			.driverContactNumber(driverDetails.getContactNumber().toString())
			    			.driverName(driverDetails.getDriverName())
			    			.id(driverDutyDetails.getId().toString())
			    			.ownerContactNumber(vehicleDetails.getOwnerDetails().getOwnerContactNumber().toString())
			    			.ownerName(vehicleDetails.getOwnerDetails().getOwnerName())
			    			.packetDate(driverRFIDVO.getPacketDate())
			    			.packetTime(driverRFIDVO.getPacketTime())
			    			.rcExpiryDate(DateUitls.getStringFromSqlDate(vehicleDetails.getRcExpiryDate(), "dd-MM-yyyy"))
			    			.rcNumber(vehicleDetails.getRcNumber())
			    			.registrationDate(DateUitls.getStringFromSqlDate(vehicleDetails.getRegistrationDate(), "dd-MM-yyyy"))
			    			.rfId(driverDetails.getRfId())
			    			.build();
			    	esDriverDutyDetailsRepository.save(esDriverDutyDetails);
			    }
			}
			catch (Exception e)
			{
				log.info("Exception in Driver Duty Saving - RFID is not mapped to the Driver DL number :: ");
			}
		}

		@Override
		public PageReadEvent<VehicleDriverDetailsVO> readData(ReadDriverDutyDetailsSetEvent request) 
		{
			Page<VehicleDriverDetailsView> dbContent = vehicleDriverDetailsViewRepository.findAll(new VehicleDriverDetailsSpecifications(request.getSearchValue(),request.getRcNumber(),request.getPacketDate(),request.getDistrictId(), request.getCityId(),request.getDate()),VehicleDriverDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));

			List<VehicleDriverDetailsVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), VehicleDriverDetailsVO.class);
			Page<VehicleDriverDetailsVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}

		@Override
		public List<DriverAuthVO> readDriverAuthData(ReadDriverAuthReportEvent request) {
			

			DriverAuthVO driverAuthVO = DriverAuthVO.builder()
					.cityId(request.getCityId())
					.districtId(request.getDistrictId())
					.stateId(request.getStateId())
					.searchDate(request.getSearchDate())
					.build();
			List<DriverAuthVO> list = driverAuthReportSQLRepository.getDriverAuthenticationDetails(driverAuthVO);
			return list;
		
		}
	}
}
