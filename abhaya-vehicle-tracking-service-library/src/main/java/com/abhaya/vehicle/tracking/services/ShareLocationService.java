package com.abhaya.vehicle.tracking.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.constants.Constants;
import com.abhaya.vehicle.tracking.data.model.EmergencyContactNumbers;
import com.abhaya.vehicle.tracking.data.model.TripDetails;
import com.abhaya.vehicle.tracking.data.repos.EmergencyContactNumbersRepository;
import com.abhaya.vehicle.tracking.data.repos.TripDetailsRepository;
import com.abhaya.vehicle.tracking.util.GoogleURLShortening;
import com.abhaya.vehicle.tracking.util.SMSConfiguration;

import lombok.extern.slf4j.Slf4j;

public interface ShareLocationService 
{
	public void shareLocation(String citizenContactNumber);

	@Slf4j
	@Service
	public class impl implements ShareLocationService
	{
		@Value("${route.url}")
		private String routeShareUrl;

		@Autowired private SMSConfiguration smsConfiguration;
		@Autowired private TripDetailsRepository tripDetailsRepository;
		@Autowired private EmergencyContactNumbersRepository repository;

		@Override
		public void shareLocation(String citizenContactNumber)
		{
			TripDetails tripDetails = tripDetailsRepository.getActiveTripByCitizenNumber(citizenContactNumber);
			if (!StringUtils.isEmpty(tripDetails))
			{
				List<EmergencyContactNumbers> contactNumbers = repository.getByCitizenMobileNumber(citizenContactNumber);
				String numbersToShare = contactNumbers.stream().map(EmergencyContactNumbers::getEmergencyContactNumber).collect(Collectors.joining(","));
				String rcNumber = tripDetails.getVehicleDetails().getRcNumber();
				
				String shortnURL = GoogleURLShortening.shortenUrl(routeShareUrl + "vehicleNumber=" + rcNumber + "&citizenMobileNumber=" + citizenContactNumber + "&isShared=true");
				log.info("shortnURL " + shortnURL);
				String message  = String.format(Constants.SHARE_LOCATION_MSG, rcNumber,tripDetails.getDriverDetails().getDriverName(),tripDetails.getDriverDetails().getContactNumber(),shortnURL);

				log.info("numbersToShare " + numbersToShare + " message " + message);
				smsConfiguration.sendSms(numbersToShare, message); 
			}
		}
	}
}
