package cr.novatec.crcasas.client.valuemap;

import java.util.LinkedHashMap;

// L = Lot B = Building H = Home;
public class PropertyTypeValueMap extends LinkedHashMap<String, String> {
	

	public PropertyTypeValueMap() {
	    put("H", "Casa");  
		put("L", "Lote");  
	    put("B", "Edificio");  
	    put("C", "Comercial");  

	}

}


