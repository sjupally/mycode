package com.abhaya.vehicle.tracking.packet;

import com.abhaya.vehicle.tracking.vos.GPSDataVO;

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
public class GPSDataPacket extends DeviceBasePacket
{
	private static final long serialVersionUID = 1L;
	private GPSDataVO gpsDataVO;
}
