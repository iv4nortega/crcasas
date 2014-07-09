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

import cr.novatec.crcasas.client.database.Division2;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

public class Division2Resource extends ServerResource  {

	
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();


    @Get
    public Representation retrieve()  {
    	
       	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
    	String callBack = queryParams.getFirstValue("callback");
  	
    	
    	String key = (String) this.getRequest().getAttributes().get("key");
    	   	
    	JSONObject jsonObj = new JSONObject();
    	
    	JSONArray jsonArr = new JSONArray();
    	
    	List<Division2> list;
    	
    	try {
    		
    		if (key != null )  list = db.getDivision2ListByDiv1(Integer.parseInt(key)); 
    		else list = db.getDivision2List();
    		
    		int c = 0;
    		 for (Division2 d2 : list) {    			 
    	      	 jsonArr.put(c, toJson(d2));    	      		 
    			 c++;
    		 }
    		     		
    		jsonObj.put("division2", jsonArr);
   		

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if (callBack!=null) {
    		
     		
    		return new StringRepresentation(callBack+"("+jsonArr.toString()+")");
    	}
    	
    	return new JsonRepresentation(jsonArr);
          
    }
	
	public JSONObject toJson(Division2 d2) throws JSONException {
		
		
		JSONObject j = new JSONObject();

		j.put("key", d2.getKey() );
		j.put("name", d2.getName());

		return j;
		
	}
    
}
