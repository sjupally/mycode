package com.abhaya.vehicle.tracking.packet.handler;

import com.abhaya.vehicle.tracking.packet.DeviceBasePacket;

public class CoRPatternClient 
{
	public DeviceBasePacket invokePacketHandler(RawData request) 
	{
		PacketHandler packetHandler = createChainOfRules();
		return packetHandler.processRequest(request);
	}

	private static PacketHandler createChainOfRules()
	{
		return PacketChainFactory.getInstance().createRules(
				new GPSDataPacketHandler(),
				new DriverDetailsPacketHandler());
	}
}
