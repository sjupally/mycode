package com.abhaya.vehicle.tracking.util;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;



public class DateUitls 
{
	private static final Log LOG = LogFactory.getLog(DateUitls.class);
	public final static String dd_MM_yy_HHmmss =  "dd-MM-yy HH:mm:ss";
	public final static String dd_MM_yyyy_HHmmss =  "dd-MM-yyyy HH:mm:ss";
	public final static String dd_MM_yyyy =  "dd-MM-yyyy";
	public final static String yyyy_MM_dd_HHmmss  = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat YYYY_MM_DD_TIME = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SS");
    public static final SimpleDateFormat DD_MM_YYYY = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat DD_MM_YYYY_SLASH = new SimpleDateFormat("dd/MM/yyyy");
    public static final SimpleDateFormat YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat HH_MM_SS_SS = new SimpleDateFormat("hh:mm:ss.SS");
    public static final SimpleDateFormat MM_DD_YYYY_TIME = new SimpleDateFormat("yyMMddHHmmss");
    public static final SimpleDateFormat MM_DD_YYYY = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat MM_DD_YYYY_TIME_SHORT = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    public static final SimpleDateFormat DD_MM_YYYY_TIME_SHORT = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    public static final SimpleDateFormat FULL_DATE_WITH_DAY = new SimpleDateFormat("EEE MMM dd, yyyy hh:mm a");
    public static final SimpleDateFormat MM_DD_YYYY_FULL_WITH_DAY = new SimpleDateFormat("MM/dd/yyyy EEE hh:mm a");
    public static final SimpleDateFormat TIME_FORMAT_ZONE = new SimpleDateFormat("K:mm a, z");
    public static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("h:mm a");
    public static final SimpleDateFormat DD_MM_YYYY_hh_MM = new SimpleDateFormat("dd/MM/yyyy hh:mm");
    public static final SimpleDateFormat DEFAULT_TIMESTAMP_FORMAT = MM_DD_YYYY_TIME;
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT = DD_MM_YYYY_SLASH;
    public static final SimpleDateFormat DEFAULT_DATETIME_FORMAT = DD_MM_YYYY_TIME_SHORT;
    public static final SimpleDateFormat YYYY_MM_DD_HH_MM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public static String getStringFromTimestamp(Timestamp timestamp,String format)
	{
		if (timestamp != null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(timestamp);
		}
		return null;
	}
	
	public static String getStringFromSqlDate(Date sqlDate,String format)
	{
		if (sqlDate != null)
		{
			if (format == null)
			{
				format = dd_MM_yyyy;
			}
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			return dateFormat.format(sqlDate);
		}
		return null;
	}
	
	public static Timestamp getSqlTimeStamp()
	{
		return new Timestamp(System.currentTimeMillis());
	}
	public static Date getSqlCurrentDate()
	{
		return new Date(new Timestamp(System.currentTimeMillis()).getTime());
	}
	
	public static Timestamp getSqlTimestampFromString(String timeStamp,String format) throws ParseException
	{
		if (timeStamp != null)
		{
			if (format == null)
			{
				format = dd_MM_yy_HHmmss;
			}
		    SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		    java.util.Date parsedDate = dateFormat.parse(timeStamp);
		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		    return timestamp;
		}
		return null;
	}
	public static Timestamp getSqlTimestampFromString(String timeStamp) throws ParseException
	{
		if (timeStamp != null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(dd_MM_yy_HHmmss);
		    java.util.Date parsedDate = dateFormat.parse(timeStamp);
		    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
		    return timestamp;
		}
		return null;
	}

	public static String getStringFromTimestamp(Timestamp dateTime) 
	{
		if (dateTime != null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat(dd_MM_yyyy_HHmmss);
			return dateFormat.format(dateTime);
		}
		return null;
	}
	

	   

	    /**
	     * Used to format the date from sal format to dd/mm/yyyy format
	     * 
	     * @param date
	     * @return string in dd/mm/yyyy format
	     */
	    public static String formatDate(String date)
	    {
	        try
	        {
	            if (date != null)
	            {
	                SimpleDateFormat dbFormat = new SimpleDateFormat("yyyy-MM-dd");

	                SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	                return df.format(dbFormat.parse(date));
	            }
	        }
	        catch (ParseException e)
	        {
	            // e.printStackTrace();
	            LOG.error("Date Parsing Exception : " + e.getMessage());
	        }

	        return date;
	    }

