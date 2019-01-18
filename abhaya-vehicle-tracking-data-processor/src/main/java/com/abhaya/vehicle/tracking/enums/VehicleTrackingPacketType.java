package com.abhaya.vehicle.tracking.enums;

import java.util.HashMap;
import java.util.Map;

public enum VehicleTrackingPacketType 
{

	GPS_DATA("0x11",Byte.valueOf((byte)17),70),
	RF_DATA("0x12",Byte.valueOf((byte)18),70);
	
	private final String hexDecimalDataType;
	private final Byte decimalDataType;
	private final Integer length;
	
    private static Map<Byte, VehicleTrackingPacketType> codeToPacketMapping;
    private static Map<Byte, Integer> codeToLengthMapping; 
	
    private VehicleTrackingPacketType(String hexDecimalDataType, Byte decimalDataType,Integer length) 
    {
		this.hexDecimalDataType = hexDecimalDataType;
		this.decimalDataType = decimalDataType;
		this.length = length;
	}

	public String getHexDecimalDataType() 
	{
		return hexDecimalDataType;
	}

	public Byte getDecimalDataType() {
		return decimalDataType;
	}

	Byte value() 
	{
		return this.decimalDataType;
	}
	public static Integer getPacketLengthByCode(Byte code) 
	{
        if (codeToLengthMapping == null) 
        {
            initLengthMapping();
        }
        if (codeToLengthMapping.get(code)!=null)
        {
            return codeToLengthMapping.get(code);
        } 
        else
        {
            return null;
        }
    }
	public static VehicleTrackingPacketType getPacketTypeByCode(Byte code) 
	{
        if (codeToPacketMapping == null) 
        {
            initMapping();
        }
        if (codeToPacketMapping.get(code)!=null)
        {
            return codeToPacketMapping.get(code);
        } else
        {
            return null;
        }
    }
     private static void initMapping() 
     {
    	codeToPacketMapping = new HashMap<Byte, VehicleTrackingPacketType>();
        for (VehicleTrackingPacketType packetType : values()) 
        {
        	codeToPacketMapping.put(packetType.decimalDataType, packetType);
        }
     }
     private static void initLengthMapping() 
     {
    	 codeToLengthMapping = new HashMap<Byte, Integer>();
        for (VehicleTrackingPacketType packetType : values()) 
        {
        	codeToLengthMapping.put(packetType.decimalDataType, packetType.getLength());
        }
     }
	public Integer getLength() {
		return length;
	}

	public static Map<Byte, Integer> getCodeToLengthMapping() {
		return codeToLengthMapping;
	}

	public static void setCodeToLengthMapping(Map<Byte, Integer> codeToLengthMapping) {
		VehicleTrackingPacketType.codeToLengthMapping = codeToLengthMapping;
	}
}
