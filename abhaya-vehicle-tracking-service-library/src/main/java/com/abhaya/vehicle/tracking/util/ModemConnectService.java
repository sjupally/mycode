package com.abhaya.vehicle.tracking.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;


public interface ModemConnectService 
{

  public String processModemCommand(String query,String ipAddress)  throws Exception;
  
  @Slf4j
  @Service
  public class Impl implements ModemConnectService 
  {
	  
	@Value("${tcp.client.port}")
	private String tcpClientPort;

	String res = "Connection timed out";
	  
	@Override
	public String processModemCommand(String query,String ipAddress) throws Exception 
	{
		res = null;
        Socket socket = getSocketConnection(query,ipAddress);
        if (socket != null)
        {
        	log.info("Connected to IoT Device...");
        	pushCommandToModem(socket,query);
            getResponseFromModem(socket);
            log.info("Reading Response from IoT Device done");
        }
        log.info("Response is :: " + res);
        return res;
	}
	  
	private Socket getSocketConnection(String query,String ipAddress)
	{
		Socket socket = null;
		try 
		{
			log.info("IP Address and Port to Connect IoT Device : "+ ipAddress + ":" + tcpClientPort );
			log.info("Connecting to Modem...");
			socket = new Socket(ipAddress, Integer.valueOf(tcpClientPort));
			socket.setSoTimeout(10 * 1000);
		} 
		catch (Exception e) 
		{
			log.info("Exception in getSocketConnection:: "+e.getCause(),e);
			res = e.getLocalizedMessage();
		}
		return socket;
	}
	
	private void pushCommandToModem(Socket socket,String query) 
	{
		try (InputStream fileInputStream = new ByteArrayInputStream(query.getBytes())) 
		{
			log.info("Pushing command to IoT Device...");
			OutputStream outToServer = socket.getOutputStream();
			final int BUFFER_SIZE = 2048;
			byte[] buffer = new byte[BUFFER_SIZE];
			int read;
			while ((read = fileInputStream.read(buffer)) != -1) 
			{
				outToServer.write(buffer, 0, read);
			}
		} 
		catch (Exception e) 
		{
			log.info("Exception in pushCommandToModem:: "+e.getCause(),e);
			res = e.getLocalizedMessage();
		}
	}
	  
	private void getResponseFromModem(Socket socket) throws Exception 
	{
		try (InputStream is = socket.getInputStream(); OutputStream writeStream = new ByteArrayOutputStream()) 
		{
			log.info("Reading response from IoT Device...");
			byte[] bufferOut = new byte[1024];
			int readVal = 0;
			while (!hasExpired(socket))
			{
				if (is.available() > 0)
				{
					while ( readVal < is.available()) 
					{
						readVal = is.read(bufferOut);
						writeStream.write(bufferOut, 0, readVal);
					}
					break;
				}
			}
			log.info(" Response in Hex : " + CommonUtility.toHex(writeStream.toString().getBytes()));
			res = CommonUtility.toHex(writeStream.toString().getBytes());
		} 
		catch (Exception e) 
		{
			log.info("Exception in getResponseFromModem:: "+e.getCause(),e);
			res =  "Connection timed out";
		}
	}
	private boolean hasExpired(Socket socket) throws Exception 
	{
	    return !socket.isConnected()
	            || !socket.isBound()
	            || socket.isClosed()
	            || socket.isInputShutdown()
	            || socket.isOutputShutdown();
	}
  }
}
