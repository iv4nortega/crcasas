package cr.novatec.crcasas.server.restless;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.datastore.GeoPt;

import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

public class Division2Geo extends ServerResource  {

	
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();


    @Get
    public Representation retrieve()  {
    	
    	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
 	   String callBack = queryParams.getFirstValue("callback");
 	
    	
    	String key = (String) this.getRequest().getAttributes().get("key");
    	
    	
    	JSONObject jsonObj = new JSONObject();
    	
    	try {
    		jsonObj = toJson(db.getDivision2Geo(Integer.parseInt(key)));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if (callBack!=null) {
    		
    		//return new JsonRepresentation(jsonObj);
    		
    		return new StringRepresentation(callBack+"("+jsonObj.toString()+")");
    	}
    	
    	return new JsonRepresentation(jsonObj);
    	   	
          
    }
	
	public JSONObject toJson(GeoPt geo) throws JSONException {
		
		
		JSONObject j = new JSONObject();

		j.put("lat", geo.getLatitude() );
		j.put("lng", geo.getLongitude());

		return j;
		
	}
    
}
