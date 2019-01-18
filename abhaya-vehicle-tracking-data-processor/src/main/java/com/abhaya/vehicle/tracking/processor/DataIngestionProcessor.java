package com.abhaya.vehicle.tracking.processor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abhaya.vehicle.tracking.data.model.StatusInfo;
import com.abhaya.vehicle.tracking.events.CreateDriverDutyEvent;
import com.abhaya.vehicle.tracking.events.CreateGPSDataEvent;
import com.abhaya.vehicle.tracking.events.CreateModemDetailsEvent;
import com.abhaya.vehicle.tracking.kafka.verticals.TravelTrackingProducer;
import com.abhaya.vehicle.tracking.packet.CommonPacket;
import com.abhaya.vehicle.tracking.packet.DeviceBasePacket;
import com.abhaya.vehicle.tracking.packet.DriverDetailsPacket;
import com.abhaya.vehicle.tracking.packet.GPSDataPacket;
import com.abhaya.vehicle.tracking.services.DeviceCommunicationService;
import com.abhaya.vehicle.tracking.services.DriverDutyDetailsService;
import com.abhaya.vehicle.tracking.services.GPSDataIngestionService;
import com.abhaya.vehicle.tracking.services.ModemDetailsService;
import com.abhaya.vehicle.tracking.services.RawDataPacketService;
import com.abhaya.vehicle.tracking.services.StatusInfoService;
import com.abhaya.vehicle.tracking.util.Geocoder;
import com.abhaya.vehicle.tracking.vos.DeviceCommunicationVO;
import com.abhaya.vehicle.tracking.vos.DriverRFIDVO;
import com.abhaya.vehicle.tracking.vos.GPSDataVO;
import com.abhaya.vehicle.tracking.vos.ModemDetailsVO;
import com.abhaya.vehicle.tracking.vos.RawDataPacketVO;
import com.abhaya.vehicle.tracking.vos.StatusInfoVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface DataIngestionProcessor 
{
	public void parse(String inputData) throws JsonProcessingException;

	@Service
	public class Impl implements DataIngestionProcessor
	{
		@Autowired private ModemDetailsService modemDetailsService;
		@Autowired private DataIngestionProcessorHelper  processorHelper;
		@Autowired private GPSDataIngestionService gpsDataIngestionService;
		@Autowired private DriverDutyDetailsService driverDutyDetailsService;
		@Autowired private StatusInfoService statusInfoService ;
		@Autowired private TravelTrackingProducer producer ;
		@Autowired private RawDataPacketService rawDataPacketService;
		@Autowired private DeviceCommunicationService deviceCommunicationService;
		
		
		public void parse(String inputData) throws JsonProcessingException 
		{
			CommonPacket cbp = processorHelper.buildPackets(inputData);
			if (cbp != null)
			{
				peristRawData(inputData,cbp);
				persistModemDetail(cbp);
				persistGPSData(cbp);
				persistDriverRFID(cbp);
				persistDeviceCommunication(cbp);
			}
		}
		private void peristRawData(String inputData, CommonPacket cbp) 
		{
			RawDataPacketVO dataPacketVO = RawDataPacketVO.builder()
			   .rawData(inputData)
			   .serialNumber(cbp.getSerialNumber())
			   .packetDate(cbp.getPacketDate())
			   .packetTime(cbp.getPacketTime())
			   .imeiNumber(cbp.getImeiNumber())
			   .build();
			rawDataPacketService.save(dataPacketVO);
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
		private void persistDeviceCommunication(CommonPacket cbp) 
		{
			List<GPSDataPacket> gpsDataPackets =  getGPSDataPacket(cbp);
			GPSDataVO gpsDataVO = null;
			if (gpsDataPackets != null && gpsDataPackets.size() > 0)
			{		
				gpsDataVO = gpsDataPackets.get(0).getGpsDataVO();
					
			}
			String latitude = convertLatitude(gpsDataVO.getLatitude());
			String langitude = convertLangitude(gpsDataVO.getLangitude());
			
			String location = Geocoder.getFormattedAddress(latitude+","+langitude);
			
			StatusInfo statusInfo = statusInfoService.findVehicleStatus(cbp.getStatusInfo().replace(" ", "").trim());
			
			DeviceCommunicationVO deviceCommunicationVO = DeviceCommunicationVO.builder()
					   .serialNumber(cbp.getSerialNumber())
					   .packetDate(cbp.getPacketDate())
					   .packetTime(cbp.getPacketTime())
					   .imeiNumber(cbp.getImeiNumber())
					   .latitude(latitude)
					   .langitude(langitude)
					   .location(location)
					   .batteryStatus(statusInfo.getDeviceBatteryStatus())
					   .ignitionStatus(statusInfo.getIgnitionStatus())
					   .engineStatus(statusInfo.getEngineStatus())
					   .panicButtonStatus(statusInfo.getPanicButtonStatus())
					   .tamperStatus(statusInfo.getDeviceTamperStatus())
					   .build();
			deviceCommunicationService.save(deviceCommunicationVO);
			
		}
		private void persistStatusInfo(CommonPacket cbp,Long trackId) 
		{
			StatusInfoVO statusInfoVO = StatusInfoVO.builder()
				.imeiNumber(cbp.getImeiNumber())
				.serialNumber(cbp.getSerialNumber())
				.packetDate(cbp.getPacketDate())
				.packetTime(cbp.getPacketTime())
				.status(cbp.getStatusInfo())
				.statusId(cbp.getStatusId())
				.rfId(cbp.getRfId())
				.trackId(trackId)
				.build();
			statusInfoService.save(statusInfoVO);
		}
		private void persistDriverRFID(CommonPacket cbp) 
		{
			DriverDetailsPacket driverDetailsPacket =  getDriverDetailsPacket(cbp);
			if (driverDetailsPacket != null)
			{
				DriverRFIDVO  driverRFIDVO = driverDetailsPacket.getDriverRFIDVO();
				driverRFIDVO.setSerialNumber(cbp.getSerialNumber());
				driverRFIDVO.setPacketDate(cbp.getPacketDate());
				driverRFIDVO.setPacketTime(cbp.getPacketTime());
				cbp.setRfId(driverRFIDVO.getDriverRFId());
				CreateDriverDutyEvent event = new CreateDriverDutyEvent();
				event.setDriverRFIDVO(driverRFIDVO);
				driverDutyDetailsService.save(event);
			}
		}
		private void persistModemDetail(CommonPacket cbp) 
		{
			ModemDetailsVO modemDetailsVO = ModemDetailsVO.builder()
				.imeiNumber(cbp.getImeiNumber())
				.imsiNumber(cbp.getImsiNumber())
				.ipAddress(processIPAddress(cbp.getSimStaticIpAddress()))
				.signalStrength(cbp.getSignalStrength())
				.simNumber(cbp.getSimNumber())
				.version(cbp.getVersion())
				.serialNumber(cbp.getSerialNumber())
				.build();
			CreateModemDetailsEvent event = new CreateModemDetailsEvent();
			event.setModemDetailsVO(modemDetailsVO);

			modemDetailsService.save(event);
		}
		private void persistGPSData(CommonPacket cbp) throws JsonProcessingException 
		{
			List<GPSDataPacket> gpsDataPackets =  getGPSDataPacket(cbp);
			if (gpsDataPackets != null && gpsDataPackets.size() > 0)
			{
				for (GPSDataPacket gpsDataPacket : gpsDataPackets)
				{
					GPSDataVO gpsDataVO = gpsDataPacket.getGpsDataVO();
					CreateGPSDataEvent createGPSDataEvent = new CreateGPSDataEvent();
					gpsDataVO.setImeiNumber(cbp.getImeiNumber());
					gpsDataVO.setPacketDate(cbp.getPacketDate());
					gpsDataVO.setPacketTime(cbp.getPacketTime());
					gpsDataVO.setSerialNumber(cbp.getSerialNumber());
					createGPSDataEvent.setGpsDataVO(gpsDataVO);
					Long trackId = gpsDataIngestionService.save(createGPSDataEvent);
					persistStatusInfo(cbp,trackId);
					if (!StringUtils.isEmpty(trackId))
					{
						gpsDataVO.setId(trackId);
						producer.send(new ObjectMapper().writeValueAsString(gpsDataVO));
					}
				}
			}
		}
		private String processIPAddress(String simStaticIpAddress) 
		{
			String ipAddress = simStaticIpAddress;
			if (simStaticIpAddress.contains("\0"))
			{
				ipAddress = simStaticIpAddress.substring(0, simStaticIpAddress.indexOf("\0"));
			}
			return ipAddress;
		}
		private List<GPSDataPacket> getGPSDataPacket(CommonPacket cbp)
		{
			List<GPSDataPacket> list = new ArrayList<>();
			if (cbp != null && cbp.getDeviceBasePacketsList() != null && cbp.getDeviceBasePacketsList().size() > 0)
			{
				for (DeviceBasePacket p :cbp.getDeviceBasePacketsList())
				{
					if (p instanceof GPSDataPacket)
					{
						list.add((GPSDataPacket) p);
					}
				}
				return list;
			}
			return null;
		}
		private DriverDetailsPacket getDriverDetailsPacket(CommonPacket cbp)
		{
			if (cbp != null && cbp.getDeviceBasePacketsList() != null && cbp.getDeviceBasePacketsList().size() > 0)
			{
				for (DeviceBasePacket p :cbp.getDeviceBasePacketsList())
				{
					if (p instanceof DriverDetailsPacket)
					{
						return (DriverDetailsPacket) p;
					}
				}
			}
			return null;
		}
		
	}
}
