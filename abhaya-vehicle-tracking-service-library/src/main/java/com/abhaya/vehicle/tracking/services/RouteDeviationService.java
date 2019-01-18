package com.abhaya.vehicle.tracking.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.constants.Constants;
import com.abhaya.vehicle.tracking.data.model.RouteDeviation;
import com.abhaya.vehicle.tracking.data.model.RouteDeviationDetailsView;
import com.abhaya.vehicle.tracking.data.model.TripDetails;
import com.abhaya.vehicle.tracking.data.repos.RouteDeviationDetailsViewRepository;
import com.abhaya.vehicle.tracking.data.repos.RouteDeviationRepository;
import com.abhaya.vehicle.tracking.data.repos.RouteDeviationSpecifications;
import com.abhaya.vehicle.tracking.data.repos.TripDetailsRepository;
import com.abhaya.vehicle.tracking.events.CreateRouteDeviationEvent;
import com.abhaya.vehicle.tracking.events.PageReadEvent;
import com.abhaya.vehicle.tracking.events.ReadRouteDeviationSetEvent;
import com.abhaya.vehicle.tracking.model.ESRouteDeviation;
import com.abhaya.vehicle.tracking.repository.ESRouteDeviationRepository;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.Geocoder;
import com.abhaya.vehicle.tracking.util.NepheleValidationUtils;
import com.abhaya.vehicle.tracking.util.SMSConfiguration;
import com.abhaya.vehicle.tracking.utils.DateUtils;
import com.abhaya.vehicle.tracking.vos.RouteDeviationVO;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

public interface RouteDeviationService 
{
	public void save(CreateRouteDeviationEvent event);
	public void sendSms(String citizemMobileNumber) throws Exception;
	public PageReadEvent<RouteDeviationVO> readData(ReadRouteDeviationSetEvent request);

	@Slf4j
	@Service
	public class impl implements RouteDeviationService
	{
		@Autowired private SMSConfiguration smsConfiguration;
		@Autowired private RouteDeviationRepository repository;
		@Autowired private RouteDeviationDetailsViewRepository routeDeviationDetailsViewRepository;
		@Autowired private TripDetailsRepository tripDetailsRepository;
		@Autowired private ESRouteDeviationRepository esRouteDeviationRepository;

		@Override
		public void save(CreateRouteDeviationEvent event) 
		{
			try
			{
				RouteDeviationVO deviationVO = event.getRouteDeviationVO();
				TripDetails tripDetails = tripDetailsRepository.getActiveTripByCitizenNumber(deviationVO.getCitizenMobileNumber());
				
				if (!StringUtils.isEmpty(tripDetails)) {
					RouteDeviation routeDeviation = repository.findByTrip(tripDetails);
					if (StringUtils.isEmpty(routeDeviation)) {
						        routeDeviation = RouteDeviation.builder()
								.createdDate(DateUitls.getCurrentSystemTimestamp())
								.deviationTime(DateUitls.getCurrentSystemTimestamp())
								.location(deviationVO.getLocation())
								.tripDetails(tripDetails)
								.geoLocation(Geocoder.getFormattedAddress(deviationVO.getLocation()))
								.build();
						routeDeviation = repository.save(routeDeviation);
						
						createIndex(tripDetails,routeDeviation);
						
						// Send Route deviation info to Citizen
						String rcNumber = tripDetails.getVehicleDetails().getRcNumber();
						String driverName = tripDetails.getDriverDetails().getDriverName();
						String driverContactNo = tripDetails.getDriverDetails().getContactNumber();
						String geoLocation = routeDeviation.getGeoLocation();
						String message  = String.format(Constants.ROUTE_DEVIATION_MSG, geoLocation, DateUtils.getCurrentTimeInStringFormat(), rcNumber,driverName,driverContactNo);
						smsConfiguration.sendSms(deviationVO.getCitizenMobileNumber(), message);
						
					}
				}
			}
			catch (Exception e)
			{
				log.info("Exception while saving Route Deviation :: " + e.getCause(),e);
			}
		}
		
