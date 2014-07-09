package cr.novatec.crcasas.server.servlet;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.GeoPt;

import cr.novatec.crcasas.client.Parameters;
import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.Division2;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")
public class AdFormServlet extends HttpServlet {


		DataBaseServicesImpl db = new DataBaseServicesImpl();

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
			
			 String listingType = req.getParameter("listingTypeAd");
			 String propertyType = req.getParameter("propertyTypeAd");

			 String propertySubType = req.getParameter("propertySubTypeAd");

			 String sale = req.getParameter("saleAd");
			 String phone = req.getParameter("phoneAd");
			 String email = req.getParameter("emailAd");
			 
			
			 String firstName = req.getParameter("firstNameAd");
			 String lastName = req.getParameter("lastNameAd");
			 String companyName = req.getParameter("companyNameAd");
			 
			 String title = req.getParameter("titleAd");
			 String description = req.getParameter("descriptionAd");
			 String localCurrency = req.getParameter("localCurrencyAd");
			 String price = req.getParameter("priceAd");

			 String showName = req.getParameter("showNameAd");
			 String showPhone = req.getParameter("showPhoneAd");
			 String showEmail = req.getParameter("showEmailAd");

			 String constructionSize = req.getParameter("constructionSizeAd");
			 String lotSize = req.getParameter("lotSizeAd");
			 String frontSize = req.getParameter("frontSizeAd");
			 
			 String bedRooms = req.getParameter("bedRoomsAd");
			 String bathRooms = req.getParameter("bathRoomsAd");
			 String partialBaths = req.getParameter("partialBathsAd");
			 String yearBuilt = req.getParameter("yearBuiltAd");
			 String floors= req.getParameter("floorsAd");
			 String garages = req.getParameter("garagesAd");

			 String address = req.getParameter("addressAd");
			 String residential = req.getParameter("residentialAd");
			 String city = req.getParameter("cityAd");
			 String countyId = req.getParameter("countyIdAd");
			 String stateId = req.getParameter("stateIdAd");

			 String video01= req.getParameter("video01Ad");

			 String includeMap= req.getParameter("loadMap");
			 String latAd= req.getParameter("latAd");
			 String lngAd = req.getParameter("lngAd");
			 
			 Integer numPhotos = 0;
			 
			 try {
				  numPhotos = Integer.parseInt(req.getParameter("numPhotosAd"));

			 } catch(NumberFormatException e){
				numPhotos = 0;
			 };


			 Integer count = numPhotos;
			 
			 String photosKey = " ";
			 
			 
		     List<Long> imageList = new ArrayList<Long>();
			 
			 
			 while (count>0) {
				 photosKey = photosKey + "photosKey: "+ req.getParameter("photosAd"+count) + "<br>";
				 imageList.add(db.getImageIdByImageKey(req.getParameter("photosAd"+count)));
				 count--;
				 

			 };


			 String adType = req.getParameter("adTypeAd");
			 String sourceKey = req.getParameter("sourceKeyAd");
			 String status = req.getParameter("statusAd");
			 String fbId = req.getParameter("fbIdAd");
			 
			 String agent = req.getParameter("agentAd");
			 
        	
			 Ad ad = saveAd(listingType, 
						 propertyType, 
						 propertySubType, 
						 sale, 
						 phone, 
						 email, 
						 firstName, 
						 lastName, 				 
						 companyName,						 
						 title, 
						 description,
						 localCurrency,
						 price,
						 showName,
						 showPhone,
						 showEmail,
						 constructionSize,
						 lotSize,
						 frontSize,
						 bedRooms,
						 bathRooms,
						 partialBaths,
						 yearBuilt,
						 floors,
						 garages,
						 address,
						 residential,
						 city,
						 countyId,
						 stateId,
						 video01,
						 includeMap,
						 latAd,
						 lngAd,
						 adType,
						 sourceKey,
						 status,
						 agent,
						 fbId);

        	if (sourceKey!=null) {
        		
        		int imagePosition = numPhotos;
                for (Long imageId : imageList)
                {
                 db.saveImageAdId(imageId, ad.getId(), imagePosition);
                 imagePosition--;
                }
        		
        	} else {
            	db.sentEmail( "Problemas con el ingreso de un anuncio" , firstName+" "+lastName );           	

        	}
        	
        	res.setContentType("text/html"); 
		    
