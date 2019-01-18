package com.abhaya.vehicle.tracking.services;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.data.model.TravelTracking;
import com.abhaya.vehicle.tracking.data.model.TripDetails;
import com.abhaya.vehicle.tracking.data.repos.TravelTrackingRepository;
import com.abhaya.vehicle.tracking.data.repos.TripDetailsRepository;
import com.abhaya.vehicle.tracking.events.CreateGPSDataEvent;
import com.abhaya.vehicle.tracking.model.ESTripTrackingDetails;
import com.abhaya.vehicle.tracking.repository.ESTripTrackingDetailsRepository;
import com.abhaya.vehicle.tracking.util.DateUitls;
import com.abhaya.vehicle.tracking.util.Geocoder;
import com.abhaya.vehicle.tracking.vos.GPSDataVO;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

public interface GPSDataIngestionService 
{
	public Long save(CreateGPSDataEvent createGPSDataEvent);
	public void indexTravelTracking(String payload);

	@Service
	@Slf4j
	public class impl implements GPSDataIngestionService
	{
		@Autowired private TravelTrackingRepository repository;
		@Autowired private TripDetailsRepository tripDetailsRepository;
		@Autowired private ESTripTrackingDetailsRepository esTripTrackingDetailsRepository;

		@Override
		public Long save(CreateGPSDataEvent createGPSDataEvent) 
		{
			try
			{
				GPSDataVO gpsDataVO = createGPSDataEvent.getGpsDataVO();
				TripDetails tripDetails = tripDetailsRepository.getTripBySerialNumber(gpsDataVO.getSerialNumber());
				String latitude = convertLatitude(gpsDataVO.getLatitude());
				String langitude = convertLangitude(gpsDataVO.getLangitude());

				TravelTracking tracking = TravelTracking.builder()
						.altitude(gpsDataVO.getAltitude())
						.cog(gpsDataVO.getCog())
						.createdDate(DateUitls.getCurrentSystemTimestamp())
						.date(DateUitls.convertDateFormat("ddMMyy", gpsDataVO.getDate()))
						.fix(gpsDataVO.getFix())
						.hdop(gpsDataVO.getHdop())
						.imeiNumber(gpsDataVO.getImeiNumber())
						.langitude(langitude)
						.latitude(latitude)
						.nsat(gpsDataVO.getNsat())
						.packetDate(gpsDataVO.getPacketDate())
						.packetTime(gpsDataVO.getPacketTime())
						.spkm(gpsDataVO.getSpkm())
						.spkn(gpsDataVO.getSpkn())
						.time(DateUitls.getIStFromUTCTime(gpsDataVO.getUtcTime()))
						.serialNumber(gpsDataVO.getSerialNumber())
						.tripDetails(tripDetails)
						.location(Geocoder.getFormattedAddress(String.join(",", latitude,langitude)))
						.build();
				tracking = repository.save(tracking);
				return tracking.getId();
			}
			catch (Exception e)
			{
				log.info("Exception while Saving GPS Data :: " + e.getCause(),e);
				return null;
			}
		}
		private String convertLatitude(String latitude)
		{
			DecimalFormat numberFormat = new DecimalFormat("##.000000");
			latitude = latitude.replaceAll("([A-Z a-z])", "");
			return numberFormat.format((Double.parseDouble(latitude.substring(0, 2)) + (Double.parseDouble(latitude.substring(2))/60)));
		}
		private String convertLangitude(String langitude)
		{
			DecimalFormat numberFormat = new DecimalFormat("##.000000");
			langitude = langitude.replaceAll("([A-Z a-z])", "");
			if (langitude.startsWith("0"))
			{
				langitude = langitude.replaceFirst("0", "");
			}
			return numberFormat.format((Double.parseDouble(langitude.substring(0, 2)) + (Double.parseDouble(langitude.substring(2))/60)));
		}
		@Override
		public void indexTravelTracking(String payload) 
		{
			try
			{
				GPSDataVO gpsDataVO = new ObjectMapper().readValue(payload, GPSDataVO.class);
				log.info("Inside  Tracking Index saving  :: " );
				TripDetails tripDetails = tripDetailsRepository.getTripBySerialNumber(gpsDataVO.getSerialNumber());
				String latitude = convertLatitude(gpsDataVO.getLatitude());
				String langitude = convertLangitude(gpsDataVO.getLangitude());

				ESTripTrackingDetails tracking = ESTripTrackingDetails.builder()
						.altitude(gpsDataVO.getAltitude())
						.cog(gpsDataVO.getCog())
						.date(DateUitls.convertDateFormat("ddMMyy", gpsDataVO.getDate()))
						.fix(gpsDataVO.getFix())
						.hdop(gpsDataVO.getHdop())
						.imeiNumber(gpsDataVO.getImeiNumber())
						.langitude(langitude)
						.latitude(latitude)
						.nsat(gpsDataVO.getNsat())
						.packetDate(gpsDataVO.getPacketDate())
						.packetTime(gpsDataVO.getPacketTime())
						.spkm(gpsDataVO.getSpkm())
						.spkn(gpsDataVO.getSpkn())
						.time(DateUitls.getIStFromUTCTime(gpsDataVO.getUtcTime()))
						.serialNumber(gpsDataVO.getSerialNumber())
						.tripId(!StringUtils.isEmpty(tripDetails) ? tripDetails.getId().toString() : null)
						.location(Geocoder.getFormattedAddress(String.join(",", latitude,langitude)))
						.id(gpsDataVO.getId().toString())
						.build();
				esTripTrackingDetailsRepository.save(tracking);
				log.info("************** Tracking Index saving done");
			}
			catch (Exception e)
			{
				log.info("Exception while Saving GPS Data Index :: " + e.getCause(),e);
			}
		}
	}
}
