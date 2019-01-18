package com.abhaya.vehicle.tracking.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GoogleURLShortening 
{
	private static final String GOOGLE_URL_SHORT_API = "https://firebasedynamiclinks.googleapis.com/v1/shortLinks?key=AIzaSyC79q-pUc8WbTxviL8GCu9agKR4Tl6PDEI";
	private static final String GOOGLE_API_DYNAMIC_LINK = "https://abhaya.page.link/?link=";

	public static String shortenUrl(String longUrl) 
	{
		try 
		{
			String encoded = URLEncoder.encode(longUrl, "UTF-8");
			String longURL = GOOGLE_API_DYNAMIC_LINK + encoded;
			String json = "{\"longDynamicLink\": \"" + longURL + "\"}";
			HttpPost postRequest = new HttpPost(GOOGLE_URL_SHORT_API);
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.setEntity(new StringEntity(json, "UTF-8"));

			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpResponse response = httpClient.execute(postRequest);
			String responseText = EntityUtils.toString(response.getEntity());

			GoogleURLParser googleShortURL = new ObjectMapper().readValue(responseText, GoogleURLParser.class);
			return googleShortURL.getShortLink();
		} 
		catch (MalformedURLException e) 
		{
			return "error";
		} 
		catch (IOException e) 
		{
			return "error";
		}
	}
}