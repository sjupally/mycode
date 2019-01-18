package com.abhaya.vehicle.tracking.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * @author suman.tipparapu
 */
public class Geocoder
{
    private static final Log LOG = LogFactory.getLog(Geocoder.class);

    public static class GeocoderStatus
    {
        private static final String OK = "OK";
    }

    public static String geocode(String latlng)
    {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?key=AIzaSyC79q-pUc8WbTxviL8GCu9agKR4Tl6PDEI&sensor=false&latlng=" + latlng;
        String json = URLConnect.connect(url);
        return json;
    }

    public static String getFormattedAddress(String latlng)
    {
        String address = latlng;
        try
        {
            String geocodeJsonData = Geocoder.geocode(latlng);
            if (geocodeJsonData != null && !"".equals(geocodeJsonData))
            {
                JSONObject responsJSON = (JSONObject) JSONValue.parse(geocodeJsonData);
                if (GeocoderStatus.OK.equalsIgnoreCase((String) responsJSON.get("status")))
                {
                    JSONArray results = (JSONArray) responsJSON.get("results");
                    JSONObject addressObj = (JSONObject) results.get(0);
                    address = (String) addressObj.get("formatted_address");
                }
                else
                {
                    //address = (String) responsJSON.get("status");
                }
            }
            else
            {
                //address = "URL Connection Exception";
            }
            
        }
        catch (Exception e)
        {
            LOG.info(e.getCause(), e);
            //address = "Exception Occured While Geocoding";
        }
        return address;
    }
}
