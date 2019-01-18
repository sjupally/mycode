package com.abhaya.vehicle.tracking.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.model.RawPacketData;
import com.abhaya.vehicle.tracking.data.repos.RawDataSpecifications;
import com.abhaya.vehicle.tracking.data.repos.RawPacketDataRepository;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadRawDataSetEvent;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.ObjectMapperUtils;
import com.abhaya.vehicle.tracking.vos.RawDataPacketVO;

public interface RawDataPacketService 
{
	public void save(RawDataPacketVO dataPacketVO);
	public List<RawPacketData> getDataBySerialNumberAndDate(String siteId, String date);
	public PageReadEvent<RawDataPacketVO> readData(ReadRawDataSetEvent request);
	
	@Service
	public class impl implements RawDataPacketService
	{
		@Autowired private RawPacketDataRepository rawPacketDataRepositary;

		@Override
		public void save(RawDataPacketVO dataPacketVO) 
		{
			RawPacketData rawPacketData = RawPacketData.builder()
					  .createdDate(DateUitls.getCurrentSystemTimestamp())
					  .rawData(dataPacketVO.getRawData())
					  .serialNumber(dataPacketVO.getSerialNumber())
					  .packetDate(dataPacketVO.getPacketDate())
					  .packetTime(dataPacketVO.getPacketTime())
					  .imeiNumber(dataPacketVO.getImeiNumber())
					  .build();
			rawPacketDataRepositary.save(rawPacketData);
			rawPacketDataRepositary.flush();
		}

		@Override 
		public List<RawPacketData> getDataBySerialNumberAndDate(String serialNumber, String date) 
		{
			return rawPacketDataRepositary.getDataBySerialNumberAndDate(serialNumber,date.replaceAll("-", "/"));
		}

		@Override
		public PageReadEvent<RawDataPacketVO> readData(ReadRawDataSetEvent request) 
		{
			Page<RawPacketData> dbContent = rawPacketDataRepositary.findAll(new RawDataSpecifications(request.getSerialNumber(),request.getImeiNumber(),request.getFromDate(),request.getToDate(),request.getPacketDate(),request.getSearchValue()),RawDataSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),request.getPageable().getPageSize()));
			List<RawDataPacketVO> content = ObjectMapperUtils.mapAll(dbContent.getContent(), RawDataPacketVO.class);
			Page<RawDataPacketVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
	}
}
