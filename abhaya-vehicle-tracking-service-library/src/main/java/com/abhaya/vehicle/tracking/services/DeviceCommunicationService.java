package com.abhaya.vehicle.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.data.model.DeviceCommunication;
import com.abhaya.vehicle.tracking.data.model.DeviceCommunicationView;
import com.abhaya.vehicle.tracking.data.repos.DeviceCommunicationRepository;
import com.abhaya.vehicle.tracking.data.repos.DeviceCommunicationSpecifications;
import com.abhaya.vehicle.tracking.data.repos.DeviceCommunicationSummarySQLRepository;
import com.abhaya.vehicle.tracking.data.repos.DeviceCommunicationViewRepository;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadDeviceCommunicationEvent;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.utils.DeviceCommunicationSummaryVO;
import com.abhaya.vehicle.tracking.vos.DeviceCommunicationVO;

import lombok.extern.slf4j.Slf4j;

public interface DeviceCommunicationService {
    public void save(DeviceCommunicationVO deviceCommunication);

    public PageReadEvent<DeviceCommunicationVO> readData(ReadDeviceCommunicationEvent request);

    List<DeviceCommunicationSummaryVO> getDeviceCommunicationSummary(ReadDeviceCommunicationEvent request);

    List<DeviceCommunicationSummaryVO> getDeviceCommunicationMovementSummary(ReadDeviceCommunicationEvent request);

    @Service
    @Slf4j
    public class impl implements DeviceCommunicationService {
        @Autowired
        private DeviceCommunicationRepository deviceCommunicationRepository;
        @Autowired
        private DeviceCommunicationViewRepository viewRepository;
        @Autowired
        private DeviceCommunicationSummarySQLRepository deviceCommunicationSummarySQLRepository;

        @Override
        public void save(DeviceCommunicationVO deviceCommunicationVO) {
        	
        	if(!StringUtils.isEmpty(deviceCommunicationVO.getSerialNumber()))
        	{
                try {
                    DeviceCommunication deviceCommunctionDetails = deviceCommunicationRepository.getBySerialNumber(deviceCommunicationVO.getSerialNumber());
                    if (deviceCommunctionDetails == null){
                        deviceCommunctionDetails = new DeviceCommunication();
                        deviceCommunctionDetails.setSerialNumber(deviceCommunicationVO.getSerialNumber());
                    }
                    deviceCommunctionDetails.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
                    deviceCommunctionDetails.setPrevPacketDate(!StringUtils.isEmpty(deviceCommunctionDetails.getPacketDate()) ? deviceCommunctionDetails.getPacketDate() : deviceCommunicationVO.getPacketDate());
    				deviceCommunctionDetails.setPrevPacketTime(!StringUtils.isEmpty(deviceCommunctionDetails.getPacketTime()) ? deviceCommunctionDetails.getPacketTime() : deviceCommunicationVO.getPacketTime());
    				deviceCommunctionDetails.setPrevLangitude(!StringUtils.isEmpty(deviceCommunctionDetails.getLangitude()) ? deviceCommunctionDetails.getLangitude() : deviceCommunicationVO.getLangitude());
    				deviceCommunctionDetails.setPrevLatitude(!StringUtils.isEmpty(deviceCommunctionDetails.getLatitude()) ? deviceCommunctionDetails.getLatitude() : deviceCommunicationVO.getLatitude());
    				
    				deviceCommunctionDetails.setPacketDate(deviceCommunicationVO.getPacketDate());
    	            deviceCommunctionDetails.setPacketTime(deviceCommunicationVO.getPacketTime());
    	            deviceCommunctionDetails.setImeiNumber(deviceCommunicationVO.getImeiNumber());
    	            deviceCommunctionDetails.setLangitude(deviceCommunicationVO.getLangitude());
    	            deviceCommunctionDetails.setLatitude(deviceCommunicationVO.getLatitude());
    	            deviceCommunctionDetails.setLocation(deviceCommunicationVO.getLocation());
                    deviceCommunctionDetails.setStatus("Communicating");
                    deviceCommunctionDetails.setIgnitionStatus(deviceCommunicationVO.getIgnitionStatus());
                    deviceCommunctionDetails.setEngineStatus(deviceCommunicationVO.getEngineStatus());
                    deviceCommunctionDetails.setPanicButtonStatus(deviceCommunicationVO.getPanicButtonStatus());
                    
                        if(deviceCommunctionDetails.getPrevLatitude().equalsIgnoreCase(deviceCommunctionDetails.getLatitude())
                        		|| deviceCommunctionDetails.getPrevLangitude().equalsIgnoreCase(deviceCommunctionDetails.getLangitude())) {
                        	
                        	if(deviceCommunicationVO.getEngineStatus().equalsIgnoreCase("ON")) {
                        		deviceCommunctionDetails.setMovement("Idling");
                        	}else {
                        		deviceCommunctionDetails.setMovement("Stopped");
                        	}
                        	
                        }else {
                        	deviceCommunctionDetails.setMovement("Running");
                        	/*if(deviceCommunicationVO.getEngineStatus().equalsIgnoreCase("ON")) {
                        		deviceCommunctionDetails.setMovement("Running");
                        	}else {
                        		deviceCommunctionDetails.setMovement("Towing");
                        	}*/
                        }
                        
                        if(deviceCommunicationVO.getBatteryStatus().equalsIgnoreCase("Device Battery Low")) {
                        	deviceCommunctionDetails.setBatteryStatus("Low");
                        }else {
                        	deviceCommunctionDetails.setBatteryStatus("Normal");
                        }
                        
                        if(deviceCommunicationVO.getTamperStatus().equalsIgnoreCase("IOT Device Tamper")) {
                        	deviceCommunctionDetails.setTamperStatus("YES");
                        }else {
                        	deviceCommunctionDetails.setTamperStatus("NO");
                        }
                   
                    
                    deviceCommunicationRepository.save(deviceCommunctionDetails);
                } catch (Exception e) {
                    log.info("Exception while saving DeviceCommunicationService Details :: " + e.getCause(), e);
                }
        	}
        	else 
        	{
        		log.info("Serial number is not available while saving DeviceCommunication");
        	}  
        }

