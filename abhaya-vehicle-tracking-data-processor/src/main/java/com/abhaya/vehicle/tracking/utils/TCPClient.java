package com.abhaya.vehicle.tracking.utils;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPClient 
{
	
	public static void main(String[] args) 
	{
        final int BUFFER_SIZE = 1024;
        String ss = "Suman fdg dsd" ;
        System.out.println(ss);
        try (Socket socket = new Socket("111.93.16.246", 45567);
        		
        InputStream fileInputStream = new ByteArrayInputStream(ss.getBytes());
        OutputStream socketOutputStream = socket.getOutputStream();)
        {
            
            long startTime = System.currentTimeMillis();
            byte[] buffer = new byte[BUFFER_SIZE];
            int read = 0 ;
            int readTotal = 0;
            while ((read = fileInputStream.read(buffer)) != -1) 
            {
                socketOutputStream.write(buffer, 0, read);
                readTotal += read;
            }
            long endTime = System.currentTimeMillis();
            System.out.println(readTotal + " bytes written in " + (endTime - startTime) + " ms. " + ss);
            readTotal = 0;
            socketOutputStream.flush();
            socketOutputStream.close();
        } 
        catch (Exception e) 
        {
        	e.printStackTrace();
        	
        }
        finally
        {
        }
    }
}
