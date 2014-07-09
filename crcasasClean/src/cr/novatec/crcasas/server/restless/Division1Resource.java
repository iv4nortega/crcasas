package cr.novatec.crcasas.server.restless;

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

import cr.novatec.crcasas.client.database.Division1;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

public class Division1Resource extends ServerResource  {

	
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();


    @Get
    public Representation retrieve()  {
    	

    	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
    	String callBack = queryParams.getFirstValue("callback");

    	
    	JSONObject jsonObj = new JSONObject();
    	
    	JSONArray jsonArr = new JSONArray();
    	
    	try {
    		
    		
    		List<Division1> list = db.getDivision1List();
    		
    		int c = 0;
    		 for (Division1 d1 : list) {    			 
    	      	 jsonArr.put(c, toJson(d1));    	      		 
    			 c++;
    		 }
    		     		
    		jsonObj.put("division1", jsonArr);
   		

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if (callBack!=null) {    		
    		return new StringRepresentation(callBack+"("+jsonArr.toString()+")");
    	}

    	
    	return new JsonRepresentation(jsonArr);
          
    }
	
	public JSONObject toJson(Division1 d1) throws JSONException {
		
		
		JSONObject j = new JSONObject();

		j.put("key", d1.getKey() );
		j.put("name", d1.getName());

		return j;
		
	}
    
}
