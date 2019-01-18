package com.abhaya.vehicle.tracking.packet.handler;

import com.abhaya.vehicle.tracking.enums.VehicleTrackingPacketType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
@Accessors(chain = true)
public class RawData
{
	private byte[] rawdata;
	private String packetDate;
	private String packetTime;
	private VehicleTrackingPacketType vehicleTrackingPacketType;
}
