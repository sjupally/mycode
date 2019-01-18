package com.abhaya.vehicle.tracking.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.model.ModemDetails;
import com.abhaya.vehicle.tracking.data.repos.ModemDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.ModemDetailsSpecifications;
import com.abhaya.vehicle.tracking.events.CreateModemDetailsEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadModemDataSetEvent;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.NepheleValidationUtils;
import com.abhaya.vehicle.tracking.vos.ModemDetailsVO;

import lombok.extern.slf4j.Slf4j;

public interface ModemDetailsService 
{
	public void save(CreateModemDetailsEvent event);
	public ModemDetails getModemBySerialNumber(String serialNumber); 
	public PageReadEvent<ModemDetailsVO> readModemDeviceData(ReadModemDataSetEvent request);

	@Service
	@Slf4j
	public class impl implements ModemDetailsService
	{
		@Autowired private ModemDetailsRepository repository;

		@Override
		public void save(CreateModemDetailsEvent event) 
		{
			try
			{
				ModemDetailsVO modemDetailsVO = event.getModemDetailsVO();
				ModemDetails modemDetails = repository.findByIMEINumber(modemDetailsVO.getImeiNumber());
				if (modemDetails == null)
				{
					modemDetails = new ModemDetails();
					modemDetails.setCreatedDate(DateUitls.getCurrentSystemTimestamp());
				}
				modemDetails.setImeiNumber(modemDetailsVO.getImeiNumber());
				modemDetails.setImsiNumber(modemDetailsVO.getImsiNumber());
				modemDetails.setIpAddress(modemDetailsVO.getIpAddress());
				modemDetails.setSignalStrength(modemDetailsVO.getSignalStrength());
				modemDetails.setSimNumber(modemDetailsVO.getSimNumber());
				modemDetails.setUpdatedDate(DateUitls.getCurrentSystemTimestamp());
				modemDetails.setVersion(modemDetailsVO.getVersion());
				modemDetails.setSerialNumber(modemDetailsVO.getSerialNumber());
				
				repository.save(modemDetails);
			}
			catch (Exception e)
			{
				log.info("Exception while Saving Modem Details :: " + e.getCause());
			}
		}

		@Override
		public ModemDetails getModemBySerialNumber(String serialNumber) 
		{
			return repository.findBySerialNumber(serialNumber);
		}
		
		@Override
		public PageReadEvent<ModemDetailsVO> readModemDeviceData(ReadModemDataSetEvent request) 
		{
			Page<ModemDetails> dbContent = repository.findAll(new ModemDetailsSpecifications(request.getSerialNumber(),request.getImeiNumber(),request.getSearchValue(),request.getSearchDate()),ModemDetailsSpecifications.constructPageSpecification(request.getPageable().getPageNumber(),request.getPageable().getPageSize()));
			List<ModemDetailsVO> content = new ArrayList<>();
			for (ModemDetails record : NepheleValidationUtils.nullSafe(dbContent)) 
			{
				ModemDetailsVO details = ModemDetailsVO.builder()
					.imeiNumber(record.getImeiNumber())
					.id(record.getId())
					.imsiNumber(record.getImsiNumber())
					.ipAddress(record.getIpAddress())
					.serialNumber(record.getSerialNumber())
					.signalStrength(record.getSignalStrength())
					.simNumber(record.getSimNumber())
					.version(record.getVersion())
					.createdDate(record.getCreatedDate() != null ? DateUitls.getStringFromTimestamp(record.getCreatedDate()) : null)
					.mobileNumber(record.getMobileNumber())
					.build();
					content.add(details);
				}
			Page<ModemDetailsVO> page = new PageImpl<>(content, request.getPageable(), dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}
	}
}
