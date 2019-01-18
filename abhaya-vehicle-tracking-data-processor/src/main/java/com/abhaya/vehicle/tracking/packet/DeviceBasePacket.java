package com.abhaya.vehicle.tracking.packet;

import java.io.Serializable;

import com.abhaya.vehicle.tracking.enums.VehicleTrackingPacketType;

import lombok.Data;

@Data
public class DeviceBasePacket  implements Serializable 
{
	private static final long serialVersionUID = 1L;
	private VehicleTrackingPacketType packetType;
}
