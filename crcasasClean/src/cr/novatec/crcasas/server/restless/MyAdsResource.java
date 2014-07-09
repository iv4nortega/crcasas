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


import cr.novatec.crcasas.client.Parameters;
import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

public class MyAdsResource extends ServerResource implements AdResource {

	
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();
	private DecimalFormat formatter = new DecimalFormat("###,###,###");


    @Get
    public Representation retrieve()  {
    	
    	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
 	   String callBack = queryParams.getFirstValue("callback");

      	
    	String user = (String) this.getRequest().getAttributes().get("user");
    	
    	JSONObject jsonObj = new JSONObject();
    	
    	JSONArray jsonArr = new JSONArray();
    	
    	List<Ad> list;
    	
    	try {
    		
    			Person person = db.getFBUser(user);
    
    			
    			// list =  db.getAdList(person.getEmail());
  
    			list = db.getAdsByFbId(person.getFbId());
     		 
    		if (list==null)	 return null;	
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
    	
    	if (callBack!=null) return new StringRepresentation(callBack+"("+jsonArr.toString()+")");    	
    	else return new JsonRepresentation(jsonArr);
          
    }
	
	public JSONObject toJson(Ad ad) throws JSONException {
		
		
		JSONObject j = new JSONObject();
		j.put("id", ad.getId());

		j.put("sourceKey", ad.getSourceKey());
		j.put("sourceUrl", Parameters.HOST_DOMAIN + Parameters.AD_HASHTAG + ad.getSourceKey());

		
		if ( ad.getImageUrl()!=null ) j.put("imgUrl", ad.getImageUrl()+"=s96");
		j.put("address", db.getDivision2ById(ad.getCountyId())+ ", " + db.getDivision1ById(ad.getStateId()));
		

		String price = ad.getCurrencyFmt()+formatter.format(ad.getPrice().doubleValue());		
		String rentPrice = ad.getCurrencyFmt()+formatter.format(ad.getRentPrice().doubleValue());		
		
	
		if (ad.getSale()) j.put("price", price);
		else j.put("price", rentPrice);

		j.put("caption", ad.getTitle());

		j.put("adTypeName", ad.getAdTypeName());
		j.put("adType", ad.getAdType());
		j.put("views", ad.getViewCount());
		
		
		if (ad.getStatus().equalsIgnoreCase("A")) {
			j.put("active", true);
			j.put("status", "A");
		} else j.put("status", "S");
				
		j.put("shortCaption", "");
		
		if (ad.getTitle()!=null) {
			int l = ad.getTitle().length();
			if (l>20) j.put("shortCaption", ad.getTitle().substring(0, 20));
			else if (l>2) j.put("shortCaption", ad.getTitle().substring(0, l-1));
			
		} 		
		
		j.put("description", "");
		
		if (ad.getDescription()!=null) {
			int l = ad.getDescription().length();
			if (l>20) j.put("description", ad.getDescription().substring(0, 20));
			else if (l>2) j.put("description", ad.getDescription().substring(0, l-1));
			
		} 

		

		return j;
		
	}
    
}
