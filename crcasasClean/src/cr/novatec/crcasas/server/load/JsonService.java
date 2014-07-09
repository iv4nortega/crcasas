package cr.novatec.crcasas.server.load;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Query;
import com.googlecode.objectify.annotation.Indexed;

import cr.novatec.crcasas.client.Parameters;
import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.server.resources.MyServerLibrary;
import cr.novatec.crcasas.server.rpc.DAO;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")



public class JsonService extends HttpServlet  {
	

	private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	private final DAO dao = new DAO();

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {	
		
		
		JSONObject responseObj = new JSONObject();
		List<JSONObject> productObjects = new LinkedList<JSONObject>();

		Query<Ad> query =  dao.ofy().query(Ad.class);
		
		Ad ad;

		QueryResultIterator<Ad> iterator = query.iterator();
		
		
		while (iterator.hasNext() ) {
			ad = iterator.next();				
			if (ad.getGeoPoint()!=null) {
				JSONObject productObj = new JSONObject();
				try {
					
					productObj.put("listingType", ad.getListingType()); // N=New R=Resell
					productObj.put("propertyType", ad.getPropertyType()); // L = Lot B = Building H = Home;
					  	
					productObj.put("sale", ad.getSale());   // Is this property on sale?
					productObj.put("rent", ad.getRent());   // Is this property for rent?
					
					productObj.put("status", ad.getStatus()); // A = active   E = expired D = deleted; S = Detenido stop
					productObj.put("source", ad.getSource()); // nacion (2) crcasas (1) craiglist (3) encuentra24 (4)
					productObj.put("sourceKey", ad.getSourceKey()); 
					productObj.put("sourceUrl", ad.getSourceUrl()); 
					productObj.put("imageUrl", ad.getImageUrl()); 
					productObj.put("title", ad.getTitle()); 
					productObj.put("description", ad.getDescription()); 
					productObj.put("price", ad.getPrice());   // price used for sort operations // i think may be deprecated

					
					productObj.put("propertySubType", ad.getPropertySubType());  // For Home = (H)ouse, (A)partment, (C)ondominum
					productObj.put("details", ad.getDetails());
					productObj.put("constructionSize", ad.getConstructionSize());
					productObj.put("lotSize", ad.getLotSize());
					productObj.put("bedRooms", ad.getBedRooms());
					productObj.put("bathRooms", ad.getBathRooms());
					productObj.put("partialBaths", ad.getPartialBaths());
					productObj.put("yearBuilt", ad.getYearBuilt());
					productObj.put("floors", ad.getFloors());
					productObj.put("garages", ad.getGarages());
				    
				    
					productObj.put("streetAddress", ad.getStreetAddress());
					productObj.put("residential", ad.getResidential());	 // nombre del residencial
					productObj.put("address", ad.getAddress());
					productObj.put("city", ad.getCity());
					productObj.put("districtId", ad.getDistrictId());
					productObj.put("countyId", ad.getCountyId());	
					productObj.put("stateId", ad.getStateId());
					productObj.put("zipCode", ad.getZipCode());
					productObj.put("countryId", ad.getCountryId());
					productObj.put("geoPoint", ad.getGeoPoint());
					productObj.put("geoCode", ad.getGeoCode()); // U = User defined geopoint ; G = Generated;  or N = None 


					productObjects.add(productObj);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}			
		}	
		
		try {
			responseObj.put("ads", productObjects);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter writer = resp.getWriter();
	    writer.write(responseObj.toString());
		writer.flush();
		

        
	 }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
	 doGet(req, resp);
	 }	 
	
		
}	