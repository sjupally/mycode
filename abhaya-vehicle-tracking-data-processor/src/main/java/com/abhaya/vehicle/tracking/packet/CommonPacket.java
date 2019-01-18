package com.abhaya.vehicle.tracking.packet;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@AllArgsConstructor
@Builder
@Setter
@Getter
@Accessors(chain = true)
@ToString
public class CommonPacket
{
	 private String startOfPacket;
	 private String version;
	 private String packetTime;
	 private String packetDate;
	 private String imeiNumber;
	 private String simNumber;
	 private String imsiNumber;
	 private String simStaticIpAddress;
	 private String signalStrength;
	 private List<DeviceBasePacket> deviceBasePacketsList;
	 private String rawData;
	 private String serialNumber;
	 private String endOfPacket;
	 private String dateTime;
	 private String statusId;
	 private String statusInfo;
	 private String rfId;
}
