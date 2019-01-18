package com.abhaya.vehicle.tracking.enums;

public enum ProtocolParamProperty 
{
	  START_OF_PACKET(4, "ASCII"),
	  VERSION(3, "ASCII"),
	  DEVICEID(10, "ASCII"),
	  SEPARATOR(1,"ASCII"),
	  PACKET_TIME(8,"ASCII"),
	  PACKET_DATE(10,"ASCII"),
	  IMEI_NUMBER(15,"ASCII"),
	  SIM_NUMBER(20,"ASCII"),
	  IMSI_NUMBER(15,"ASCII"),
	  SIM_STATIC_IPADDRESS(15,"ASCII"),
	  SIGNAL_STRENGTH(5,"ASCII"),
	  STATUS_ID(1,"ASCII"),
	  STATUS_INFO(4,"ASCII"),
	  DEVICE_DATA_LENGTH(4,"ASCII");
	
	
	  public Integer noOfBytes;
	  public String dataType;

	  ProtocolParamProperty(Integer noOfBytes, String dataType)
	  {
	    this.noOfBytes = noOfBytes;
	    this.dataType = dataType;
	  }
	  
	  public Integer getNoOfBytes() {
			return noOfBytes*2;
	  }
		
	  public String getDataType() {
			return dataType;
	  }

	  @Override
	  public String toString() {
			return this.getNoOfBytes() + this.getDataType();
	  }
}
