package cr.novatec.crcasas.server.restless;


import java.text.DecimalFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

public class HasAdsResource extends ServerResource  {

	
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();


    @Get
    public String retrieve()  {
      	
    	String user = (String) this.getRequest().getAttributes().get("user");
    	    	
    	Boolean hasAds = false;
    	
    			Person person = db.getFBUser(user);
    
    			if (person!=null) hasAds = db.hasAdsByFbId(person.getFbId());
     	    	
    	
    	return  hasAds.toString();
          
    }
	
    
}
