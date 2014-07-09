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


import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

public class AdFormResource extends ServerResource implements AdResource {

	
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();


    @Get
    public Representation retrieve()  {
    	
    	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
 	   String callBack = queryParams.getFirstValue("callback");

    	
    	String ad = (String) this.getRequest().getAttributes().get("anuncio");

    	JSONObject jsonObj = new JSONObject();
    	    	
    	try {
    		jsonObj = toJson(db.getAdBySourceKey(ad));

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	if (callBack!=null) {    		
    		return new StringRepresentation(callBack+"("+jsonObj.toString()+")");
    	}
   	
    	
    	return new JsonRepresentation(jsonObj);
          
    }
	
	public JSONObject toJson(Ad ad) throws JSONException {
		
		// imgUrl 
		//   address 
       // price: "US$400,000", 
		// caption: "Linda casa", 
		// name: "Oscar Valverde", 
		// phone: "83949608" 

		
		JSONObject j = new JSONObject();
		
		j.put("adIdAd", ad.getId() );
		
		
		if (ad.getSale()) j.put("saleAd", "true");
		else j.put("saleAd", "false");

		j.put("propertyTypeAd", ad.getPropertyType()); 
		j.put("propertySubTypeAd",  ad.getPropertySubTypeName()); 

		j.put("listingTypeAd", ad.getListingTypeName()); 

		j.put("firstNameAd", ad.getFirstName()); 

		j.put("lastNameAd", ad.getLastName()); 
		
		if (ad.getIsAgent()) j.put("agentAd", "TRUE");
		else j.put("agentAd", "FALSE");
		
		j.put("phoneAd", ad.getPhone()); 
		
		j.put("emailAd", ad.getEmail()); 

		j.put("stateIdAd", ad.getStateId()); 

		j.put("countyIdAd", ad.getCountyId()); 

		j.put("cityAd", ad.getCity()); 

		j.put("residentialAd", ad.getResidential()); 

		j.put("addressAd", ad.getStreetAddress());

		j.put("lotSizeAd", ad.getLotSize());

		j.put("frontSizeAd", ad.getFrontSize());

		j.put("constructionSizeAd", ad.getConstructionSize());
		
		j.put("bedRoomsAd", ad.getBedRooms());

		j.put("bathRoomsAd", ad.getBathRooms());

		j.put("partialBathsAd", ad.getPartialBaths());

		j.put("floorsAd", ad.getFloors());
		
		j.put("garagesAd", ad.getGarages());

		j.put("yearBuiltAd", ad.getYearBuilt());
		
		if (ad.getLocalCurrency()) j.put("localCurrencyAd", "TRUE");
		else j.put("localCurrencyAd", "FALSE");

		if (ad.getSale()) {
			if (ad.getLocalCurrency()) j.put("priceAd", ad.getLocalPrice());
			else j.put("priceAd", ad.getDolPrice());			
		} else {
			if (ad.getLocalCurrency()) j.put("priceAd", ad.getLocalRentPrice());
			else j.put("priceAd", ad.getDolRentPrice());			
		};
		
		j.put("titleAd", ad.getTitle());
		j.put("descriptionAd", ad.getDescription());

		
		if (ad.getShowPhone()) j.put("showPhoneAd", "TRUE");
		else j.put("showPhoneAd", "FALSE");

		if (ad.getShowName()) j.put("showNameAd", "TRUE");
		else j.put("showNameAd", "FALSE");

		if (ad.getShowEmail()) j.put("showEmailAd", "TRUE");
		else j.put("showEmailAd", "FALSE");

		
		
		GeoPt geo = ad.getGeoPoint();
		
		if (geo!=null) {
			j.put("latAd", geo.getLatitude());
			j.put("lngAd", geo.getLongitude());
			
			j.put("loadMap", "TRUE");
			
		} else {
			
			j.put("loadMap", "FALSE");
			geo = db.getDivision2Geo(ad.getCountyId());
			
			if (geo!=null) {
				j.put("latAd", geo.getLatitude());
				j.put("lngAd",geo.getLongitude());					
			}
			
		};
		
		// VIDEOS SECTION
		j.put("video01Ad", ad.getVideo01());
		
		j.put("viewCountAd", ad.getViewCount()) ;
				
		// HIDDEN SECTION
				
		j.put("adTypeAd", ad.getAdType()) ;
		j.put("statusAd", ad.getStatus()) ;
		

		return j;
		
	}
    
}