	    public static String formatDate(String  date, String dateFormat)
	    {
	        if (date != null)
	        {
	            if (dateFormat == null)
	            {
	                dateFormat = "dd/MM/yyyy";
	            }
	            DateFormat formatter = new SimpleDateFormat(dateFormat);

	            return formatter.format(date);
	        }
	        return null;
	    }

	    /**
	     * used to convert String date to sql date
	     * 
	     * @param date
	     * @return
	     */
	    public static java.sql.Date getSqlDateFromString(String date)
	    {
	        try
	        {
	            if (date != null)
	            {
	                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	                java.util.Date dat = formatter.parse(date);
	                Calendar c = formatter.getCalendar();
	                c.setTime(dat);
	                long l = c.getTimeInMillis();
	                return new java.sql.Date(l);
	            }
	            return null;
	        }
	        catch (ParseException e)
	        {
	            LOG.error(e.getMessage(), e);
	            return null;
	        }
	        catch (Exception e)
	        {
	            LOG.error(e.getMessage(), e);
	            return null;
	        }
	    }

	    /**
	     * used to convert String date to sql date
	     * 
	     * @param date
	     * @return
	     */
	    public static java.sql.Date getSqlDateFromString(String date, String dateFormat)
	    {
	        try
	        {
	            if (date != null)
	            {
	                if (dateFormat == null)
	                {
	                    dateFormat = "dd/MM/yyyy";
	                }
	                DateFormat formatter = new SimpleDateFormat(dateFormat);
	                java.util.Date dat = formatter.parse(date);
	                Calendar c = formatter.getCalendar();
	                c.setTime(dat);
	                long l = c.getTimeInMillis();
	                return new java.sql.Date(l);
	            }
	            return null;
	        }
	        catch (ParseException e)
	        {
	            LOG.error(e.getMessage());
	            return null;
	        }
	    }

