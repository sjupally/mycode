package com.abhaya.vehicle.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.model.PanicSummaryView;
import com.abhaya.vehicle.tracking.data.repos.PanicSummaryReportSQLRepository;
import com.abhaya.vehicle.tracking.data.repos.PanicSummaryViewRepository;
import com.abhaya.vehicle.tracking.data.repos.PanicSummaryViewSpecifications;
import com.abhaya.vehicle.tracking.events.ReadPanicSummaryEvent;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.utils.PanicSummaryVO;

import lombok.extern.slf4j.Slf4j;

public interface PanicSummaryService 
{
	public List<PanicSummaryVO> readPanicSummaryData(ReadPanicSummaryEvent request);
	
	public List<PanicSummaryVO> readData(ReadPanicSummaryEvent request) ;

	@Slf4j
	@Service
	public class impl implements PanicSummaryService
	{
		@Autowired private PanicSummaryReportSQLRepository sqlRepository;
		
		@Autowired private PanicSummaryViewRepository panicSummaryViewRepository;

		@Override
		public List<PanicSummaryVO> readPanicSummaryData(ReadPanicSummaryEvent request)
		{
			PanicSummaryVO anicSummaryVO = PanicSummaryVO.builder()
					.cityId(request.getCityId())
					.districtId(request.getDistrictId())
					.stateId(request.getStateId())
					.eventSource(request.getEventSource())
					.build();
			
			log.info(" Panic payload :"+ anicSummaryVO.toString());
			
			List<PanicSummaryVO> list = sqlRepository.getPanicSummary(anicSummaryVO);
			log.info(" Panic List :"+ list.toString());
			return list;
		}
		
		
		@Override
		public List<PanicSummaryVO> readData(ReadPanicSummaryEvent request) 
		{
			List<PanicSummaryView> dbContent = panicSummaryViewRepository.findAll(new PanicSummaryViewSpecifications(request.getEventSource(),request.getStateId(),request.getDistrictId(), request.getCityId(),request.getSearchDate()));
			List<PanicSummaryVO> content = ObjectMapperUtils.mapAll(dbContent, PanicSummaryVO.class);
			return content;
		}
	}
}
