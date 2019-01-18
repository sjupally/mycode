package com.abhaya.vehicle.tracking.enums;

public enum GPSDataPacketParams {

	HEADER(1, "Hexadecimal","ASCII"),
	UTC_TIME(10, "Hexadecimal","ASCII"),
	LATITUDE(11, "Hexadecimal","ASCII"),
	LANGITUDE(12, "Hexadecimal","ASCII"),
	HDOP(3, "Hexadecimal", "ASCII"),
	ALTITUDE(6, "Hexadecimal","ASCII"),
	FIX(1, "Hexadecimal","ASCII"),
	COG(3,"Hexadecimal","ASCII"),
	SPKM(3,"Hexadecimal","ASCII"),
	SPKN(3,"Hexadecimal","ASCII"),
	DATE(6,"Hexadecimal","ASCII"),
	
	NSAT(2,"Hexadecimal","ASCII"), 
	SEPARATOR(1,"Hexadecimal","ASCII");

	public Integer length;
	public String fromType;
	public String toType;
	
	GPSDataPacketParams(Integer length, String fromType, String toType) 
	{
		this.length = length;
		this.fromType = fromType;
		this.toType = toType;
	}

	public Integer getLength() {
		return length;
	}

	public String getFromType() {
		return fromType;
	}

	public String getToType() {
		return toType;
	}
}
