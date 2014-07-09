package cr.novatec.crcasas.client.valuemap;

import java.util.HashMap;
import java.util.LinkedHashMap;

// For Home = (H)ouse, (A)partment, (C)ondominum
public class PropertySubTypeValueMap extends LinkedHashMap<String, String> {
	
	// (H)ouse, (A)partment, (C)ondominum (B) Habitaciones (V) Vacaciones
	public PropertySubTypeValueMap() {
	    put("H", "Casa");  
	    put("A", "Apartamento");  
	    put("C", "Condominio");  
	    put("B", "Habitaciones");  
	    put("V", "Vacaciones");  
	    
	}

}


