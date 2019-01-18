package com.abhaya.vehicle.tracking.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.constants.Constants;
import com.abhaya.vehicle.tracking.data.model.CitizenDetails;
import com.abhaya.vehicle.tracking.data.model.EmergencyContactNumbers;
import com.abhaya.vehicle.tracking.data.repos.CitizenDetailsRepository;
import com.abhaya.vehicle.tracking.data.repos.EmergencyContactNumbersRepository;
import com.abhaya.vehicle.tracking.data.repos.EmergencyContactNumbersSpecifications;
import com.abhaya.vehicle.tracking.data.repos.SpecificationUtils;
import com.abhaya.vehicle.tracking.events.CreateEmergencyContactsDataEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadEmergencyContactsSetEvent;
import com.abhaya.vehicle.tracking.util.NepheleValidationUtils;
import com.abhaya.vehicle.tracking.vos.EmergencyContactNumbersVO;
import com.abhaya.vehicle.tracking.vos.ResponseVO;

import lombok.extern.slf4j.Slf4j;

public interface EmergencyContactsService 
{

	public ResponseVO save(CreateEmergencyContactsDataEvent event);
	public void deleteEmgContacts(String citizenMobileNumber, String emergencyContactNumber);
	public PageReadEvent<EmergencyContactNumbersVO> readData(ReadEmergencyContactsSetEvent request);
	
	@Service
	@Slf4j
	public class impl implements EmergencyContactsService
	{
		@Autowired private CitizenService citizenService;
		@Autowired private CitizenDetailsRepository citizenDetailsRepository;
		@Autowired private EmergencyContactNumbersRepository repository;

		@Override
		public ResponseVO save(CreateEmergencyContactsDataEvent event) 
		{
			ResponseVO responseVO = new ResponseVO();
			try
			{
				List<EmergencyContactNumbersVO> list = event.getEmergencyContactNumbersVOs();
				CitizenDetails citizenDetails = citizenService.checkCitizen(list.get(0).getCitizenMobileNumber());
				repository.deleteAll(repository.deleteExistingByCitizen(citizenDetails));
				List<EmergencyContactNumbers> result = list.stream().map(temp -> 
				{
					 EmergencyContactNumbers contactNumbers = EmergencyContactNumbers.builder()
								.citizenDetails(citizenDetails)
								.emergencyContactNumber(temp.getEmergencyContactNumber())
								.name(temp.getName())
								.build();
			            return contactNumbers;
			        }).collect(Collectors.toList());
				 repository.saveAll(result);				
				responseVO.setCode(Constants.ResponseMessages.CODE_200);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_200);
			}
			catch (Exception e)
			{
				responseVO.setCode(Constants.ResponseMessages.CODE_500);
				responseVO.setMessage(Constants.ResponseMessages.MESSAGE_500 + " " +e.getMessage());
				log.info("Exception while saving Emergency contacts :: " + e.getCause() ,e);
			}
			return responseVO;
		}
		
		@Override
		public PageReadEvent<EmergencyContactNumbersVO> readData(ReadEmergencyContactsSetEvent request) 
		{
			Page<EmergencyContactNumbers> dbContent = repository.findAll(new EmergencyContactNumbersSpecifications(request.getEmergencyContactNumber(),request.getCitizenMobileNumber()),
					EmergencyContactNumbersSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize(),
					SpecificationUtils.sortBySortKey(request.getProperty(), request.getDirection())));
			
			List<EmergencyContactNumbersVO> content = new ArrayList<>();
			for (EmergencyContactNumbers record : NepheleValidationUtils.nullSafe(dbContent)) 
			{
				EmergencyContactNumbersVO details = EmergencyContactNumbersVO.builder()
					.id(record.getId())
					.emergencyContactNumber(record.getEmergencyContactNumber())
					.name(record.getName())
					.citizenMobileNumber(record.getCitizenDetails().getMobileNumber())
					.build();
				content.add(details); 
			}
			Page<EmergencyContactNumbersVO> page = new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0);
			return new PageReadEvent<>(page);
		}

		@Override
		public void deleteEmgContacts(String citizenMobileNumber, String emergencyContactNumber) 
		{
			CitizenDetails citizenDetails = citizenDetailsRepository.getCitizenByMobileNumber(citizenMobileNumber);
			EmergencyContactNumbers contactNumbers = repository.getByCitizenAndNumber(citizenDetails,emergencyContactNumber);
			if (contactNumbers == null)
			{
				repository.delete(contactNumbers);
			}
		}
	}
}
