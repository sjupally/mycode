package com.abhaya.vehicle.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.model.PoliceStationDetails;
import com.abhaya.vehicle.tracking.data.repos.PoliceStationDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.PoliceStationDetailsSpecifications;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadPoliceStationDetailsSetEvent;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.vos.PoliceStationDetailsVO;

public interface PoliceStationDetailsService 
{
	public PageReadEvent<PoliceStationDetailsVO> readPoliceStationsData(ReadPoliceStationDetailsSetEvent request);
	
	@Service
	public class impl implements PoliceStationDetailsService
	{
		@Autowired private PoliceStationDetailsRepository repository;

		@Override
		public PageReadEvent<PoliceStationDetailsVO> readPoliceStationsData(ReadPoliceStationDetailsSetEvent request) 
		{
			Page<PoliceStationDetails> dbContent = repository.findAll(new PoliceStationDetailsSpecifications(request.getStationName(),request.getMobileNumber()),PoliceStationDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			List<PoliceStationDetailsVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), PoliceStationDetailsVO.class);
			return new PageReadEvent<>(new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0));
		}
	}
}
