package cr.novatec.crcasas.client.resources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.appengine.api.datastore.GeoPt;


public class MyClientLibrary {

	private static MyClientLibrary instance = null;  
	
    public static MyClientLibrary getInstance() {  
        if (instance == null) {  
            instance = new MyClientLibrary();  
        }  
        return instance;  
    } 
	
    
	public static String sanitizeString(String string) {

		System.out.println(string);

		String returnString = "";
		if (string == null) returnString = "EMPTY";
		else  if (string.trim().length() == 0) returnString = "EMPTY";
		else {
			returnString = string.trim().toUpperCase().replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U").replace("Ñ","N");		
		}

		
		
		System.out.println(returnString);
		

		return returnString;		
	}
	
	
	public static int findDistrict(String[] names, String name)
	{
		name = sanitizeString(name);
		for (int n = 0; n < names.length; n++)
		{
			if (names[n].equalsIgnoreCase(name))
				return n;
		}
		
		for (int n = 0; n < names.length; n++)
		{
			if (names[n].contains(name))
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
	
	public static String[] splitAddress(String address)
	{
		return address.split(",");
	}
	
	public static String removeState(String address, String state)
	{
		return address.substring(address.indexOf(state), state.length());
	}
	
	public static int searchArray(String[] array, String target) {
		
		for(int i=0; i<array.length; i++) 
		         if(array[i].equals(target))
		             return i;
		
		return -1;
	}

}
