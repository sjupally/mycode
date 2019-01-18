package com.abhaya.vehicle.tracking.packet.handler;


import com.abhaya.vehicle.tracking.enums.GPSDataPacketParams;
import com.abhaya.vehicle.tracking.enums.VehicleTrackingPacketType;
import com.abhaya.vehicle.tracking.packet.DeviceBasePacket;
import com.abhaya.vehicle.tracking.packet.DriverDetailsPacket;
import com.abhaya.vehicle.tracking.util.CommonUtility;
import com.abhaya.vehicle.tracking.vos.DriverRFIDVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DriverDetailsPacketHandler implements PacketHandler 
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
		DeviceBasePacket basePkt= null;
		if (request.getVehicleTrackingPacketType().name() == VehicleTrackingPacketType.RF_DATA.name()) 
		{
			log.info(" !!!!!!!!!!!!!!!!!!!!!!!!  I am in "+ VehicleTrackingPacketType.RF_DATA.name());

			String driverRFId = CommonUtility.hexToASCII(request.getRawdata());
			log.info(" #########  Driver RFID :" + driverRFId);

			DriverRFIDVO rfidvo = DriverRFIDVO.builder().driverRFId(driverRFId).build();
			return DriverDetailsPacket.builder().driverRFIDVO(rfidvo).build();
		} 
		else 
		{
			basePkt = nextHandler.processRequest(request);
		}
		return basePkt;
	}
	@SuppressWarnings("unused")
	private int getNextCursorPosition(int cursorPos)
	{
		cursorPos = cursorPos + GPSDataPacketParams.SEPARATOR.getLength();
		return cursorPos;
	}
}
