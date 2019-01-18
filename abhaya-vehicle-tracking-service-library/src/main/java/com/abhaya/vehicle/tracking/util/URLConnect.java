package com.abhaya.vehicle.tracking.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import lombok.extern.slf4j.Slf4j;

/**
 * @author suman.t
 */
@Slf4j
public class URLConnect
{

    public static String send(String url)
    {
        InputStream inputStream = null;
        try
        {
            URL encodedUrl = new URL(url.replace(" ", "%20"));
            // LOG.info("Connecting to url : " + encodedUrl);
            URLConnection connection = encodedUrl.openConnection();
            inputStream = connection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer out = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null)
            {
                out.append(line);
            }

            return out.toString();
        }
        catch (Exception e)
        {
            log.info("=====>>URL Connection Exception.<<=====");
            log.info("" +e.getCause(), e);
            return "URL Connection Exception.";
        }
        finally
        {
            try
            {
                if (inputStream != null) inputStream.close();
            }
            catch (Exception e)
            {
            	log.info("" +e.getCause(), e);
            }
        }
    }

    /**
     * @author suman.t
     * @param urlString
     * @return response
     * @throws Exception
     */
    public static String connect(String urlString)
    {
        String response = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try
        {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            //urlConnection.connect();
            urlConnection.setReadTimeout(20 * 1000);
            urlConnection.setConnectTimeout(20 * 1000);
            inputStream = urlConnection.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer out = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null)
            {
                out.append(line);
            }

            response = out.toString();
            in.close();

            return response;
        }
        catch (Exception e)
        {
            log.info("=====>>URL Connection Exception.<<=====");
            log.info("" +e.getCause(), e);
            return null;
        }
        finally
        {
            try
            {
                if (inputStream != null) inputStream.close();
                if (urlConnection != null) urlConnection.disconnect();
            }
            catch (Exception e)
            {
            	 log.info("" +e.getCause(), e);
            }
        }
    }
}
