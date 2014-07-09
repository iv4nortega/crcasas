package cr.novatec.crcasas.client.valuemap;

import java.util.HashMap;
import java.util.LinkedHashMap;

//A = active   E = expired D = deleted; S = Detenido P Pendiente C Confirmado N Nuevo
public class StatusValueMap extends LinkedHashMap<String, String> {
	

	public StatusValueMap() {
	    put("A", "Activo");  
	    put("E", "Expiro");  
	    put("D", "Borrado");  
	    put("S", "Detenido");  
	    put("P", "Pendiente");  
	    put("C", "Confirmado");  
	    put("N", "Nuevo");  

	 // A = active   E = expired D = deleted; S = Detenido stop P= Pendiente de Conf C= Confirmado  N=Nuevo
	}

}


