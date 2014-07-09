package cr.novatec.crcasas.server.resources;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.api.datastore.GeoPt;

import cr.novatec.crcasas.client.Parameters;


public class MyServerLibrary {

	private static MyServerLibrary instance = null;  
	
    public static MyServerLibrary getInstance() {  
        if (instance == null) {  
            instance = new MyServerLibrary();  
        }  
        return instance;  
    } 
	
    
	public static String sanitizeString(String string) {
		String returnString = "";
		if (string == null) returnString = "EMPTY";
		else  if (string.trim().length() == 0) returnString = "EMPTY";
		else {
			returnString = string.trim().toUpperCase().replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U").replace("Ñ","N");		
		}

		return returnString;		
	}
	

	
	public Date stringToDate(String dateString) {
		
		java.util.Date date;
		
		try {
			if (dateString != null )
			date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(dateString);
			else date = new SimpleDateFormat("dd/MM/yyyy", Locale.US).parse("01/01/2011");
		
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

		
	}
	
	public String capitalizeFirst(String string) {
		  char[] chars = string.toLowerCase().toCharArray();
		  boolean found = false;
		  for (int i = 0; i < chars.length; i++) {
		    if (!found && Character.isLetter(chars[i])) {
		      chars[i] = Character.toUpperCase(chars[i]);
		      found = true;
		    } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
		      found = false;
		    }
		  }
		  return String.valueOf(chars);
	}
	
	public Date stringToDate2(String dateString) {
		
		java.util.Date date;
		try {
						
			if (dateString != null )
				date = new SimpleDateFormat("d MMM yyyy", Locale.US).parse(dateString);
			else date = new SimpleDateFormat("d MMM yyyy", Locale.US).parse("1 Jan 2011");
		
			return date;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

		
	}
	
	
	
	public Long stringToLong(String sNumber) {
		String digits = "";
		if (sNumber != null)	{	
			sNumber = sNumber.replaceFirst("(\\.[0-9][0-9])[^0-9]*$", "" );
			sNumber = sNumber.replaceFirst("(,[0-9][0-9])[^0-9]*$", "" );
			

			Pattern p = Pattern.compile("([0-9]+)");                       
			Matcher m = p.matcher(sNumber);
			while (m.find()) { 
				digits = digits + m.group(1);
			}
			return Long.parseLong(digits);
		}
		else return 0L;
	}

	public Integer stringToInteger(String sNumber) {
		String digits = "";
		
		if (sNumber != null)	{	
			
			sNumber = sNumber.replaceFirst("(\\.)([0-9][0-9][0-9])", "$2" );
			sNumber = sNumber.replaceFirst("(\\,)([0-9][0-9][0-9])", "$2" );

			
			sNumber = sNumber.replaceFirst("(\\.[0-9][0-9])", "" );
			sNumber = sNumber.replaceFirst("(\\,[0-9][0-9])", "" );
			

			Pattern p = Pattern.compile("([0-9]+)");                       
			Matcher m = p.matcher(sNumber);
			while (m.find()) { 
				digits = digits + m.group(1);
			}
			if (digits.length()>=1 )
			return Integer.parseInt(digits);
			else return 0;
		}
		else return 0;	}
	
	
	public static int nameSearch(String[] names, String name)
	{
		for (int n = 0; n < names.length; n++)
		{
			if (names[n].equals(name))
				return n;
		}

		return -1;
	}
	
	public static Integer findState(String address)
	{
		address = sanitizeString(address);
		if (address.contains("ALAJUELA"))
			return 2;
		if (address.contains("SAN JOSE"))
			return 1;
		if (address.contains("CARTAGO"))
			return 3;
		if (address.contains("HEREDIA"))
			return 4;
		if (address.contains("PUNTARENAS"))
			return 6;
		if (address.contains("LIMON"))
			return 7;
		if (address.contains("GUANACASTE"))
			return 5;
		return 1;
	}
	
	public static GeoPt stringToGeo(String sGeoPoint, String pattern) {
		// example pattern: "([-+]?[0-9]*\\.?[0-9]+),.([-+]?[0-9]*\\.?[0-9]+)"
		GeoPt g = null;
		if (sGeoPoint != null)	{	
		    Pattern p = Pattern.compile(pattern);                       
	        Matcher m = p.matcher(sGeoPoint);
	         while (m.find()) { 
	    		 g = new GeoPt(Float.parseFloat(m.group(1)), Float.parseFloat(m.group(2)));
	        	 return g;
	        }
			}	
		return g;
	}
	

	
	
}
