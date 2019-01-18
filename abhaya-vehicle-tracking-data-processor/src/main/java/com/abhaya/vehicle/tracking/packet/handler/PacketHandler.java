package com.abhaya.vehicle.tracking.packet.handler;

import com.abhaya.vehicle.tracking.packet.DeviceBasePacket;

//The chain of responsibility Design Pattern has a
//group of objects that are able to solve a problem
//between them based on some internal communication.
//If one of the object is unable to solve it, it passes
//the problem to the next object in the chain

//Lets create a PacketHandler interface which has two methods 
public interface PacketHandler 
{
	// It passes the data to next PacketHandler in chain 
	// if one fails to handle the problem
	public void nextHandler(PacketHandler nextHandler);
	
	// It processes the request and if it fails it just passes
	// it to next PacketHandler in the chain.
	public DeviceBasePacket processRequest(RawData request);
}
