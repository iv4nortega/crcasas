package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import com.google.appengine.api.datastore.GeoPt;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

import cr.novatec.crcasas.client.Parameters;
import cr.novatec.crcasas.client.resources.MyClientLibrary;

@Unindexed
public class AdHistory implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
    Long id;
	private @Indexed String listingType; // N=New R=Resell
	private @Indexed String propertyType; // L = Lot B = Building H = Home;
	  	
	private @Indexed Boolean sale;   // Is this property on sale?
	private @Indexed Boolean rent;   // Is this property for rent?
	
    private @Indexed Key<Person> agentKey;
	private @Indexed Key<Person> ownerKey;

    private @Indexed String status; // A = active   E = expired D = deleted; S = Detenido stop
	private Date pubDate; 
	private Date startDate;
	private Date endDate;
	private Date expDate; 
	private Date updateDate;
	private @Indexed Integer source; // nacion (2) crcasas (1) craiglist (3) encuentra24 (4)
	private String sourceKey; 
	private String sourceUrl; 
	private String imageUrl; 
	private String title; 
	private String description; 
	private @Indexed Long price;   // price used for sort operations // i think may be deprecated
	private Boolean localCurrency; // define si se usa el local currency o no
	private Long localPrice;     // currencycode1
	private Long dolPrice;       // currencycode2

	private Long localRentPrice;   //currencycode1    
	private Long dolRentPrice;     // currencycode2    

	private Boolean showName;   // True = show the name in the Ad
	private Boolean showPhone;  // True = show the phone in the Ad
	private Boolean showEmail;  // True = show the email in the Ad
	private Boolean showAddress;  // True = show detail address in the Ad FOR THE FUTURE
	
	
	private String currencyCode1; // Type: COL, USD // i think may be deprecated
	private String currencyCode2; // Type: COL, USD // i think may be deprecated
	
	private Integer viewCount; // cuantas veces ha sido visto el anuncio.
	
    private @Indexed String propertySubType;  // For Home = (H)ouse, (A)partment, (C)ondominum
    private String details;
    private Integer constructionSize;
    private Integer lotSize;
    private Integer bedRooms;
    private Integer bathRooms;
    private Integer partialBaths;
    private Integer yearBuilt;
    private Integer floors;
    private Integer garages;
    
    
	private String streetAddress;
	private String residential;	 // nombre del residencial
	private String address;
	@Indexed private String city;
	@Indexed private Integer districtId;
	@Indexed private Integer countyId;	
	@Indexed private Integer stateId;
	@Indexed private String zipCode;
	@Indexed private String countryId;
	private GeoPt geoPoint;
	private String geoCode; // U = User defined geopoint ; G = Generated;  or N = None 

	private Integer searchPriority;
	private Integer highLight;
	private Integer animation;
	private Integer feedBack;
	private Integer advertisement;
	private Integer emailPriority;
	private Integer smsPriority;

	@Indexed private List<String> keyword; 

    
	public AdHistory() {
	}




	public AdHistory(String listingType, String propertyType, Boolean sale,
			Boolean rent, Key<Person> agentKey, Key<Person> ownerKey,
			String status, Date pubDate, Date startDate, Date endDate,
			Date expDate, Integer source, String sourceKey, String sourceUrl,
			String imageUrl, String title, String description, Long localPrice,
			Long dolPrice, Long localRentPrice, Long dolRentPrice,
			Boolean showName, Boolean showPhone, Boolean showEmail,
			Boolean showAddress, String currencyCode1, String currencyCode2,
			Integer viewCount, String propertySubType, String details,
			Integer lotSize, Integer constructionSize, Integer bedRooms,
			Integer bathRooms, Integer partialBaths, Integer yearBuilt,
			Integer floors, String streetAddress, String residential,
			String city, Integer districtId, Integer countyId, Integer stateId,
			String zipCode, String countryId, GeoPt geoPoint, String geoCode) {
		super();
		this.listingType = listingType;
		this.propertyType = propertyType;
		this.sale = sale;
		this.rent = rent;
		this.agentKey = agentKey;
		this.ownerKey = ownerKey;
		this.status = status;
		this.pubDate = pubDate;
		this.startDate = startDate;
		this.endDate = endDate;
		this.expDate = expDate;
		this.source = source;
		this.sourceKey = sourceKey;
		this.sourceUrl = sourceUrl;
		this.imageUrl = imageUrl;
		this.title = title;
		this.description = description;
		this.localPrice = localPrice;
		this.dolPrice = dolPrice;
		this.localRentPrice = localRentPrice;
		this.dolRentPrice = dolRentPrice;
		this.showName = showName;
		this.showPhone = showPhone;
		this.showEmail = showEmail;
		this.showAddress = showAddress;
		this.currencyCode1 = currencyCode1;
		this.currencyCode2 = currencyCode2;
		this.viewCount = viewCount;
		this.propertySubType = propertySubType;
		this.details = details;
		this.constructionSize = constructionSize;
		this.lotSize = lotSize;
		this.bedRooms = bedRooms;
		this.bathRooms = bathRooms;
		this.partialBaths = partialBaths;
		this.yearBuilt = yearBuilt;
		this.floors = floors;
		this.streetAddress = streetAddress;
		this.residential = residential;
		this.city = city;
		this.districtId = districtId;
		this.countyId = countyId;
		this.stateId = stateId;
		this.zipCode = zipCode;
		this.countryId = countryId;
		this.geoPoint = geoPoint;
		this.geoCode = geoCode;
		
		this.searchPriority=0;
		this.highLight=0;
		this.animation=0;
		this.feedBack=0;
		this.advertisement=0;
		this.emailPriority=0;
		this.smsPriority=0;
		
		// this.price = calculatePrice(Parameters.AD_CURRENCY_US);

		
		
	}

	
	// 		Ad ad = new Ad(source[CODE_IMAGE_SRC], urlString, key, source[CODE_TITLE], 
	//               library.stringToLong(source[CODE_PRECIO]), 
	//               library.stringToDate(source[CODE_PUBDATE]), 
	//               library.sanitizeString(source[CODE_ZONE ]), sale, rent, typeName, country);

	public AdHistory(String imageUrl, String sourceUrl, String sourceKey, Integer source, String description, Long dolPrice, 
			Date pubDate, String zone, Boolean sale, Boolean rent,
			String typeName, String country, String currencyCode2, 
			Integer lotSize, Integer constructionSize, Integer yearBuilt,
			Date updateDate, Long dolRentPrice, String title,
			Integer districtId, Integer countyId, Integer stateId, GeoPt geoPt) {
		this.setImageUrl( imageUrl );
		this.setDescription(description) ;
		this.setSale(sale);
		this.setRent(rent);
		this.setPropertyType(typeName);
		this.setStartDate(pubDate);
		this.setPubDate(pubDate);
		
		System.out.println("Ingresa: "+" Code: " + currencyCode2 + " dolPrice: "+dolPrice+" dolRentPrice: "+dolRentPrice);
		
		if (currencyCode2.equals(Parameters.AD_COUNTRY_CRC)) {
			this.setLocalPrice(dolPrice);
			this.setLocalRentPrice(dolRentPrice);
			this.setCurrencyCode1(currencyCode2);	
			
			this.setLocalCurrency(true);
			
			Float f = dolPrice*Parameters.EXCHANGE_RATE_US;			


			this.setDolPrice(f.longValue());
			
			f = dolRentPrice*Parameters.EXCHANGE_RATE_US;			

			this.setDolRentPrice(f.longValue());
			
			if (! this.getSale()) {
				this.setLocalPrice(0L);
				this.setDolPrice(0L);
			
			}
			
			if (! this.getRent()) {
				this.setLocalRentPrice(0L);
				this.setDolRentPrice(0L);
			
			}
			
			
		} else {
			this.setDolPrice(dolPrice);
			this.setDolRentPrice(dolRentPrice);
			this.setCurrencyCode2(currencyCode2);
			
			this.setLocalCurrency(false);
			
			this.setLocalPrice( dolPrice*Parameters.EXCHANGE_RATE);
						
			this.setDolRentPrice(dolRentPrice*Parameters.EXCHANGE_RATE);
			
			if (! this.getSale()) {
				this.setLocalPrice(0L);
				this.setDolPrice(0L);
			
			}
			
			if (! this.getRent()) {
				this.setLocalRentPrice(0L);
				this.setDolRentPrice(0L);
			
			}

			
		}
		
		System.out.println("Sale: "+" Code: " + currencyCode2 + " localPrice: "+this.getLocalPrice()+" localRentPrice: "+this.getLocalRentPrice()+ " dolPrice: "+this.getDolPrice()+ " dolRentPrice: "+this.getDolRentPrice());

		
		this.setCountryId(country);
		this.setAddress(zone);
		
		this.setStateId(MyClientLibrary.findState(zone));
		
		this.setSourceUrl(sourceUrl);
		this.setSourceKey(sourceKey);
		this.setSource(source);
		this.setCurrencyCode2(currencyCode2);
		this.setLotSize(lotSize);
		this.setConstructionSize(constructionSize);
		this.setYearBuilt(yearBuilt);
		this.setUpdateDate(updateDate);
		this.setTitle(title);
		
		this.setDistrictId(districtId);
		this.setCountyId(countyId);
		this.setStateId(stateId);
		
		this.setGeoPoint(geoPt);
		
		// U = User defined geopoint ; G = Generated;  or N = None
		if (geoPt==null) this.geoCode = "N" ;
		else this.geoCode = "U"; 
		
		//this.price = calculatePrice(Parameters.AD_CURRENCY_US);

	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public String getListingType() {
		return listingType;
	}



	public void setListingType(String listingType) {
		if (listingType==null) this.listingType="R";
		else this.listingType = listingType;
	}



	public Boolean getLocalCurrency() {
		return localCurrency;
	}




	public void setLocalCurrency(Boolean localCurrency) {
		this.localCurrency = localCurrency;
	}




	public Long getPrice() {
		
		if (getSale()) {
			return getDolPrice();
		}
		if (getRent()) {
			return getDolRentPrice();
		}				
		return getDolPrice();
	}


	public String getPriceFmt() {
    	return this.getPrice().toString() ;		
	}

	public void setPrice(Long price) {
		if (this.price==null) this.price=0L;
		this.price = price;
	}




	public Boolean getSale() {
		return sale;
	}



	public void setSale(Boolean sale) {
		if (sale==null) this.sale=true;
		else this.sale = sale;
	}



	public Boolean getRent() {
		return rent;
	}



	public void setRent(Boolean rent) {
		if (rent==null) this.rent=false;
		else this.rent = rent;
	}



	public Key<Person> getAgentKey() {
		return agentKey;
	}



	public void setAgentKey(Key<Person> agentKey) {
		this.agentKey = agentKey;
	}






	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		if (status==null) this.status=Parameters.AD_STATUS_ACTIVE;
		else this.status = status;
	}



	public Date getPubDate() {
		return pubDate;
	}



	public void setPubDate(Date pubDate2) {
		this.pubDate = pubDate2;
	}



	public Date getExpDate() {
		return expDate;
	}



	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}



	public int getSource() {
		if (source==null) return 0;
		return source;
	}


	public void setSource(Integer source) {
		this.source = source;
	}


	public String getSourceString() {
		if ( (source>=0) && (source<=5)) 
			return "Fuente: "+Parameters.SOURCE_NAMES[source];

		return "";
	}
	
	public String getSourceKey() {
		return sourceKey;
	}



	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}



	public String getSourceUrl() {
		return sourceUrl;
	}



	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}



	public String getImageUrl() {
		return imageUrl;
	}



	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}



	public String getTitle() {
		if (title==null) return "";
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getDescription() {
		if (description==null) return "";
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Long getLocalPrice() {
		if (localPrice==null) return 0L;
		return localPrice;
	}



	public void setLocalPrice(Long localPrice) {
		if (localPrice==null) this.localPrice=0L;
		else this.localPrice = localPrice;
	}



	public Long getDolPrice() {
		if (dolPrice==null) return 0L;
		return dolPrice;
	}



	public void setDolPrice(Long dolPrice) {
		if (dolPrice==null) this.dolPrice=0L;
		else this.dolPrice = dolPrice;
	}





	public Boolean getShowName() {
		return showName;
	}





	public void setShowName(Boolean showName) {
		if (showName==null) this.showName=false;
		else this.showName = showName;
	}





	public Boolean getShowPhone() {
		return showPhone;
	}





	public void setShowPhone(Boolean showPhone) {
		if (showPhone==null) this.showPhone=false;
		else this.showPhone = showPhone;
	}





	public Boolean getShowEmail() {
		return showEmail;
	}





	public void setShowEmail(Boolean showEmail) {
		if (showEmail==null) this.showEmail=false;
		else this.showEmail = showEmail;
	}





	public Long getLocalRentPrice() {
		if (localRentPrice==null) return 0L;
		return localRentPrice;
	}





	public void setLocalRentPrice(Long localRentPrice) {
		if (localRentPrice==null) this.localRentPrice=0L;
		else this.localRentPrice = localRentPrice;

	}





	public Long getDolRentPrice() {
		if (dolRentPrice==null) return 0L;
		return dolRentPrice;
	}





	public void setDolRentPrice(Long dolRentPrice) {
		if (dolRentPrice==null) this.dolRentPrice=0L;
		else this.dolRentPrice = dolRentPrice;

	}





	public Date getStartDate() {
		return startDate;
	}





	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}





	public Date getEndDate() {
		return endDate;
	}





	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}


	public String getCurrencyCode1() {
		if (currencyCode1 == null) this.currencyCode1 = "CRC";
		return currencyCode1;
	}





	public void setCurrencyCode1(String currencyCode1) {
		if (currencyCode1 == null) this.currencyCode1 = "CRC"; // http://www.xe.com/iso4217.php
		else this.currencyCode1 = currencyCode1;
	}





	public String getCurrencyCode2() {
		if (currencyCode2 == null) this.currencyCode2 = "USD";
		return currencyCode2;
	}





	public void setCurrencyCode2(String currencyCode2) {
		if (currencyCode2 == null) this.currencyCode2 = "USD"; // http://www.xe.com/iso4217.php
		else this.currencyCode2 = currencyCode2;
	}





	public String getPropertyType() {
		return propertyType;
	}





	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}





	public Boolean getShowAddress() {
		return showAddress;
	}





	public void setShowAddress(Boolean showAddress) {
		this.showAddress = showAddress;
	}





	public Integer getViewCount() {
		return viewCount;
	}





	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}




	public Key<Person> getOwnerKey() {
		return ownerKey;
	}




	public void setOwnerKey(Key<Person> ownerKey) {
		this.ownerKey = ownerKey;
	}




	public String getPropertySubType() {
		return propertySubType;
	}




	public void setPropertySubType(String propertySubType) {
		this.propertySubType = propertySubType;
	}




	public String getDetails() {
		return details;
	}




	public void setDetails(String details) {
		this.details = details;
	}




	public Integer getConstructionSize() {
		if (constructionSize==null) this.constructionSize = 0;

		return constructionSize;
	}

	public String getConstructionSizeString() {
		if (getConstructionSize()>0) return "Cons: " + getLotSize() + "m";
		return "";
	}



	public void setConstructionSize(Integer constructionSize) {
		this.constructionSize = constructionSize;
	}




	public Integer getLotSize() {
		if (lotSize==null) this.lotSize = 0;

		return lotSize;
	}

	
	public String getLotSizeString() {
		if (getLotSize()>0) return "Lote: " + getLotSize() + "m";
		return "";
	}



	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}




	public Integer getBedRooms() {
		if (bedRooms==null) this.bedRooms = 0;

		return bedRooms;
	}


	public String getBedRoomsString() {
		if (getBedRooms()>0) return "Hab: " + getBedRooms();
		return "";
	}



	public void setBedRooms(Integer bedRooms) {
		this.bedRooms = bedRooms;
	}




	public Integer getBathRooms() {
		if (bathRooms==null) this.bathRooms = 1;

		return bathRooms;
	}


	public String getBathRoomsString() {
		if (getBathRooms()>0) return "Banos: " + getBathRooms();
		return "";
	}


	public void setBathRooms(Integer bathRooms) {
		this.bathRooms = bathRooms;
	}




	public Integer getPartialBaths() {
		if (partialBaths==null) this.partialBaths = 0;

		return partialBaths;
	}




	public void setPartialBaths(Integer partialBaths) {
		this.partialBaths = partialBaths;
	}




	public Integer getYearBuilt() {
		if (yearBuilt==null) this.yearBuilt = 0;

		return yearBuilt;
	}




	public void setYearBuilt(Integer yearBuilt) {

		this.yearBuilt = yearBuilt;
	}




	public Integer getFloors() {
		if (floors==null) this.floors = 1;

		return floors;
	}




	public void setFloors(Integer floors) {

		this.floors = floors;
	}




	public String getStreetAddress() {
		return streetAddress;
	}




	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}




	public String getResidential() {
		return residential;
	}




	public void setResidential(String residential) {
		this.residential = residential;
	}




	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	public Integer getDistrictId() {
		return districtId;
	}




	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}




	public Integer getCountyId() {
		return countyId;
	}




	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}




	public Integer getStateId() {
		return stateId;
	}




	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}




	public String getZipCode() {
		return zipCode;
	}




	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}




	public String getCountryId() {
		return countryId;
	}




	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}


	public GeoPt getGeoPoint() {
		return geoPoint;
	}


	public void setGeoPoint(GeoPt geoPoint) {
		this.geoPoint = geoPoint;
	}




	public String getGeoCode() {
		return geoCode;
	}




	public void setGeoCode(String geoCode) {
		this.geoCode = geoCode;
	}



	public Date getUpdateDate() {
		return updateDate;
	}




	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}




	public String getAddress() {
		return address;
	}


	public String getShortAddress() {
		if (address != null && address.length()>14)
		return address.substring(0, 14);
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}
	
	public Long calculatePrice(String currency) {
		
		if ( currency.equals(Parameters.AD_CURRENCY_US) 
			 &&  this.getCurrencyCode1().equals(Parameters.AD_CURRENCY_US) ) {
			 if (this.getSale()) return choosePrice(this.getLocalPrice(), this.getDolPrice(), Parameters.EXCHANGE_RATE.floatValue() );
			 else  return choosePrice(this.getLocalRentPrice(), this.getDolRentPrice(), Parameters.EXCHANGE_RATE.floatValue() );			
		}
	
		if ( currency.equals(Parameters.AD_CURRENCY_US) 
				 &&  this.getCurrencyCode2().equals(Parameters.AD_CURRENCY_US) ) {
				 if (this.getSale()) return choosePrice(this.getDolPrice(), this.getLocalPrice(), Parameters.EXCHANGE_RATE.floatValue() );
				 else  return choosePrice(this.getDolRentPrice(), this.getLocalRentPrice(), Parameters.EXCHANGE_RATE.floatValue() );			
		}
		
		if ( currency.equals(Parameters.AD_CURRENCY_CRC) 
				 &&  this.getCurrencyCode1().equals(Parameters.AD_CURRENCY_CRC) ) {
				 if (this.getSale()) return choosePrice(this.getLocalPrice(), this.getDolPrice(), Parameters.EXCHANGE_RATE_US );
				 else  return choosePrice(this.getLocalRentPrice(), this.getDolRentPrice(), Parameters.EXCHANGE_RATE_US );			
			}

		
		if ( currency.equals(Parameters.AD_CURRENCY_CRC) 
				 &&  this.getCurrencyCode2().equals(Parameters.AD_CURRENCY_CRC) ) {
				 if (this.getSale()) return choosePrice(this.getDolPrice(), this.getLocalPrice(), Parameters.EXCHANGE_RATE_US );
				 else  return choosePrice(this.getDolRentPrice(), this.getLocalRentPrice(), Parameters.EXCHANGE_RATE_US );			
		}

	
		return 0L;	
			
	}
	
	public Long choosePrice(Long long1, Long long2, Float exchange) {
		if ((long1==null) && (long2==null) ) return 0L;
		
		if ((long1 == null || long1 < 1) &&  (long2==null) ) return 0L;

		if ((long2 == null || long2 < 1) &&  (long1==null) ) return 0L;

		if ((long1 == null || long1 < 1) ) {
			Float result = long2 * exchange;
			return result.longValue();
		} else return long1;
		

	}




	public Integer getGarages() {
		if (garages==null) this.garages = 0;
		return garages;
	}


	public String getGarageString() {
		if (getGarages()>=0) return "Garajes: " + getGarages();
		return "";
	}


	public void setGarages(Integer garages) {
		this.garages = garages;
	}
	
	public String showPrice() {
		return "showprice";
	}
	
	public int showPriceAsInt() {

		Long l = 0L;
    	if (this.getSale()) {    	
      	       		 
    		if (this.getCurrencyCode1().equals(Parameters.AD_CURRENCY_CRC) && (this.getLocalPrice() != null)) l =   this.getLocalPrice()/Parameters.EXCH_RATE ;
    		if (this.getCurrencyCode1().equals(Parameters.AD_CURRENCY_US) && (this.getLocalPrice() != null)) l =  this.getLocalPrice() ;
    		
       		if (this.getCurrencyCode2().equals(Parameters.AD_CURRENCY_CRC) && (this.getDolPrice() != null)) l =  this.getDolPrice()/Parameters.EXCH_RATE ;
    		if (this.getCurrencyCode2().equals(Parameters.AD_CURRENCY_US) && (this.getDolPrice() != null)) l =  this.getDolPrice() ;		    		
    	}
 
    	if (this.getRent()) {   
       	 		
    		if (this.getCurrencyCode1().equals(Parameters.AD_CURRENCY_CRC) && (this.getLocalRentPrice() != null)) l = this.getLocalRentPrice()/Parameters.EXCH_RATE ;
    		if (this.getCurrencyCode1().equals(Parameters.AD_CURRENCY_US) && (this.getLocalRentPrice() != null)) l =  this.getLocalRentPrice() ;
    		
       		if (this.getCurrencyCode2().equals(Parameters.AD_CURRENCY_CRC) && (this.getDolRentPrice() != null)) l =  this.getDolRentPrice()/Parameters.EXCH_RATE ;
    		if (this.getCurrencyCode2().equals(Parameters.AD_CURRENCY_US) && (this.getDolRentPrice() != null)) l =  this.getDolRentPrice() ;		    		
    	}		
    	
		return l.intValue();
	}
	
	
	public String showDivision1() {
		int div1 = this.getStateId();
		if (div1 >=1 && div1 <= 7) return this.getShortAddress()+", "+Parameters.DIVISION1_NAMES[div1-1];
		return "";
		
	}
	
	public String getSourceName(){
		
		
		if (source!=null)
			if ( (source>=0) && (source<=5)) return Parameters.SOURCE_NAMES[source];
		return "";
		
	}
	
	public String getShortDescription(){
		
		
		if (propertyType.equals(Parameters.PROPERTY_PROPERTYTYPE_LOT)) {
			
			return getLotSizeString() + "" + getSourceString();
			
		}
		
		return        getConstructionSizeString() + 
				" " + getLotSizeString() +
				" " + getBedRoomsString() +
				" " + getBathRoomsString() +
				" " + getGarageString() +				
				" " + getSourceString();
		
	}
	
	public String getMakerInfo() {
		
		return "<p><img alt='No hay imagen' src="
				+ "'"+getImageUrl()+ "'" 
				+ "style='width: 80px; height: 60px; ' />"
				+ getAddress()+" "+ getPriceFmt()+ "</p>"
				+ "<p>" + getShortDescription() + "</p>"
				+ "<p>" + "<a href='"+ getSourceUrl()+"' target='_blank'>ir...</a></p>"
				;
		
		
	}
	
}
