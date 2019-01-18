package com.abhaya.vehicle.tracking.processor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.abhaya.vehicle.tracking.enums.ProtocolParamProperty;
import com.abhaya.vehicle.tracking.enums.VehicleTrackingPacketType;
import com.abhaya.vehicle.tracking.packet.CommonPacket;
import com.abhaya.vehicle.tracking.packet.DeviceBasePacket;
import com.abhaya.vehicle.tracking.packet.handler.CoRPatternClient;
import com.abhaya.vehicle.tracking.packet.handler.RawData;
import com.abhaya.vehicle.tracking.util.CommonUtility;

import lombok.extern.slf4j.Slf4j;

public interface DataIngestionProcessorHelper 
{

	public CommonPacket buildPackets(String input);
	
	@Service
	@Slf4j
	public class impl implements DataIngestionProcessorHelper
	{
		@Override
		public CommonPacket buildPackets(String input) 
		{
			CommonPacket cbp =  null;
			try
			{
				int cursorPos = 0;
				String startOfPacket = CommonUtility.hexToASCII(input.substring(cursorPos, cursorPos=cursorPos+ ProtocolParamProperty.START_OF_PACKET.getNoOfBytes()));
				log.info(" startOfPacket :"+ startOfPacket);
				if(!startOfPacket.startsWith("#GNV"))
				{
					String version = CommonUtility.hexToASCII(input.substring(cursorPos = getNextCursorPosition(cursorPos), cursorPos = cursorPos + ProtocolParamProperty.VERSION.getNoOfBytes()));
					log.info(" version :"+ version);
					
					String deviceSerialNumber = CommonUtility.hexToASCII(input.substring(cursorPos = getNextCursorPosition(cursorPos), cursorPos = cursorPos + ProtocolParamProperty.DEVICEID.getNoOfBytes()));
					log.info(" deviceSerialNumber :"+ deviceSerialNumber);

					String packetTime = CommonUtility.hexToASCII(input.substring(cursorPos = getNextCursorPosition(cursorPos), cursorPos = cursorPos + ProtocolParamProperty.PACKET_TIME.getNoOfBytes()));
					log.info(" packetTime :"+ packetTime);

					String packetDate = CommonUtility.hexToASCII(input.substring(cursorPos = getNextCursorPosition(cursorPos), cursorPos = cursorPos + ProtocolParamProperty.PACKET_DATE.getNoOfBytes()));
					log.info(" packetDate :"+ packetDate);

					String imeiNumber = CommonUtility.hexToASCII(input.substring(cursorPos = getNextCursorPosition(cursorPos), cursorPos = cursorPos + ProtocolParamProperty.IMEI_NUMBER.getNoOfBytes()));
					log.info(" Modem Imei :"+ imeiNumber);

					String simNumberHex = input.substring(cursorPos = getNextCursorPosition(cursorPos), cursorPos = cursorPos + ProtocolParamProperty.SIM_NUMBER.getNoOfBytes());
					log.info(" simNumberHex :"+ simNumberHex);
					String simNumber = null;
					if (!simNumberHex.matches("[0]+"))
					{
						simNumber = CommonUtility.hexToASCII(simNumberHex);
						log.info(" simnumber :"+ simNumber);
					}
					String imsiNumberHex = input.substring(cursorPos = getNextCursorPosition(cursorPos), cursorPos = cursorPos + ProtocolParamProperty.IMSI_NUMBER.getNoOfBytes());
					log.info(" Sim imsiNumberHex :"+ imsiNumberHex);
					String imsiNumber = null;
					if(!imsiNumberHex.matches("[0]+"))
					{
						imsiNumber = CommonUtility.hexToASCII(imsiNumberHex);
						log.info(" imsiNumber :"+ imsiNumber);
					}
					String statisIpHex = input.substring(cursorPos = getNextCursorPosition(cursorPos), cursorPos = cursorPos + ProtocolParamProperty.SIM_STATIC_IPADDRESS.getNoOfBytes());
					log.info(" statisIpHex :"+ statisIpHex);
					String simStaticIp = CommonUtility.hexToASCII(statisIpHex);
					log.info(" simStaticIp :"+ simStaticIp);

					String signalStrength = CommonUtility.hexToASCII(input.substring(cursorPos = getNextCursorPosition(cursorPos), cursorPos = cursorPos + ProtocolParamProperty.SIGNAL_STRENGTH.getNoOfBytes()));
					log.info(" signalStrength :"+ signalStrength);

					byte[] statusIdParam = CommonUtility.hexToBytes(input.substring(cursorPos = getNextCursorPosition(cursorPos), cursorPos = cursorPos + ProtocolParamProperty.STATUS_ID.getNoOfBytes()), false);
					log.info("statusIdParam  : " + CommonUtility.byteArrayToString(statusIdParam));
					String statusId = CommonUtility.hexToDecimalAsUnSinged(statusIdParam).toString();
					log.info("statusId  : " + statusId);

					byte[] statusInfoParam = CommonUtility.hexToBytes(input.substring(cursorPos, cursorPos = cursorPos + ProtocolParamProperty.STATUS_INFO.getNoOfBytes()), false);
					log.info("statusInfoParam  : " + CommonUtility.byteArrayToString(statusInfoParam));
					String statusInfo = CommonUtility.hexToDecimalAsUnSinged(statusInfoParam).toString();
					log.info("statusInfo  : " + statusInfo);
					
					try
					{
						List<DeviceBasePacket> deviceBasePacketsList = new ArrayList<DeviceBasePacket>();
						do {
							
							String deviceIdByte = input.substring(cursorPos,cursorPos=cursorPos+2);
							log.info(" **** deviceIdByte :"+ deviceIdByte);
							byte[] id = CommonUtility.hexToBytes(deviceIdByte, false);
							log.info(" deviceId :"+ id[0]);

							String deviceType = input.substring(cursorPos, cursorPos = cursorPos + 2);
							//log.info(" deviceType :"+ deviceType);
							log.info(" deviceType :"+ CommonUtility.hexToASCII(deviceType)); ;
							log.info(" ###### Cursor Position - "+ cursorPos);
							
							if(deviceIdByte.equalsIgnoreCase("11") || deviceIdByte.equalsIgnoreCase("12"))
							{
								
								VehicleTrackingPacketType trackingPacketType = VehicleTrackingPacketType.getPacketTypeByCode(id[0]);
								log.info(" ^^^^^^^^^^^^^^^^^^^^^^^^^ Device PacketType : " + trackingPacketType.name());

								int deviceDataLen = CommonUtility.hexToDecimal(input.substring(cursorPos, cursorPos = cursorPos + ProtocolParamProperty.DEVICE_DATA_LENGTH.getNoOfBytes()));
								log.info(" Device Data Length :"+ deviceDataLen);
								
								String deviceData = input.substring(cursorPos,cursorPos = cursorPos + (deviceDataLen*2));
								log.info(" **** deviceData:"+ deviceData);

								byte[] rawdata =  CommonUtility.hexToBytes(deviceData,false);
								CoRPatternClient coRPatternClient = new CoRPatternClient();
								RawData rawData = RawData.builder().rawdata(rawdata).packetDate(packetDate).packetTime(packetTime).vehicleTrackingPacketType(trackingPacketType).build();
								DeviceBasePacket basePacket = coRPatternClient.invokePacketHandler(rawData);
								deviceBasePacketsList.add(basePacket);
							}else{
								log.info(" !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  Parsing Done !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
								log.info(" **** CRC cursorPos:"+ cursorPos);
							}
						}while (!input.substring(cursorPos,cursorPos + 2).equalsIgnoreCase("2E"));
						
						cbp = CommonPacket.builder()
							   .startOfPacket(startOfPacket)
							   .version(version)
							   .serialNumber(deviceSerialNumber)
							   .packetTime(packetTime)
							   .packetDate(packetDate)
							   .imeiNumber(imeiNumber)
							   .simNumber(simNumber)
							   .imsiNumber(imsiNumber)
							   .simStaticIpAddress(simStaticIp)
							   .signalStrength(signalStrength)
							   .deviceBasePacketsList(deviceBasePacketsList)
							   .statusId(statusId)
							   .statusInfo(CommonUtility.byteArrayToString(statusInfoParam))
							   .build();
						
					}catch (Exception e)
					{
						log.info("Exception in else condition :: "+e.getCause(),e);
						return null;
					}
				}
				else
				{
					log.info("Other packet"+startOfPacket);
				}
				return cbp;
			}
			catch (Exception e)
			{
				log.info("Exception in build packet :: "+e.getCause(),e);
				return null;
			}
		}
		private int getNextCursorPosition(int cursorPos)
		{
			cursorPos = cursorPos + ProtocolParamProperty.SEPARATOR.getNoOfBytes();
			return cursorPos;
		}
	}
}
