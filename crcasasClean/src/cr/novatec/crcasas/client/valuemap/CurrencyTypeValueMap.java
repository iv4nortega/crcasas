package cr.novatec.crcasas.client.valuemap;

import java.util.LinkedHashMap;

import cr.novatec.crcasas.client.Parameters;


public class CurrencyTypeValueMap extends LinkedHashMap<String, String> {
	

	public CurrencyTypeValueMap() {
	    put("C", Parameters.CRC_CURRENCY);  
	    put("D", Parameters.US_CURRENCY);  
	}

}


