package com.abhaya.vehicle.tracking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.data.model.CitizenDetails;
import com.abhaya.vehicle.tracking.data.repos.CitizenDetailsRepository;
import com.abhaya.vehicle.tracking.util.DateUitls;

public interface CitizenService 
{
	public CitizenDetails checkCitizen(String mobileNumber);
	
	@Service
	public class impl implements CitizenService
	{
		@Autowired private CitizenDetailsRepository repository;

		@Override
		public CitizenDetails checkCitizen(String mobileNumber) 
		{
			CitizenDetails citizenDetails = repository.getCitizenByMobileNumber(mobileNumber);
			if (citizenDetails == null)
			{
				citizenDetails = CitizenDetails.builder()
						.createdOn(DateUitls.getCurrentSystemTimestamp())
						.mobileNumber(mobileNumber)
						.build();
				citizenDetails = repository.save(citizenDetails);
			}
			return citizenDetails;
		}
	}
}