		    res.getWriter().println(ad.getSourceKey());

		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
		    resp.setHeader("Content-Type", "text/html");
		    resp.getWriter().println("Hello");
		   
		    
        	
		}
		
		protected Ad saveAd(String listingType, 
				 				String propertyType, 
				 				String propertySubType, 
				 				String sale, 
				 				String phone, 
				 				String email, 
				 				String firstName, 
				 				String lastName, 				 
				 				String companyName,				 				
				 				String title, 
				 				String description,
				 				String localCurrency,
				 				String price,
				 				String showName,
				 				String showPhone,
				 				String showEmail,
				 				String constructionSize,
				 				String lotSize,
				 				String frontSize,
				 				String bedRooms,
				 				String bathRooms,
				 				String partialBaths,
				 				String yearBuilt,
				 				String floors,
				 				String garages,
				 				String address,
				 				String residential,
				 				String city,
				 				String countyId,
				 				String stateId,
				 				String video01,
				 				String includeMap,
				 				String latAd,
				 				String lngAd,
				 				String adType,
				 				String sourceKey,
				 				String status,
				 				String agent,
				 				String fbId)
				{
			
					Ad ad = db.getAdBySourceKey(sourceKey);
					
					 ad.setListingType(listingType);
					 ad.setPropertyType(propertyType);
					ad.setPropertySubType(propertySubType);
					
					ad.setSale(stringToBoolean(sale));
					ad.setRent(!ad.getSale());
					
					ad.setPhone(phone);
					ad.setEmail(email);
					ad.setFirstName(firstName);
					ad.setLastName(lastName);
					ad.setCompanyName(companyName);
					
					ad.setTitle(title);
					ad.setDescription(description);
					
					ad.setLocalCurrency(stringToBoolean(localCurrency));
					
					if (ad.getLocalCurrency()) {
						
						if (ad.getSale() ) ad.setLocalPrice(stringToLong(price));
						else ad.setLocalRentPrice(stringToLong(price));
						
					} else {

						if (ad.getSale() ) ad.setDolPrice(stringToLong(price));
						else ad.setDolRentPrice(stringToLong(price));

					}
					
					ad.setShowName(stringToBoolean(showName));
					ad.setShowPhone(stringToBoolean(showPhone));
					ad.setShowEmail(stringToBoolean(showEmail));
					
					ad.setConstructionSize(stringToInteger(constructionSize));
					
					ad.setLotSize(stringToInteger(lotSize));
					
					ad.setLotSize(stringToInteger(lotSize));
					
					ad.setFrontSize(stringToInteger(frontSize));
					
					ad.setBedRooms(stringToInteger(bedRooms));
					
					ad.setBathRooms(stringToInteger(bathRooms));
					
					ad.setPartialBaths(stringToInteger(partialBaths));
					
					ad.setYearBuilt(stringToInteger(yearBuilt));
					
					ad.setFloors(stringToInteger(floors));
					
					ad.setGarages(stringToInteger(garages));
					
					ad.setAddress(address);
		
					ad.setResidential(residential);
					ad.setCity(city);
					
					Integer countyId2 = stringToInteger(countyId);
					ad.setCountyId(countyId2);

					Integer stateId2 = stringToInteger(stateId);					
					ad.setStateId(stateId2);
					
					if (!isDivision2Valid(countyId2, stateId2)) ad.setCountyId(getFirstDivision2(stateId2));
					
					ad.setVideo01(video01);
					
					Boolean map = stringToBoolean(includeMap);
					
					if (map) {
						
						Float la = stringToFloat(latAd);
						Float lo = stringToFloat(lngAd);
						
						if (Math.abs(la) < 1) map = false;
						if (Math.abs(lo) < 1) map = false;
						
						if (map) {
						
						ad.setGeoCode(Parameters.PROPERTY_GEOCODE_USERDEFINED);
						ad.setGeoPoint(new GeoPt(la, lo));				
						
						}
					}; 
					
					
					if (!map) {
						GeoPt geoPt = null;
						Division2 d2 = new Division2();
						d2 = db.getDivision2ByKey(stringToInteger(countyId));
						if (d2!=null) geoPt = d2.getGeoPoint();
						if (geoPt!=null) ad.setGeoCode(Parameters.PROPERTY_GEOCODE_GENERATED);						
						else ad.setGeoCode(Parameters.PROPERTY_GEOCODE_NONE);
					};					
					
					ad.setAdType(adType);					
					ad.setStatus(status);															
					ad.setUpdateDate(new Date());						
					
					db.saveAd(ad);
					
					return ad;									
				}

	protected Integer stringToInteger(String s) {
		
		Integer result = 0;
		
		if (s==null) return result;
		
		try 
		{
			result = Integer.parseInt(s);
		}
		
		catch( NumberFormatException e)
		{
			result = 0;
		};
		
		return result;
				
	};
	
	protected Long stringToLong(String s) {
		
		Long result = 0l;
		
		if (s==null) return result;

		
		try 
		{
			//result = Long.parseLong(s);
			result = Long.parseLong(s);
		}
		
		catch( NumberFormatException e)
		{
			result = 0l;
		};
		
		return result;
				
	};

	
	protected Float stringToFloat(String s) {
		
		Float result = 0f;
		
		if (s==null) return result;

		
		try 
		{
			result = Float.parseFloat(s);
		}
		
		catch( NumberFormatException e)
		{
			result = 0f;
		};
		
		return result;
				
	}
	
	
	protected Boolean stringToBoolean(String s) {
		
		Boolean result = false;
		if (s==null) return result;
		
		if (s.toUpperCase().equals("TRUE") ) result = true;
		if (s.toUpperCase().equals("ON") ) result = true;
				
		return result;
				
	}
	
	protected Person getPersonFromFbId(String fbId) {
		return null;
	}
	
	protected Boolean isDivision1Valid(Integer division1) {
		if (division1 == null ) return false;
		if ( division1 < 1 || division1 > 7 ) return false;

		return true;
	}
	
	protected Boolean isDivision2Valid(Integer division2, Integer division1) {
		if (division2 == null ) return false;
		if ( division2 < 101 ) return false;
		if ( isDivision1Valid(division1 ) ){
			if (division2 < division1*100) return false;
			if (division2 > (division1+1)*100) return false;
			
		} else return false;
		return true;
	}
	
	protected Integer getFirstDivision2(Integer division1) {
		if (isDivision1Valid(division1)) return division1*100+1;
		else return 101;
	}
}