        @Override
        public PageReadEvent<DeviceCommunicationVO> readData(ReadDeviceCommunicationEvent request) {
            DeviceCommunicationSpecifications specifications = new DeviceCommunicationSpecifications(request.getStatus(), request.getStateId(), request.getDistrictId(), request.getCityId(), request.getSearchValue(), request.getSearchDate(),
            		request.getMovement(),request.getBatteryStatus(), request.getIgnitionStatus(), request.getEngineStatus(), request.getTamperStatus(), request.getPanicButtonStatus());
            Page<DeviceCommunicationView> dbContent = viewRepository.findAll(specifications, DeviceCommunicationSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
            List<DeviceCommunicationVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), DeviceCommunicationVO.class);
            Page<DeviceCommunicationVO> page = new PageImpl<>(content, request.getPageable(), dbContent != null ? dbContent.getTotalElements() : 0);
            return new PageReadEvent<>(page);
        }

        @Override
        public List<DeviceCommunicationSummaryVO> getDeviceCommunicationSummary(ReadDeviceCommunicationEvent request) {
            DeviceCommunicationSummaryVO deviceCommunicationSummaryVO = DeviceCommunicationSummaryVO.builder()
                    .cityId(request.getCityId())
                    .districtId(request.getDistrictId())
                    .stateId(request.getStateId())
                    .build();
            return deviceCommunicationSummarySQLRepository.getDeviceCommunicationSummary(deviceCommunicationSummaryVO, request.getIsDistrictWise());
        }

        @Override
        public List<DeviceCommunicationSummaryVO> getDeviceCommunicationMovementSummary(ReadDeviceCommunicationEvent request) {
            DeviceCommunicationSummaryVO deviceCommunicationSummaryVO = DeviceCommunicationSummaryVO.builder()
                    .cityId(request.getCityId())
                    .districtId(request.getDistrictId())
                    .stateId(request.getStateId())
                    .build();
            return deviceCommunicationSummarySQLRepository.getDeviceCommunicationMovementSummary(deviceCommunicationSummaryVO, request.getIsDistrictWise());
        }
    }
}
