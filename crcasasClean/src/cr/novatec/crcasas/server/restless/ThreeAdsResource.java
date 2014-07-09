package cr.novatec.crcasas.server.restless;


import java.text.DecimalFormat;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.server.resources.MyServerLibrary;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

public class ThreeAdsResource extends ServerResource implements AdResource {

	
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	private DecimalFormat formatter = new DecimalFormat("###,###,###");

	MyServerLibrary library = new MyServerLibrary();
	
    @Get
    public Representation retrieve()  {
    	
   	
    	String type = (String) this.getRequest().getAttributes().get("type");
   	
    	
    	JSONObject jsonObj = new JSONObject();
    	
    	JSONArray jsonArr = new JSONArray();
    	
    	List<Ad> list;

		list = db.getThreeAdsList();

    	if (!list.isEmpty()) {
    		
        	try {
        		int c = 0;
       		 for (Ad a : list) {    			 
       	      	 if (a.notEmpty()) jsonArr.put(c, toJson(a));    	      		 
       			 c++;
       		 }
       		     		
       		jsonObj.put("anuncios", jsonArr);

    		} catch (JSONException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    		
    	}
		

    	
    	
    	
    	return new JsonRepresentation(jsonArr);
          
    }
	
	public JSONObject toJson(Ad ad) throws JSONException {
		
		
		JSONObject j = new JSONObject();
		j.put("id", ad.getId());
		if ( ad.getShowName()) j.put("name", ad.getFirstName()+ " " + ad.getLastName() );
		if ( ad.getImageUrl()!=null ) j.put("imgUrl", ad.getImageUrl()+"=s170-c");
		
		String location = db.getDivision2ById(ad.getCountyId())+ ", " + db.getDivision1ById(ad.getStateId());
		
		
		j.put("address", library.capitalizeFirst(location));
		j.put("price", ad.getImageUrl());
		

		String price = ad.getCurrencyFmt()+formatter.format(ad.getPrice().doubleValue());		
		String rentPrice = ad.getCurrencyFmt()+formatter.format(ad.getRentPrice().doubleValue());		
		
	
		if (ad.getSale()) j.put("price", price);
		else j.put("price", rentPrice);

		if ( ad.getShowPhone()) j.put("phone", ad.getPhone());

		j.put("caption", ad.getTitle());
		j.put("ispremium", ad.isPremium());
		j.put("isexclusive", ad.isExclusive());
		j.put("isbasic", ad.isBasic());
		j.put("description", ad.getDescription());


		return j;
		
	}
    
}
