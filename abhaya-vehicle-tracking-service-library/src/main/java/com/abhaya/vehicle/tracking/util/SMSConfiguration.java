package com.abhaya.vehicle.tracking.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SMSConfiguration 
{

	@Value("${sms.apiKey}")
	private String apiKey;

	@Value("${sms.sender}")
	private String sender;

	public  String sendSms(String numbers,String message) 
	{
		try 
		{
			String apiKeyValue 	   = "apikey=" + apiKey;
			String messageKeyValue = "&message=" + message;
			String senderKeyValue  = "&sender=" + sender;
			String numbersKeyValue = "&numbers=" + numbers;

			HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
			String data = apiKeyValue + senderKeyValue + numbersKeyValue + messageKeyValue ;
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
			conn.getOutputStream().write(data.getBytes("UTF-8"));
			final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			final StringBuffer stringBuffer = new StringBuffer();
			String line;
			while ((line = rd.readLine()) != null)
			{
				stringBuffer.append(line);
			}
			rd.close();
			log.info("SMS Response ::  " +stringBuffer.toString());
			return stringBuffer.toString();
		} 
		catch (Exception e) 
		{
			log.info("Exception Sending SMS " + e);
			return "Error "+ e;
		}
	}
}