		private void createIndex(TripDetails tripDetails, RouteDeviation routeDeviation) 
		{
			ESRouteDeviation esRouteDeviation = ESRouteDeviation.builder()
					.citizenMobileNumber(tripDetails.getCitizenDetails().getMobileNumber())
					.createdDate(DateUitls.getCurrentTimeInStringFormat())
					.destination(tripDetails.getDestiLocation())
					.deviationTime(DateUitls.getCurrentTimeInStringFormat())
					.dlNumber(tripDetails.getDriverDetails().getDlNumber())
					.driverContactNumber(tripDetails.getDriverDetails().getContactNumber().toString())
					.driverName(tripDetails.getDriverDetails().getDriverName())
					.geoLocation(routeDeviation.getGeoLocation())
					.id(routeDeviation.getId().toString())
					.location(routeDeviation.getLocation())
					.ownerContactNumber(tripDetails.getVehicleDetails().getOwnerDetails().getOwnerContactNumber().toString())
					.ownerName(tripDetails.getVehicleDetails().getOwnerDetails().getOwnerName())
					.rcNumber(tripDetails.getVehicleDetails().getRcNumber())
					.rfId(tripDetails.getDriverDetails().getRfId())
					.serialNumber(tripDetails.getVehicleDetails().getSerialNumber())
					.source(tripDetails.getSourceLatLang())
					.tripId(tripDetails.getId().toString())
					.triprequestId(tripDetails.getRequestId())
					.build();
			esRouteDeviationRepository.save(esRouteDeviation);
			
		}

		@Override
		public PageReadEvent<RouteDeviationVO> readData(ReadRouteDeviationSetEvent request) 
		{
			RouteDeviationSpecifications specifications = new RouteDeviationSpecifications(request.getDlNumber(),request.getRcNumber(),request.getCitizenMobileNumber(),request.getSerialNumber(),request.getRfId(), request.getSearchValue(), request.getSearchDate(), request.getTripId());
			Page<RouteDeviationDetailsView> dbContent = routeDeviationDetailsViewRepository.findAll(specifications,RouteDeviationSpecifications.constructPageSpecification(request.getPageable().getPageNumber(), request.getPageable().getPageSize()));
			
			List<RouteDeviationVO> content = new ArrayList<>();
			for (RouteDeviationDetailsView record : NepheleValidationUtils.nullSafe(dbContent)) 
			{
				if (!StringUtils.isEmpty(record))
				{
					RouteDeviationVO rec = RouteDeviationVO.builder()
						.citizenMobileNumber(record.getCitizenContactNumber())
						.deviationTime(record.getDeviationTime())
						.dlNumber(record.getDlNumber())
						.driverName(record.getDriverName())
						.location(record.getLocation())
						.geoLocation(record.getGeoLocation())
						.rcNumber(record.getRcNumber())
						.requestId(record.getRequestId())
						.rfId(record.getRfId())
						.serialNumber(record.getSerialNumber())
						.stateId(record.getStateId())
						.districtId(record.getDistrictId())
						.cityId(record.getCityId())
						.sLatLng(record.getSLatLng())
						.sLocation(record.getSLocation())
						.dLatLng(record.getDLatLng())
						.dLocation(record.getDLocation())
						.driverContactNumber(record.getDriverContactNumber())
						.ownerContactNumber(record.getOwnerContactNumber())
						.ownerName(record.getOwnerName())
						.tripId(record.getTripId())
						.build();
					content.add(rec); 
				}
			}
			return new PageReadEvent<>(new PageImpl<>(content, request.getPageable(),dbContent != null ? dbContent.getTotalElements() : 0));
		}

		@Override
		public void sendSms(String source) throws JsonParseException, JsonMappingException, IOException 
		{
			String citizenMobileNumber = source.split("#")[1];
			String location = source.split("#")[0];
			TripDetails tripDetails = tripDetailsRepository.getActiveTripByCitizenNumber(citizenMobileNumber);
			String rcNumber = tripDetails.getVehicleDetails().getRcNumber();
			location = Geocoder.getFormattedAddress(location);
			String message  = String.format(Constants.ROUTE_DEVIATION_MSG, location,DateUtils.getCurrentTimeInStringFormat(), rcNumber,tripDetails.getDriverDetails().getDriverName(),tripDetails.getDriverDetails().getContactNumber());
			smsConfiguration.sendSms(citizenMobileNumber, message);
		}
	}
}
