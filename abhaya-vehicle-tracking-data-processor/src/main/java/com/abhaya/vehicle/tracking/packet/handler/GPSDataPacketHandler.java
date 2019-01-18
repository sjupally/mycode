package com.abhaya.vehicle.tracking.packet.handler;

import com.abhaya.vehicle.tracking.enums.GPSDataPacketParams;
import com.abhaya.vehicle.tracking.enums.VehicleTrackingPacketType;
import com.abhaya.vehicle.tracking.packet.DeviceBasePacket;
import com.abhaya.vehicle.tracking.packet.GPSDataPacket;
import com.abhaya.vehicle.tracking.util.CommonUtility;
import com.abhaya.vehicle.tracking.vos.GPSDataVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GPSDataPacketHandler implements PacketHandler 
{
	
	public PacketHandler nextHandler;

	@Override
	public void nextHandler(PacketHandler nextHandler) 
	{
		this.nextHandler = nextHandler;
	}

	@Override
	public DeviceBasePacket processRequest(RawData request) 
	{
		DeviceBasePacket basePkt = null;
		if (request.getVehicleTrackingPacketType().name() == VehicleTrackingPacketType.GPS_DATA.name()) 
		{
			log.info(" !!!!!!!!!!!!!!!!!!!!!!!!  I am in "+ VehicleTrackingPacketType.GPS_DATA.name());
			int cursorPos = 0;

			String delims = ",";
			String gpsData = CommonUtility.hexToASCII(request.getRawdata());
			log.info(" #########  GPS Data :" + gpsData);
			String[] tokens = gpsData.substring(cursorPos).split(delims);

			String utcTime = tokens[0];
			log.info(" utcTime :" + utcTime);
			cursorPos = cursorPos + utcTime.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);

			String latitude = tokens[1];
			log.info(" latitude :" + latitude);
			cursorPos = cursorPos + latitude.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);
			
			String langitude = tokens[2];
			log.info(" longitude :" + langitude);
			cursorPos = cursorPos + langitude.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);

			String hdop = tokens[3];
			log.info(" hdop :" + hdop);
			cursorPos = cursorPos + hdop.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);

			String altitude = tokens[4];
			log.info(" altitude :" + altitude);
			cursorPos = cursorPos + altitude.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);

			String fix = tokens[5];
			log.info(" fix :" + fix);
			cursorPos = cursorPos + fix.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);

			String cog = tokens[6];
			log.info(" cog :" + cog);
			cursorPos = cursorPos + cog.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);

			String spkm = tokens[7];
			log.info(" spkm :" + spkm);
			cursorPos = cursorPos + spkm.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);

			String spkn = tokens[8];
			log.info(" spkn :" + spkn);
			cursorPos = cursorPos + spkn.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);

			String date = tokens[9];
			log.info(" date :" + date);
			cursorPos = cursorPos + date.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);

			String nsat = tokens[10];
			log.info(" nsat :" + nsat);
			cursorPos = cursorPos + nsat.length();
			cursorPos = getNextCursorPosition(cursorPos);
			log.info("Header cursorPos : " + cursorPos);

			GPSDataVO gpsDataVO = GPSDataVO.builder()
					.utcTime(utcTime)
					.langitude(langitude)
					.latitude(latitude)
					.hdop(hdop)
					.altitude(altitude)
					.cog(cog)
					.date(date)
					.fix(fix)
					.nsat(nsat)
					.spkm(spkm)
					.spkn(spkn)
					.build();
			basePkt =  GPSDataPacket.builder().gpsDataVO(gpsDataVO).build();
		} 
		else 
		{
			basePkt = nextHandler.processRequest(request);
		}
		return basePkt;
	}
	private int getNextCursorPosition (int cursorPos)
	{
		cursorPos = cursorPos + GPSDataPacketParams.SEPARATOR.getLength();
		return cursorPos;
	}
}
