package com.abhaya.vehicle.tracking.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Month;
import java.util.ArrayList;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtility 
{

	private static Logger logger =  LoggerFactory.getLogger(CommonUtility.class);
	public static enum DataType 
	{
		int8((byte)0x0f),
		int16((byte)0x10);
		private Byte datatype;
		
		DataType(Byte datatype)
		{
			this.datatype = datatype;
		}
		public Byte getCode(){
			return datatype;
		}
	}
	
	class StructurePacket{
		int structurecount;
		int totaldatacount;
		byte[] converteddata;
		public int getStructurecount() {
			return structurecount;
		}
		public void setStructurecount(int structurecount) {
			this.structurecount = structurecount;
		}
		public int getTotaldatacount() {
			return totaldatacount;
		}
		public void setTotaldatacount(int totaldatacount) {
			this.totaldatacount = totaldatacount;
		}
		public byte[] getConverteddata() {
			return converteddata;
		}
		public void setConverteddata(byte[] converteddata) {
			this.converteddata = converteddata;
		}
	}
	public static String toDate(byte[] data)
	{
		if(data[0] == 0x00)
		{
			return "00-00-00 00:00:00";
		}
		int year =  toInt(subbytes(data, 0,1));
		int month = toInt(subbytes(data, 1,2));
		int day = toInt(subbytes(data, 2,3));
		
		int hrs = toInt(subbytes(data, 3,4));
		int min =  toInt(subbytes(data, 4,5));
		int sec =  toInt(subbytes(data, 5,6));
	
		
		String ret  = String.format("%02d",day) +"-"+String.format("%02d",month)+"-"+String.format("%02d",year)+" "+String.format("%02d",hrs)+":"+String.format("%02d",min)+":"+String.format("%02d",sec);
		return ret;
	}
	public static String  dateToHex(String date)
	{
		logger.info("Date Data"+date);
		int year =  Integer.parseInt(date.substring(0,2));
		int month =  Integer.parseInt(date.substring(2,4));
		int day =  Integer.parseInt(date.substring(4,6));
		int hour =  Integer.parseInt(date.substring(6,8));
		int minuts =  Integer.parseInt(date.substring(8,10));
		int secs =  Integer.parseInt(date.substring(10,12));

		return decimalToHex(year)+ decimalToHex(month)+ decimalToHex(day)+ decimalToHex(hour)+ decimalToHex(minuts)+ decimalToHex(secs) ;
		
	}
	public static String toTime(byte[] data)
	{
		if(data[0]==0x00)
		{
			return "00:00";
		}
		
		int hrs = toInt(subbytes(data, 0,1));
		int min =  toInt(subbytes(data, 1,2));
	
		String ret  = String.format("%02d",hrs)+":"+String.format("%02d",min);
		return ret;
	}
	public static String toMonth(byte[] data)
	{
		if (data[0]==0x00)
		{
			return "00:00";
		}
		int hrs = toInt(subbytes(data, 0,1));
		int min =  toInt(subbytes(data, 1,2));
	
		String ret  = Month.of(hrs)+" "+String.format("%02d",min);
		return ret;
	}
	public static String toHex(byte[] bytes) 
    {   
        return toHex(bytes, 0, bytes.length);        
    } 
	
	public static String toHex(byte[] bytes, int index, int count) 
    {         
        final char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        char[] hexChars = new char[count * 3];
        int tmp;
        for (int pos = index; pos != count; ++pos) 
        {
            tmp = bytes[pos] & 0xFF;
            hexChars[pos * 3] = hexArray[tmp >>> 4];
            hexChars[pos * 3 + 1] = hexArray[tmp & 0x0F];
            hexChars[pos * 3 + 2] = ' ';
        }
        return new String(hexChars);
    }   
	
	// Raj added
	public static  String asHex(byte[] buf)
	    {
		  	final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
	        char[] chars = new char[2 * buf.length];
	        for (int i = 0; i < buf.length; ++i)
	        {
	            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
	            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
	        }
	        return new String(chars);
	    }

	public static String hexToASCII(String hexValue)
	{
	      StringBuilder output = new StringBuilder("");
	      for (int i = 0; i < hexValue.length(); i += 2)
	      {
	    	  
	         String str = hexValue.substring(i, i + 2);
	         
	         output.append((char) Integer.parseInt(str, 16));
	      }
	      return output.toString();
	}
	public static String hexToASCII(byte[] data) {
	    StringBuilder sb = new StringBuilder(data.length);
	    for (int i = 0; i < data.length; ++ i) {
	        if (data[i] < 0) throw new IllegalArgumentException();
	        sb.append((char) data[i]);
	    }
	    return sb.toString();
	}
	
	public static byte[] hexToBytes(String str, boolean includeSpace)
    {
        if (includeSpace && !str.isEmpty() && str.charAt(str.length() -1) != ' ')
        {
            str += " ";
        }
        int cnt = includeSpace ? 3 : 2;
        if (str.length() == 0 || str.length() % cnt != 0)
        {
            throw new RuntimeException("Not hex string");
        }
        byte[] buffer = new byte[str.length() / cnt];
        char c;
        for (int bx = 0, sx = 0; bx < buffer.length; ++bx, ++sx)
        {
            c = str.charAt(sx);
            buffer[bx] = (byte)((c > '9' ? (c > 'Z' ? (c - 'a' + 10) : (c - 'A' + 10)) : (c - '0')) << 4);
            c = str.charAt(++sx);
            buffer[bx] |= (byte)(c > '9' ? (c > 'Z' ? (c - 'a' + 10) : (c - 'A' + 10)) : (c - '0'));
            if (includeSpace)
            {
                ++sx;
            }
        }
        return buffer;
    }

	public static byte[] subbytes(byte[] source, int srcBegin) {
	    return subbytes(source, srcBegin, source.length);
	}
	
	public static byte[] subbytes(byte[] source, int srcBegin, int srcEnd) {
	    byte destination[];

	    destination = new byte[srcEnd - srcBegin];
	    getBytes(source, srcBegin, srcEnd, destination, 0);

	    return destination;
	}
	
	public static void getBytes(byte[] source, int srcBegin, int srcEnd, byte[] destination,int dstBegin) 
	{
		    System.arraycopy(source, srcBegin, destination, dstBegin, srcEnd - srcBegin);
	}
	
	public static int getLenfromBytes(byte[] length){
		length[0] = (byte) (length[0] & (byte)0x07);
		length[1] = (byte) (length[1] & (byte)0xFF);
		BigInteger b = new BigInteger(length);
		return b.intValue();
	}
	
	public static int toInt(byte byteval){
		Byte b  = new Byte(byteval);
		return b.intValue();
	}
	
	public static int toInt(byte[] byteval){
		BigInteger b = new BigInteger(byteval);
		return b.intValue();
	}
	
	public static String toInts(byte[] byteval){
		BigInteger b = new BigInteger(byteval);
		return b.toString();
	}
	public static int toInt(byte[] bytes, int offset) {
		  int ret = 0;
		  for (int i=0; i<2 && i+offset<bytes.length; i++) {
		    ret <<= 8;
		    ret |= (int)bytes[i] & 0xFF;
		  }
		  return ret;
		}
	
	public static boolean check7EstartEnd(byte[] rawdata){
		if((rawdata[0] == ((byte)0x7E)) && (rawdata[rawdata.length-1] == ((byte)0x7E))){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean isMultiPacket(byte[] lenpacket){
		byte b= (byte) (lenpacket[0] & (byte)0x08);
		if(b == (byte) 0x08){
			return true;
		}else{
			return false;
		}
	}
	
	public static byte[] combineByteArray(byte[] a, byte[] b) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		try {
			outputStream.write( a );
			outputStream.write( b );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("{}", e);;
		}
		return outputStream.toByteArray( );
	}
	
	public static byte[] combinePacket(byte[] rawdata){
		int stringpos=1;
		int datacount = getLenfromBytes(CommonUtility.subbytes(rawdata, stringpos, stringpos+2));
		logger.info("Raw Data :"+toHex(rawdata));

		byte[] a = subbytes(rawdata, stringpos,stringpos = stringpos+datacount-2);
		while(stringpos+3 < rawdata.length){
			int datacount2 = getLenfromBytes(subbytes(rawdata, stringpos+4, stringpos+6));
			byte[] b = subbytes(rawdata,stringpos=stringpos+4,stringpos=stringpos+datacount2-2);
			a = combineByteArray(a,subbytes(b,7));
		}
		return a;
	}

	public static ArrayList<byte[]> combinePacket2(byte[] rawdata){
		int stringpos=-3;
		ArrayList<byte[]> returnarr = new ArrayList<byte[]>();
		byte[] a = null;
		int arrayc = 1;
		while(stringpos+3<rawdata.length){
		int datacount = getLenfromBytes(CommonUtility.	subbytes(rawdata, stringpos+4, stringpos+6));
		boolean isMulti = isMultiPacket(subbytes(rawdata,stringpos+4, stringpos+6));
		byte[] b = subbytes(rawdata, stringpos = stringpos+4,stringpos = stringpos+datacount-2);
		if(a == null){
			a = b;
		}else{
			a = combineByteArray(a,subbytes(b,7));
		}
		if(!isMulti){
			returnarr.add(a);
			arrayc++;
			a=null;
		}
		}	
		return returnarr;
	}
	

	public static ArrayList<byte[]> combinePacket3(byte[] rawdata){
		int stringpos=-3;
		ArrayList<byte[]> returnarr = new ArrayList<byte[]>();
		byte[] a = null;
		int arrayc = 1;
		while(stringpos+4<rawdata.length){
			
		byte[] datatype = subbytes(rawdata,stringpos+3,stringpos+4);
		int datacount = getLenfromBytes(CommonUtility.subbytes(rawdata, stringpos+5, stringpos+7));
		boolean isMulti = isMultiPacket(subbytes(rawdata,stringpos+5, stringpos+7));
		
		byte[] b = subbytes(rawdata, stringpos = stringpos+5,stringpos = stringpos+datacount-2);
		if(a == null){
			a = b;
		}else{
			a = combineByteArray(a,subbytes(b,7));
		}
		
		if(!isMulti){
			byte[] x = combineByteArray(datatype, a);
			returnarr.add(x);
			arrayc++;
			a=null;
		}
		}	
		
		return returnarr;
	}
	
	public static String convertDate(byte[] data)
	{
		//logger.info("Date Data"+toHex(data));
		
		if(data[0]==0x00){
			return "00-00-00 00:00:00";
		}
		int year = toInt(subbytes(data, 1,3));
		int month = toInt(subbytes(data, 3,4));
		int day = toInt(subbytes(data, 4,5));
		int hrs = toInt(subbytes(data, 6,7));
		int min = toInt(subbytes(data, 7,8));
		int sec = toInt(subbytes(data, 8,9));
		
		String ret  = String.format("%02d",day) +"-"+String.format("%02d",month)+"-"+String.format("%04d",year)+" "+String.format("%02d",hrs)+":"+String.format("%02d",min)+":"+String.format("%02d",sec);
		return ret;
	}

	
	public static ArrayList<byte[]> getBlocksofData(byte[] rawdata){
		
		int stringpos=13;
		logger.info("Billing Data Truncated" + toHex(subbytes(rawdata, stringpos)));
		
		int i=0;
		stringpos++; // 81 -> Parameter ID
		
		
		if(subbytes(rawdata, stringpos,stringpos=stringpos+1)[0] == (byte)0x00){
			//More Blocks Coming in 
			BigInteger x = new  BigInteger(subbytes(rawdata, stringpos,stringpos=stringpos+4));
			int blockid = x.intValue();
			
			stringpos = stringpos + 2 ;
			int sizeblock = new BigInteger(subbytes(rawdata, stringpos,stringpos=stringpos+2)).intValue();
			
			if(blockid == 1 ){
				stringpos++;
				int numberofblocks = new BigInteger(subbytes(rawdata, stringpos,stringpos=stringpos+1)).intValue();
			
			}
			}
			
			stringpos++;
			int numofelements = new BigInteger(subbytes(rawdata, stringpos,stringpos=stringpos+1)).intValue();
			
			
			byte datatype = CommonUtility.subbytes(rawdata, stringpos,stringpos=stringpos+1)[0];
			byte[] dataval = null;
			switch(datatype){
				case (byte)0x09:
					dataval = CommonUtility.subbytes(rawdata,stringpos,stringpos=stringpos+13);
					break;
				case (byte)0x12:
					dataval = CommonUtility.subbytes(rawdata,stringpos,stringpos=stringpos+2);
					break;
				case (byte)0x0F:
					dataval = CommonUtility.subbytes(rawdata,stringpos,stringpos=stringpos+1);
					break;
				case (byte)0x06:
					dataval = CommonUtility.subbytes(rawdata,stringpos,stringpos=stringpos+4);
					break;
				case (byte)0x05:
					dataval = CommonUtility.subbytes(rawdata,stringpos,stringpos=stringpos+4);
					break;
				default:
					break;
			}
		
		return null;
	}

	
	public static String getUnits(byte datatype) {
		// TODO Auto-generated method stub
		switch(datatype){
		case (byte)0x21:
			return "A";
		case (byte)0x22:
			return "C";
		case (byte)0x23:
			return "V";
		case (byte)0x2C:
			return "Hz";
		case (byte)0x1C:
			//return "VA";
			return "K";
		case (byte)0x1B:
			//return "W";
			return "K";
		case (byte)0x1D:
			//return "var";
			return "K";
		case (byte)0x1E:
			//return "Wh";
			return "K";		
		case (byte)0x20:
			//return "varh";
			return "K";
		case (byte)0x1F:
			//return "VAh";
			return "K";
		
		default:
			return "";
		}
			
	
		
	}
	public static float round(float d) {
		String str = String.format("%.2f", d);
		Float f= new Float(4.05);
		return f.parseFloat(str);
    }
	public static float getValue(byte[] data, int scalar) {
		int val = new BigInteger(data).intValue();
		float finalval = (float) (val*Math.pow(10, scalar));
		
		return round(finalval);
	}
	
	public static float getValue(byte[] data) {
		int val = new BigInteger(data).intValue();
		float finalval = (float) (val);
		return round(finalval);
	}

	public static String getEventCode(String s, byte datatype) {
		
		int code=toInt(hexToBytes(s, true));
		return Integer.toString(code);
		
		//return "NA";
		/*switch(code){
		
		case 1:
			return "1";
		case 2:
			return "1";
		case 3:
			return "2";
		case 4:
			return "2";
		case 5:
			return "3";
		case 6:
			return "3";
		case 7:
			return "33";
		case 8:
			return "33";
		case 9:
			return "58";
		case 10:
			return "58";
		case 11:
			return "14";
		case 12:
			return "14";
		case 51:
			return "4";
		case 52:
			return "4";
		case 53:
			return "5";
		case 54:
			return "5";
		case 55:
			return "6";
		case 56:
			return "6";
		case 57:
			return "24";
		case 58:
			return "24";
		case 59:
			return "25";
		case 60:
			return "25";
		case 61:
			return "26";
		case 62:
			return "26";
		case 63:
			return "7";
		case 64:
			return "7";
		case 65:
			return "19";
		case 66:
			return "19";
		case 67:
			return "37";
		case 68:
			return "37";
		case 101:
			return "13";
		case 102:
			return "13";
			
		case 201:
			return "27";
		case 202:
			return "27";
		case 203:
			return "28";
		case 204:
			return "28";
		case 205:
			return "12";
		case 206:
			return "12";
		case 251:
			return "60";
		
		case 151:
			return "3";
		case 152:
			return "8";
		case 153:
			return "2";
		case 154:
			return "9";
		case 155:
			return "6";
			
		}
		return "NA";*/
	}
	
public static String getStatusCode(String s, byte datatype) {
		
		int code=toInt(hexToBytes(s, true));
		switch(code){
		case 1:
			return  "1";
		case 2:
			return "0";
		case 3:
			return "1";
		case 4:
			return "0";
		case 5:
			return "1";
		case 6:
			return "0";
		case 7:
			return "1";
		case 8:
			return "0";
		case 9:
			return "1";
		case 10:
			return "0";
		case 11:
			return "1";
		case 12:
			return "0";
		case 51:
			return "1";
		case 52:
			return "0";
		case 53:
			return "1";
		case 54:
			return "0";
		case 55:
			return "1";
		case 56:
			return "0";
		case 57:
			return "1";
		case 58:
			return "0";
		case 59:
			return "1";
		case 60:
			return "0";
		case 61:
			return "1";
		case 62:
			return "0";
		case 63:
			return "1";
		case 64:
			return "0";
		case 65:
			return "1";
		case 66:
			return "0";
		case 67:
			return "1";
		case 68:
			return "0";
		case 101:
			return "1";
		case 102:
			return "0";
		case 151:
			return "1";
		case 152:
			return "1";
		case 153:
			return "1";
		case 154:
			return "1";
		case 155:
			return "1";
		case 201:
			return "1";
		case 202:
			return "0";
		case 203:
			return "1";
		case 204:
			return "0";
		case 205:
			return "1";
		case 206:
			return "0";
		case 251:
			return "1";
		case 301:
			return "0";
		case 302:
			return "1";
		/*case 1:
			return "0";
		case 2:
			return "1";
		case 3:
			
			
			return "0";
		case 4:
			return "1";
		case 5:
			return "0";
		case 6:
			return "1";
		case 7:
			return "0";
		case 8:
			return "1";
		case 9:
			return "0";
		case 10:
			return "1";
		case 11:
			return "0";
		case 12:
			return "1";
		case 51:
			return "0";
		case 52:
			return "1";
		case 53:
			return "0";
		case 54:
			return "1";
		case 55:
			return "0";
		case 56:
			return "1";
		case 57:
			return "0";
		case 58:
			return "1";
		case 59:
			return "0";
		case 60:
			return "1";
		case 61:
			return "0";
		case 62:
			return "1";
		case 63:
			return "0";
		case 64:
			return "1";
		case 65:
			return "0";
		case 66:
			return "1";
		case 67:
			return "0";
		case 68:
			return "1";
		case 101:
			return "0";
		case 102:
			return "1";
			
		case 201:
			return "0";
		case 202:
			return "1";
		case 203:
			return "0";
		case 204:
			return "1";
		case 205:
			return "0";
		case 206:
			return "1";
		case 251:
			return "0";
		case 151:
			return "0";
		case 152:
			return "0";
		case 153:
			return "0";
		case 154:
			return "0";
		case 155:
			return "0";*/
		
		}
		return "NA";
	}
	

	public static Properties getProps (String fileName) {
		Properties props = null;
		try {
			File file = new File(fileName);
			FileInputStream fileInput = new FileInputStream(file);
			props = new Properties();
			props.load(fileInput);
		} catch (Exception e) {
			logger.error("{}", e);;
		}
		return props;
	}
	  
	  public static int getInterval(Properties props) {
		  int interval = 15;
		  try {
			  interval = Integer.parseInt(props.getProperty("QUERYINTERVAL", "15"));
		  } catch (NumberFormatException nfe) {
			  logger.info("{}", nfe);
			  interval = 15;
		  }
		  return interval;
	  }
	  
	  // Raj added
	  public static int hexToDecimal(String s) {
	        String digits = "0123456789ABCDEF";
	        s = s.toUpperCase();
	        int val = 0;
	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);
	            int d = digits.indexOf(c);
	            val = 16*val + d;
	        }
	        return val;
	  }
	  
	  // precondition:  d is a nonnegative integer
	  public static String decimalToHex(int d) 
	  {
	        String digits = "0123456789ABCDEF";
	        if (d == 0) return "00";
	        String hex = "";
	        while (d > 0) 
	        {
	            int digit = d % 16;                // rightmost digit
	            hex = digits.charAt(digit) + hex;  // string concatenation
	            d = d / 16;
	        }
	        hex = appendZeros(hex, 2-hex.length());
	        return hex;
	  }
	  
	  public static BigInteger hexToDecimalAsUnSinged(byte[] data) {
	        BigInteger unsigned = new BigInteger(1, data);
			return unsigned;
	  }
	  
	  public static BigInteger hexToDecimalAsSinged(byte[] data) {
	        BigInteger unsigned = new BigInteger(data);
			return unsigned;
	  }
	  
	  public static Integer hexToDecimalAsInt(byte[] data) {
		  BigInteger b = new BigInteger(data);
			return b.intValue();
	  }
	  
	  public static long hexToDecimalAsLong(byte[] data) {
		  BigInteger b = new BigInteger(data);
			return b.longValue();
	  }
	  
	  public static float hexToDecimalAsFloat(byte[] data) {
		  BigInteger b = new BigInteger(data);
			return b.floatValue();
	  }
	  
	  public static double hexToDecimalAsDouble(byte[] data) {
		  BigInteger b = new BigInteger(data);
			return b.doubleValue();
	  }
	  
	  // Code to convert byte arr to str: 
	  public static String byteArrayToString(byte[] data){
	        return new String(toHex(data));
	  }
	  
	  
	public static String calculateCRC(int lenth, byte[] inputValue) 
	{
		int counter = 0, count = 0;
		int crcValue = 0xffff;

		for (counter = 0; counter < lenth; counter++) 
		{
			crcValue ^= inputValue[counter];
			for (count = 0; count < 8; count++) 
			{
				if ((crcValue % 2) > 0) 
				{
					crcValue = crcValue / 2;
					crcValue ^= 0xA001;
				} 
				else 
				{
					crcValue = crcValue / 2;
				}
			}
		}
		return reverseCRC(appendZeros(Integer.toHexString(crcValue).toUpperCase(),4-Integer.toHexString(crcValue).toUpperCase().length()));
	}
	public static String stringToHex(String value)
	{
		//use for serail number
		return String.format("%x", new BigInteger(value));
	}
	public static String stringToHex1(String value)
	{
		return String.format("%x", value);
	}
	public static String asciiToHex(String asciiValue)
	{
	    char[] chars = asciiValue.toCharArray();
	    StringBuffer hex = new StringBuffer();
	    for (int i = 0; i < chars.length; i++)
	    {
	        hex.append(Integer.toHexString((int) chars[i]));
	    }
	    return hex.toString();
	}  
	public static String appendZeros(String value,int noOfZeros)
	{
		for (int i = 0; i < noOfZeros; i++)
		{
			value = "0" + value;
		}
		return value;
	}
	public static String appendTrailZeros(String value, int noOfZeros) 
	{
		for (int i = 0; i < noOfZeros; i++)
		{
			value =  value +  "0";
		}
		return value;
	}
	public static String reverseCRC(String crc) 
	{
		return crc.substring(2) + crc.substring(0,2);
	}
	public static String removeExponantial(double d)
	{
		String s = new BigDecimal(d).toPlainString();
		return String.format("%.2f",Double.parseDouble(s));
	}
}