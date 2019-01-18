package com.abhaya.vehicle.tracking.constants;

public class Constants 
{
	
	public static class ResponseMessages
	{
		public static final Long CODE_400 = 400L;
		public static final Long CODE_404 = 404L;
		public static final Long CODE_200 = 200L;
		public static final Long CODE_500 = 500L;
		
		public static final String MESSAGE_400 = "Already Existed";
		public static final String MESSAGE_200 = "Success";
		public static final String MESSAGE_500 = "Internal Server Error";
		public static final String MESSAGE_404 = "Not Existed";
	}
	public static final String ROUTE_DEVIATION_MSG = "Your route is deviated at %s and %s, riding in vehicle - %s and driven by %s - %s.";
	public static final String DISTRESS_MSG = "I have raised a distress call since my route is deviated, riding in vehicle - %s and driven by %s - %s. Track the current location here: %s";
	public static final String DRIVER_ON_BOARD_MSG = "Driver is not on boarded.";
	
	public static final String SHARE_LOCATION_MSG = "Riding in Abhaya authorized vehicle - %s and driven by %s - %s. Track the current location here: %s";
}
