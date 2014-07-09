package cr.novatec.crcasas.server.restless;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.google.appengine.api.datastore.GeoPt;


import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

public class AdServerResource extends ServerResource implements AdResource {

	
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	private DecimalFormat formatter = new DecimalFormat("###,###,###");

    @Get
    public Representation retrieve()  {
    	
    	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
    	   String callBack = queryParams.getFirstValue("callback");

    	
    	String ad = (String) this.getRequest().getAttributes().get("anuncio");
    	
    	JSONObject jsonObj = getAd(ad);
    	
    	if (callBack!=null) {
    		
    		//return new JsonRepresentation(jsonObj);
    		
    		return new StringRepresentation(callBack+"("+jsonObj.toString()+")");
    	}
    	
    	return new JsonRepresentation(jsonObj);
          
    };
    

    @Delete
    public Representation delete()  {
    	
       	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
 	    String callBack = queryParams.getFirstValue("callback");
    	
    	String adId = (String) this.getRequest().getAttributes().get("anuncio");
    	String action = (String) this.getRequest().getAttributes().get("action");
    	String key = (String) this.getRequest().getAttributes().get("key");

    	String result = "Not authorized";
    	
    	if (action.equalsIgnoreCase("DELETE")) {
    		
    		Ad ad =  db.getAdBySourceKey(adId);
    		
    		if (ad!=null) {
    			Person person = db.getPersonByAd(ad);
    			
    			if (person!=null) {
    				if (person.getFbId().equals(key)) {
    					
    					db.deleteAd(ad.getId());
    					
    					result = "Deleted";
    				}
    			}
    		}
    		
    	}
    	
    	if (callBack!=null) {
    		
    		return new StringRepresentation(callBack+"("+result+")");
    	}
    	   	
    	return new JsonRepresentation(result);
          
    };
    
    
    @Put
    public Representation pause()  {
    	
       	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
 	    String callBack = queryParams.getFirstValue("callback");

    	
    	String adId = (String) this.getRequest().getAttributes().get("anuncio");
    	String action = (String) this.getRequest().getAttributes().get("action");
    	String key = (String) this.getRequest().getAttributes().get("key");

    	String result = "Not authorized";
    	
    	if (action.equalsIgnoreCase("ACTIVATE") || action.equalsIgnoreCase("PAUSE")) {
    		
    		Ad ad =  db.getAdBySourceKey(adId);
    		
    		if (ad!=null) {
    			Person person = db.getPersonByAd(ad);
    			
    			
    			
    			
    			if (person!=null) {
    				if (person.getFbId().equals(key)) {
    					
    					if (action.equalsIgnoreCase("ACTIVATE")) ad.activate();
    					else ad.stop();
    					
    					result = "Status changed";
    				}
    			}
    		}
    		
    	}
    	
   	if (callBack!=null) {
    		
    		return new StringRepresentation(callBack+"("+result+")");
    	}
 
    	   	
    	return new JsonRepresentation(result);
          
    };
    
    
	
    public JSONObject getAd(String ad) {
    	
      	JSONObject jsonObj = new JSONObject();
    	
    	try {
    		Ad ad2 = db.getAdBySourceKey(ad);
    		jsonObj = toJson(ad2);
    		
    		if (ad2!=null) {
    			Integer vc = ad2.getViewCount();
    			if (vc!=null) {
    				if (vc >= 0) ad2.setViewCount(vc++);
    				else ad2.setViewCount(1);
    			}
    		}
    		
    		db.saveAd(ad2);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return jsonObj;
    }
    
	public JSONObject toJson(Ad ad) throws JSONException {
		
		// imgUrl 
		//   address 
       // price: "US$400,000", 
		// caption: "Linda casa", 
		// name: "Oscar Valverde", 
		// phone: "83949608" 

		
		JSONObject j = new JSONObject();
		
		j.put("adId", ad.getSourceKey() );
		
		j.put("imgUrl", ad.getImageUrl());
		j.put("address", db.getDivision2ById(ad.getCountyId())+ " " + db.getDivision1ById(ad.getStateId()));
		

		String price = ad.getCurrencyFmt()+formatter.format(ad.getPrice().doubleValue());		
		String rentPrice = ad.getCurrencyFmt()+formatter.format(ad.getRentPrice().doubleValue());		
		
	
		if (ad.getSale()) j.put("price", price);
		else j.put("price", rentPrice);
		
		j.put("saleType", ad.getSaleTypeName());
		
		
		j.put("rentPrice", rentPrice);

		j.put("phone", ad.getPhone());
		
		if (ad.getPhone()!=null) j.put("callPhone", ad.getPhone().replaceAll("\\D+",""));
		
		j.put("caption", ad.getTitle());
		
		j.put("state", db.getDivision1ById(ad.getStateId()));
		
		j.put("county", db.getDivision2ById(ad.getCountyId()));
		
		j.put("showName", ad.getShowName());
		j.put("showEmail", ad.getShowEmail());
		j.put("showAddress", ad.getShowAddress());
		j.put("showPhone", ad.getShowPhone());
		
		j.put("showVideo", ad.showVideo());
		j.put("showPhotosyn", ad.showPhotoSyn());
		
		if (ad.getShowName()) j.put("name", ad.getFirstName() + " "+ ad.getLastName());
		if (ad.getShowPhone()) j.put("phone",ad.getPhone());
		if (ad.getShowEmail()) j.put("email", ad.getEmail());
		
		
		
		j.put("listingType", ad.getListingTypeName()); 
		j.put("propertyType", ad.getPropertyType()); 
		
		if (ad.getPropertyType().equals("H")) j.put("isHouse", true);

		
		j.put("propertyTypeName", ad.getPropertyTypeName()); 

		
		j.put("propertySubTypeName",  ad.getPropertySubTypeName()); 
		
		
		j.put("description", ad.getDescription());
		
		j.put("lotSize", ad.getLotSize());
		j.put("frontSize", ad.getFrontSize());

		j.put("constructionSize",  ad.getConstructionSize());
		j.put("bathRooms", ad.getBathRooms());
		j.put("partialBaths", ad.getPartialBaths());
		j.put("bedRooms", ad.getBedRooms());
		j.put("garages", ad.getGarages());
		j.put("floors", ad.getFloors());
		j.put("yearBuild", ad.getYearBuilt());
		
		j.put("pubDate", new SimpleDateFormat("dd/MM/yyyy").format(ad.getPubDate()));



		j.put("city", ad.getCity());
		j.put("residential",ad.getResidential());
		if (ad.getShowAddress()) j.put("address", ad.getAddress());

		GeoPt geo = ad.getGeoPoint();
		
		if (geo!=null) {
			j.put("lat", geo.getLatitude());
			j.put("lng", geo.getLongitude());
			
		} else {
			geo = db.getDivision2Geo(ad.getCountyId());
			
			if (geo!=null) {
				j.put("lat", geo.getLatitude());
				j.put("lng",geo.getLongitude());					
			}
			
		}
		
		// VIDEOS SECTION
		j.put("video01", ad.getVideo01());
		j.put("video02", ad.getVideo02());
		j.put("video03", ad.getVideo03());
		j.put("photosyn01", ad.getPhotosyn01());
		j.put("photosyn02", ad.getPhotosyn02());
		j.put("photosyn03", ad.getPhotosyn03());
		
		j.put("viewCount", ad.getViewCount()) ;
		

		return j;
		
	}
    
}