	    /**
	     * To get previous day's date for the entered date string
	     * 
	     * @param dateStr
	     * @return sql date
	     */
	    public static java.sql.Date getPreviousDayDate(String dateStr)
	    {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date yesterday = null;
	        try
	        {
	            GregorianCalendar gc = new GregorianCalendar();
	            java.util.Date d = sdf.parse(dateStr);
	            gc.setTime(d);
	            int dayBefore = gc.get(Calendar.DAY_OF_YEAR);
	            gc.roll(Calendar.DAY_OF_YEAR, -1);
	            int dayAfter = gc.get(Calendar.DAY_OF_YEAR);
	            if (dayAfter > dayBefore)
	            {
	                gc.roll(Calendar.YEAR, -1);
	            }
	            gc.get(Calendar.DATE);
	            yesterday = gc.getTime();
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        return getSqlDateFromString(sdf.format(yesterday));
	    }

	    public static String getAfterDayDate(String dateStr)
	    {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        String tomarrow = null;
	        try
	        {
	            GregorianCalendar gc = new GregorianCalendar();
	            java.util.Date d = sdf.parse(dateStr);
	            gc.setTime(d);
	            gc.add(Calendar.DATE, 1);
	            tomarrow = gc.get(Calendar.YEAR) + "-" + (gc.get(Calendar.MONTH) + 1) + "-" + gc.get(Calendar.DATE);
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        return tomarrow;
	    }

	    /**
	     * method to getCurrent Time Stamp as String
	     * 
	     * @return
	     */

	    public static String getCurrentTimestamp()
	    {
	    	java.util.Date date = new java.util.Date();
	        return DD_MM_YYYY_hh_MM.format(date);
	    }

	    /**
	     * method to get timestamp according to dateformat given
	     * 
	     * @param format
	     * @return
	     */

	    public static String getCurrentTimestamp(SimpleDateFormat format)
	    {
	        java.util.Date date = new java.util.Date();
	        return DEFAULT_TIMESTAMP_FORMAT.format(date);
	    }

	    /**
	     * uses the default DEFAULT_DATE_FORMAT or DEFAULT_DATETIME_FORMAT based on input length
	     * 
	     * @param dateString
	     * @return java.util.Date
	     * @see DEFAULT_DATE_FORMAT
	     * @see DEFAULT_DATETIME_FORMAT
	     */

	    public static java.util.Date getDate(String dateString)
	    {
	        try
	        {
	            if (dateString != null && dateString.length() < 13)
	            {
	                return YYYY_MM_DD.parse(dateString); // return date only
	            }
	            else
	            {
	                //
	                return DEFAULT_DATE_FORMAT.parse(dateString); // return
	                                                              // timestamp
	                                                              // column
	            } // end if
	        }
	        catch (ParseException e)
	        {
	            LOG.error(e.getMessage());
	            return null;
	        }
	    } // end

	    /**
	     * @param format
	     * @return
	     */
	    public static java.util.Date getCurrentDate(String format)
	    {
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
	        try
	        {
	            return simpleDateFormat.parse(getCurrentDateAsString(format));
	        }
	        catch (ParseException e)
	        {
	            LOG.error(e.getStackTrace());
	        }
	        return null;
	    }

	    /**
	     * @param format
	     * @return
	     */
	    public static String getCurrentDateAsString(String format)
	    {
	    	java.util.Date date = new java.util.Date();
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
	        return simpleDateFormat.format(date);
	    }

	    /**
	     * uses the default DEFAULT_DATE_FORMAT or DEFAULT_DATETIME_FORMAT based on input length
	     * 
	     * @param dateString
	     * @return java.util.Date
	     * @see DEFAULT_DATE_FORMAT
	     * @see DEFAULT_DATETIME_FORMAT
	     */

	    public static java.util.Date  getDate(String dateString, String format)
	    {
	        try
	        {
	            if (format == null)
	            {
	                format = "dd/MM/yyyy";
	            }
	            return new SimpleDateFormat(format).parse(dateString); // return
	                                                                   // date only
	        }
	        catch (ParseException e)
	        {
	            e.printStackTrace();
	            return null;
	        }
	    } // end

	    /**
	     * to get java.sql.Date instance of current System date
	     * 
	     * @return
	     */
	    public static Date getCurrentSystemDate()
	    {
	        return new Date(Calendar.getInstance().getTime().getTime());
	    }

	    /**
	     * to get java.sql.Time from current System time
	     * 
	     * @return
	     */
	    public static String getCurrentSystemTime()
	    {
	        return new java.sql.Time(Calendar.getInstance().getTime().getTime()).toString();
	    }

	    /**
	     * to get java.sql.Timestamp from current System time
	     * 
	     * @return
	     */
	    public static java.sql.Timestamp getCurrentSystemTimestamp()
	    {
	        return new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());
	    }

	    /**
	     * @param date
	     * @return
	     */
	    public static java.sql.Date getSqlDateFromUtilDate(java.util.Date date)
	    {
	        return new java.sql.Date(date.getTime());
	    }

	    /**
	     * @return
	     */
	    public static String getCurrentTimeInStringFormat()
	    {
	    	java.util.Date date = new java.util.Date();
	        SimpleDateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	        return ft.format(date);
	    }

	    public static String getCurrentDateInStringFormat()
	    {
	    	java.util.Date date = new java.util.Date();
	        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
	        return ft.format(date);
	    }

	    public static String getCurrentTimeInStringFormatWithMilliSec()
	    {
	    	LOG.info("Max Day: " );
	    	java.util.Date d = new java.util.Date();
	        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        return ft.format(d);
	    }

	    /**
	     * @param monthValue
	     * @param yearValue
	     * @return
	     */
	    public static int getMaxDateInMonth(String monthValue, String yearValue)
	    {
	        int maxDay = 0;
	        try
	        {
	            int month = Integer.parseInt(monthValue);
	            int year = Integer.parseInt(yearValue);
	            GregorianCalendar cal = new GregorianCalendar(year, (month - 1), 1); // Get
	                                                                                 // the
	                                                                                 // number
	                                                                                 // of
	                                                                                 // days
	                                                                                 // in
	                                                                                 // that
	                                                                                 // month
	            maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	            LOG.info("Max Day: " + maxDay);
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            return 0;
	        }
	        return maxDay;
	    }

	    /**
	     * @param date1
	     * @param date2
	     * @return
	     */
	    public static String compareDates(java.sql.Date date1, java.sql.Date date2)
	    {
	        Calendar calendar1 = Calendar.getInstance();
	        calendar1.setTime(date1);
	        Calendar calendar2 = Calendar.getInstance();
	        calendar2.setTime(date2);
	        String str = "";
	        long timeinmillis1 = calendar1.getTimeInMillis();
	        long timeinmillis2 = calendar2.getTimeInMillis();
	        if (timeinmillis1 > timeinmillis2)
	        {
	            str = "DATE1";
	        }
	        else
	        {
	            str = "DATE2";
	        }

	        return str;
	    }

	    /**
	     * @param dateStr
	     * @param format
	     * @return
	     */
	    public static Time getSqlTimeStamp(String dateStr, String format)
	    {
	    	java.util.Date date = getDate(dateStr, format);
	        return new Time(date.getTime());
	    }

	    public static long getDateTimeInMilliSeconds(String dateStr, String timeStr)
	    {
	        // Make a String that has a date in it, with MEDIUM date format
	        // and SHORT time format.
	        String dateString = dateStr + " " + timeStr;

	        // Get the default MEDIUM/SHORT DateFormat
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	        java.util.Date date = null;
	        // Parse the date
	        try
	        {
	            date = format.parse(dateString);
	        }
	        catch (ParseException pe)
	        {
	            System.out.println("ERROR: could not parse date in string \"" + dateString + "\"");
	        }
	        return date.getTime();
	    }

	    public static int daysBetween(java.sql.Date d1, java.sql.Date d2)
	    {
	        return ((int) ((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24))) + 1;
	    }
	    
	    public static int getDaysDifferenceBetweenDates(java.sql.Date fromDate, java.sql.Date toDate)
	    {
	        return ((int) ((toDate.getTime() - fromDate.getTime()) / (1000 * 60 * 60 * 24)));
	    }

	    /**
	     * to get java.sql.Time from String time
	     * 
	     * @return
	     */
	    @SuppressWarnings("deprecation")
	    public static java.sql.Time getSqlTimeFromString(String timeStr)
	    {
	        try
	        {
	            if (timeStr != null)
	            {
	                String[] ampmSplitArr = timeStr.split(" ");
	                String time = ampmSplitArr[0];
	                String[] actualTime = time.split(":");
	                if (timeStr.contains("AM"))
	                {
	                    if (Integer.parseInt(actualTime[0]) == 12)
	                    {
	                        return new java.sql.Time(Integer.parseInt(actualTime[0]) + 12, Integer.parseInt(actualTime[1]), Integer.parseInt("00"));
	                    }
	                    else
	                    {
	                        return new java.sql.Time(Integer.parseInt(actualTime[0]), Integer.parseInt(actualTime[1]), Integer.parseInt("00"));
	                    }
	                }
	                else
	                {
	                    if (Integer.parseInt(actualTime[0]) == 12)
	                    {
	                        return new java.sql.Time(Integer.parseInt(actualTime[0]), Integer.parseInt(actualTime[1]), Integer.parseInt("00"));
	                    }
	                    else
	                    {
	                        return new java.sql.Time(Integer.parseInt(actualTime[0]) - 12, Integer.parseInt(actualTime[1]), Integer.parseInt("00"));
	                    }
	                }
	            }
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            return null;
	        }
	        return null;
	    }

	    public static String formatTime(String timeStr)
	    {
	        String[] timeArr = timeStr.split(":");
	        String time = "";
	        int hours = Integer.parseInt(timeArr[0]);
	        if (hours < 12 && hours != 0)
	        {
	            time = hours + ":" + timeArr[1] + " AM";
	        }
	        else if (hours == 12)
	        {
	            time = hours + ":" + timeArr[1] + " PM";
	        }
	        else if (hours == 0)
	        {
	            time = 12 + ":" + timeArr[1] + " AM";
	        }
	        else
	        {
	            hours = hours - 12;
	            time = hours + ":" + timeArr[1] + " PM";
	        }
	        return time;
	    }

	    public static double timeDifference(long latertime, long earliertime)
	    {
	        double difference = latertime - earliertime;
	        double noOfHours = (difference) / (1000 * 60 * 60);
	        return noOfHours;
	    }

	    public static long timeDifferenceInHours(long latertime, long earliertime)
	    {
	        long noOfHours = (latertime - earliertime) / (1000 * 60 * 60);
	        return noOfHours;
	    }

	    public static long timeDifferenceInMinutes(long laterTime, long earliyerTime)
	    {
	        long noOfMinuts = (long) Math.floor((laterTime - earliyerTime) / 1000 / 60);
	        return noOfMinuts;
	    }

	    public static long ellapsedMinutes(long laterTime, long earliyerTime)
	    {
	        int noOfMinuts = (int) (Math.floor((laterTime - earliyerTime) / (1000 * 60)) % 60);
	        return noOfMinuts;
	    }

	    @SuppressWarnings("unused")
	    public static long dateDifference(long latertime, long earliertime)
	    {
	        double difference = latertime - earliertime;

	        double daysDifference = Math.floor(difference / 1000 / 60 / 60 / 24);
	        difference -= daysDifference * 1000 * 60 * 60 * 24;

	        double hoursDifference = Math.floor(difference / 1000 / 60 / 60);
	        difference -= hoursDifference * 1000 * 60 * 60;

	        double minutesDifference = Math.floor(difference / 1000 / 60);
	        difference -= minutesDifference * 1000 * 60;

	        double secondsDifference = Math.floor(difference / 1000);

	        return (long) daysDifference;
	    }

	    public static String getTime(String time)
	    {
	        String subTime = time.substring(0, time.length() - 3);
	        if (time.contains("PM"))
	        {
	            int hours = Integer.parseInt(subTime.substring(0, subTime.indexOf(':')));
	            subTime = subTime.replaceFirst("" + hours, "" + (hours + 12));
	        }
	        return subTime;
	    }

	    public static synchronized String getUniqueId()
	    {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmssS");
	        return dateFormat.format(new java.util.Date());
	    }

	    public static java.sql.Date addDays(Date d, int days)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DAY_OF_MONTH, (int) days);
	        java.util.Date  newDate = cal.getTime();
	        return new java.sql.Date(newDate.getTime());
	    }

	    /**
	     * used to convert String date to TimeStamp
	     * 
	     * @param date
	     * @return
	     */
	    public static Timestamp getTimeStampDateFromString(String date)
	    {
	        try
	        {
	            if (date != null)
	            {
	                DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	                java.util.Date dat = formatter.parse(date);
	                Calendar c = formatter.getCalendar();
	                c.setTime(dat);
	                long l = c.getTimeInMillis();
	                return new Timestamp(new java.sql.Date(l).getTime());
	            }
	            return null;
	        }
	        catch (ParseException e)
	        {
	            LOG.info(e.getCause(), e);
	            return null;
	        }
	    }

	    public static Timestamp getTimeStampFromString(String date, String format)
	    {
	        try
	        {
	            if (date != null)
	            {
	                DateFormat formatter = new SimpleDateFormat(format);
	                java.util.Date dat = formatter.parse(date);
	                Calendar c = formatter.getCalendar();
	                c.setTime(dat);
	                long l = c.getTimeInMillis();
	                return new Timestamp(new java.sql.Date(l).getTime());
	            }
	            return null;
	        }
	        catch (ParseException e)
	        {
	            LOG.info(e.getCause(), e);
	            return null;
	        }
	    }

	    public static String convertTimeIn24hourFormat(String date)
	    {
	        try
	        {
	            if (date != null)
	            {
	                SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
	                SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");
	                java.util.Date   formatedDate = parseFormat.parse(date);
	                System.out.println(parseFormat.format(formatedDate) + " = " + displayFormat.format(formatedDate));
	                return displayFormat.format(formatedDate);
	            }
	            return null;
	        }
	        catch (ParseException e)
	        {
	            return null;
	        }
	    }

	    public static String convertDateFormat(java.sql.Date timestamp,String format)
	    {
	        try
	        {
	        	if (timestamp != null)
	        	{
	        		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(format);
	    	        return  DATE_FORMAT.format(timestamp);
	        	}
	        	
	        }
	        catch (Exception e)
	        {
	        }
	        return null;
	    }
	    public static String convertTimestampFormat(Timestamp timestamp,String format)
	    {
	        try
	        {
	        	java.sql.Date date = new java.sql.Date(timestamp.getTime());
	        	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(format);
		        return  DATE_FORMAT.format(date);
	        }
	        catch (Exception e)
	        {
	            return null;
	        }
	    }

	    public static Timestamp addDays2CurrentDate(long noOfDays)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.add(Calendar.DAY_OF_MONTH, (int) noOfDays);
	        java.util.Date   newDate = cal.getTime();
	        return new Timestamp(newDate.getTime());
	    }

	    public static long getDaysDifference(String date)
	    {
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String dateStop = simpleDateFormat.format(new java.util.Date());

	        // HH converts hour in 24 hours format (0-23), day calculation
	        java.util.Date   d1 = null;
	        java.util.Date   d2 = null;

	        try
	        {
	            d1 = simpleDateFormat.parse(date);
	            d2 = simpleDateFormat.parse(dateStop);

	            // in milliseconds
	            long diff = d2.getTime() - d1.getTime();

	            // long diffSeconds = diff / 1000 % 60;
	            // long diffMinutes = diff / (60 * 1000) % 60;
	            // long diffHours = diff / (60 * 60 * 1000) % 24;
	            long diffDays = diff / (24 * 60 * 60 * 1000);

	            return diffDays;
	        }
	        catch (ParseException e)
	        {
	            return 0L;
	        }
	    }

	    public static java.sql.Date addDays2GivenDate(String date, int days) throws ParseException
	    {
	        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date dat = formatter.parse(date);
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(dat);
	        cal.add(Calendar.DAY_OF_MONTH, (int) days);
	        java.util.Date  newDate = cal.getTime();
	        return new java.sql.Date(newDate.getTime());
	    }

	    public static java.sql.Date addDaysToGivenDate(Date givenDate, int days)
	    {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(givenDate);
	        calendar.add(Calendar.DAY_OF_MONTH, days);
	        java.util.Date  newDate = calendar.getTime();
	        return new java.sql.Date(newDate.getTime());
	    }

	    public static java.sql.Date getCurrentSqlDate()
	    {
	    	java.util.Date  d = new  java.util.Date();
	        return new java.sql.Date(d.getTime());
	    }

	    public static boolean validateDateFormat(String date)
	    {
	        if (date.matches("([0-9]{2})-([0-9]{2})-([0-9]{4})")) return true;
	        else return false;
	    }

	    /*public static String format(Timestamp timestamp, String format)
	    {
	        try
	        {
	            if (format == null)
	            {
	                format = "yyyyMMdd-HHmm";
	            }
	            SimpleDateFormat sdf = YYYY_MM_DD_TIME;
	            java.util.Date  utilDate = sdf.parse(timestamp + "");
	            return DateUitls.formatDate(utilDate, format);
	        }
	        catch (Exception e)
	        {
	            LOG.error(e.getMessage(), e);
	        }
	        return null;
	    }*/

	    public static String formatSqlDate(java.sql.Date date, String format)
	    {
	        SimpleDateFormat simpDate = new SimpleDateFormat(format);
	        return simpDate.format(date);
	    }

	    public static String formatTo12Hour(Timestamp timestamp)
	    {
	        try
	        {
	            SimpleDateFormat sdf = new SimpleDateFormat("h:mma");
	            return sdf.format(timestamp);
	        }
	        catch (Exception e)
	        {
	            LOG.error(e.getMessage(), e);
	        }
	        return null;
	    }

	    public static String convertStringToDate(Date indate, String format)
	    {
	        String dateString = null;
	        SimpleDateFormat sdfr = new SimpleDateFormat(format);
	        /*
	         * you can also use DateFormat reference instead of SimpleDateFormat like this: DateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
	         */
	        try
	        {
	            dateString = sdfr.format(indate);
	        }
	        catch (Exception ex)
	        {
	            System.out.println(ex);
	        }
	        return dateString;
	    }

	    /**
	     * @author rabindranath.s
	     * @param date
	     * @return {int} Day Of Month
	     */
	    public static int getDayOfMonth(Date date)
	    {
	        try
	        {
	            Calendar calendar = Calendar.getInstance();
	            calendar.setTime(date);
	            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
	            return calendar.get(Calendar.DAY_OF_MONTH);
	        }
	        catch (Exception e)
	        {
	            LOG.info(e.getCause(), e);
	            return 0;
	        }
	    }

	    /**
	     * @author rabindranath.s
	     * @param value
	     * @return @return {String} Day Of Month Suffix
	     */
	    public static String getOrdinalSuffix(int value)
	    {
	        int hunRem = value % 100;
	        int tenRem = value % 10;

	        if (hunRem - tenRem == 10)
	        {
	            return "th";
	        }
	        switch (tenRem)
	        {
	            case 1:
	                return "st";
	            case 2:
	                return "nd";
	            case 3:
	                return "rd";
	            default:
	                return "th";
	        }
	    }
	    public static java.sql.Date getDayBeforePreviousDay(String dataDate,int increment)
	    {
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	        Calendar cal = Calendar.getInstance();
	        try
	        {
	        	cal.setTime(sdf.parse(dataDate));
	            cal.add(Calendar.DATE, -increment);    
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        return getSqlDateFromString(sdf.format(cal.getTime()),"yyyy-MM-dd");
	    }
	    public static java.sql.Date getPreviousSunday()
	    {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        Calendar cal = Calendar.getInstance();
	        try
	        {
	    		cal.add( Calendar.DAY_OF_WEEK, -(cal.get(Calendar.DAY_OF_WEEK)-1)); 
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        return getSqlDateFromString(sdf.format(cal.getTime()));
	    }
	    
		public static String getCurrentYearLastTwoDigits()
	    {
	    	String year = Calendar.getInstance().get(Calendar.YEAR)+"";
	    	return year.substring(2);
	    }
		
		public static String getFormatedDateFromStringDate(String date,String currentformat)
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(getSqlDateFromString(date,currentformat)); 
		}

		public static java.sql.Date getFutureDayDate(int increment)
	    {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        Calendar cal = Calendar.getInstance();
	        try
	        {
	            cal.add(Calendar.DATE, +increment);    
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        return getSqlDateFromString(sdf.format(cal.getTime()));
	    }
		public static void main(String...s) throws ParseException
		{
			System.out.println(getCurrentSystemTimestamp());
		}
		
		public static Timestamp appendStartTime(String date) throws ParseException
		{
			return DateUitls.getSqlTimestampFromString(date +" 00:00:00", "dd/MM/yyyy HH:mm:ss");
		}
		public static Timestamp appendEndTime(String date) throws ParseException
		{
			return DateUitls.getSqlTimestampFromString(date +" 23:59:59", "dd/MM/yyyy HH:mm:ss");
		}
		
		public static Timestamp getSqlTimestampFromStringForB(String timeStamp) throws ParseException
		{
			if (timeStamp != null)
			{
			    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm:ss");
			    java.util.Date parsedDate = dateFormat.parse(timeStamp);
			    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			    return timestamp;
			}
			return null;
		}
		
		public static String getIStFromUTCTime(String utcTime) throws ParseException
		{
			if (!StringUtils.isEmpty(utcTime))
			{
				SimpleDateFormat df = new SimpleDateFormat("HHmmss");
		        df.setTimeZone(TimeZone.getTimeZone("UTC"));
		        java.util.Date date = null;
		        try 
		        {
		            date = df.parse(utcTime);
		        } 
		        catch (ParseException e) 
		        {
		            e.printStackTrace();
		        }
		        df.setTimeZone(TimeZone.getDefault());
		        String formattedDate = df.format(date);
				
		       SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
		       return df2.format(df.parse(formattedDate));
			}
			return null;
		}
		public static String convertDateFormat(String sourceFormat,String value) throws ParseException
		{
			if (!StringUtils.isEmpty(value))
			{
				SimpleDateFormat df = new SimpleDateFormat(sourceFormat);
				java.util.Date date = null;
		        try 
		        {
		            date = df.parse(value);
		        } 
		        catch (ParseException e) 
		        {
		            e.printStackTrace();
		        }
		        String formattedDate = df.format(date);
				
		        SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
		        return df2.format(df.parse(formattedDate));
			}
			return null;
		}
}
