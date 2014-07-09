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
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

public class AdsResource extends ServerResource implements AdResource {

	
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	private DecimalFormat formatter = new DecimalFormat("###,###,###");

    @Get
    public Representation retrieve()  {
    	
    	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
 	   String callBack = queryParams.getFirstValue("callback");

   	
    	String type = (String) this.getRequest().getAttributes().get("type");
    	String mode = (String) this.getRequest().getAttributes().get("mode");
    	String division1 = (String) this.getRequest().getAttributes().get("division1");
    	String division2 = (String) this.getRequest().getAttributes().get("division2");
   	
    	String propertyType = "H";
    	Boolean sale = true;
    	
    	if (mode!=null && mode.equalsIgnoreCase("true")) sale = true; 
    	else sale = false;
    	
       	if (type==null)  propertyType="H"; 
       	
       	
       	else {
       		
       		propertyType = type.toUpperCase();
  		
       	};
  	


    	Integer div1 = null;
    	
    	if (division1 !=null ) div1 = Integer.parseInt(division1);

    	Integer div2 = null;
    	
    	if (division2 !=null ) div2 = Integer.parseInt(division2);
    	
    	JSONObject jsonObj = new JSONObject();
    	
    	JSONArray jsonArr = new JSONArray();
    	
    	List<Ad> list;
    	
    	try {
    		
    		if (div1 != null) {
    			if (div2 !=null) {
    				
    				list = db.getAdsByFilter(propertyType, sale, div1, div2);
    			}
    			else {
    				list = db.getAdsByFilter(propertyType, sale, div1);
    			}
    			
    		}
    		else  list = db.getPremiumAds();
    		 
    		
    		int c = 0;
    		 for (Ad a : list) {    			 
    	      	 jsonArr.put(c, toJson(a));    	      		 
    			 c++;
    		 }
    		     		
    		jsonObj.put("anuncios", jsonArr);
   		

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	if (callBack!=null) {    		
    		return new StringRepresentation(callBack+"("+jsonArr.toString()+")");
    	}
    	
    	return new JsonRepresentation(jsonArr);
          
    }
	
	public JSONObject toJson(Ad ad) throws JSONException {
		
		
		JSONObject j = new JSONObject();
		j.put("id", ad.getSourceKey());
		if ( ad.getShowName()) j.put("name", ad.getFirstName()+ " " + ad.getLastName() );
		if ( ad.getImageUrl()!=null ) j.put("imgUrl", ad.getImageUrl()+"=s144");
		j.put("address", db.getDivision2ById(ad.getCountyId())+ ", " + db.getDivision1ById(ad.getStateId()));
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


		return j;
		
	}
    
}
