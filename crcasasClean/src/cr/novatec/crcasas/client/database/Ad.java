package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;


import com.google.appengine.api.datastore.GeoPt;

//import java.text.NumberFormat; 
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;

import cr.novatec.crcasas.client.Parameters;
import cr.novatec.crcasas.client.resources.MyClientLibrary;


public class Ad implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
    Long id;
	private @Indexed String listingType; // N=New R=Resell
	private @Indexed String propertyType; // L = Lot B = Building H = Home C= Comercial;
	  	
	private @Indexed Boolean sale;   // Is this property on sale?
	private @Indexed Boolean rent;   // Is this property for rent?
	
    private @Indexed Key<Person> agentKey;
	private @Indexed Key<Person> ownerKey;

	private String firstName;
	private String lastName;
	private String phone;
	private @Indexed String email;
	private String companyName;
	
    private @Indexed String status; 
	 // A = active   E = expired D = deleted; S = Detenido stop P= Pendiente de Conf C= Confirmado  N=Nuevo Z=New withoutsave Y=new been using.
    private Boolean confirmationSent;
    private Date confirmationSentDate;
    private Integer confirmationKey;


	private Date pubDate; 
	private @Indexed Date startDate;
	private Date endDate;
	private Date expDate; 
	private @Indexed Date creationDate; 
	
	private @Indexed Date updateDate;
	private @Indexed Integer source; // nacion (2) crcasas (1) craiglist (3) encuentra24 (4)
	private @Indexed String sourceKey; 
	private String sourceUrl; 
	private String imageUrl; 
	
	private @Indexed Boolean hasImage;
		
	private Boolean isPortrait;
	
	private String title; 
	private String description; 
	private @Indexed Long price;   // price used for sort operations // i think may be deprecated
	private Boolean localCurrency; // define si se usa el local currency o no
	private @Indexed Long localPrice;     // currencycode1
	private @Indexed Long dolPrice;       // currencycode2

	private @Indexed Long localRentPrice;   //currencycode1    
	private @Indexed Long dolRentPrice;     // currencycode2    

	private Boolean showName;   // True = show the name in the Ad
	private Boolean showPhone;  // True = show the phone in the Ad
	private Boolean showEmail;  // True = show the email in the Ad
	private Boolean showAddress;  // True = show detail address in the Ad FOR THE FUTURE
	
	
	private String currencyCode1; // Type: COL, USD // i think may be deprecated
	private String currencyCode2; // Type: COL, USD // i think may be deprecated
	
	private Integer viewCount; // cuantas veces ha sido visto el anuncio.
	
    private @Indexed String propertySubType;  // For Home = (H)ouse, (A)partment, (C)ondominum (B) Habitaciones (V) Vacaciones
                                              // For Lots = (L) Lote (F) Finca, (C) Lotes comerciales
    										  // For comercial =  (B) Edificios (W) Bodegas (V) Bovedas (O) Oficinas
    private String details;
    private Integer constructionSize;
    private Integer lotSize;
    private Integer frontSize;



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

	private String adType;

	
	private String photosyn01;
	private String photosyn02;
	private String photosyn03;

	private String video01;
	private String video02;
	private String video03;
	
	
	private Boolean premium;
	private Integer category; //  1 = basico  2 etc
	private Date startPayment;  // fecha del primer pago
	private Date nextPayment;  // fecha del siguiente pago
	private Integer paymentSatus; // 0 = pagado 1 = pending 2 = overdue 

	private Boolean markToDelete;
	
	@Indexed private List<String> keyword; 

	private Boolean isAgent;
	
	private Boolean firstContact;

	private Boolean hasPost; //  This Ad has a Facebook Post

	private Integer postId; //  Facebook PostId
	private Integer postCount; //  Facebook Post Count
	
	
	public Boolean getFirstContact() {
		return firstContact;
	}

	public void setFirstContact(Boolean firstContact) {
		this.firstContact = firstContact;
	}

	public Ad() {
		
		this.creationDate = new Date();
		this.hasImage = false;
		this.firstContact = false;
		this.geoCode = "N";
		this.stateId = 1;
		this.countyId = 101;
		
	}

	public void newInmobi(String countryId, 
						 Integer stateId, 
						 Integer countyId, 
						 Integer districtId,
						 String sourceKey,
						 String sourceUrl,
						 String imageUrl,
						 Long price,
						 String title, 
						 Boolean localCurrency,
						 Boolean sale,
						 String propertyType,
						 String propertySubType
			) {

			this.countryId = countryId;
			this.stateId = stateId;
			this.countyId = countyId;
			this.districtId = districtId;
			
			this.sourceKey = sourceKey;
			this.sourceUrl = sourceUrl;

			setImageUrl(imageUrl);
			
	
			
			this.title = title;
			
			this.propertyType = propertyType;
			this.propertySubType = propertySubType;
			
			
			
	}

	public Ad(String listingType, String propertyType, Boolean sale,
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
		setImageUrl(imageUrl);
		
		
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
		
		this.setSearchPriority(0);
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

	public Ad(String imageUrl, String sourceUrl, String sourceKey, Integer source, String description, Long dolPrice, 
			Date pubDate, String zone, Boolean sale, Boolean rent,
			String typeName, String country, String currencyCode2, 
			Integer lotSize, Integer constructionSize, Integer yearBuilt,
			Date updateDate, Long dolRentPrice, String title,
			Integer districtId, Integer countyId, Integer stateId, GeoPt geoPt, String email) {
		this.setImageUrl( imageUrl );
		this.setDescription(description) ;
		this.setSale(sale);
		this.setRent(rent);
		this.setPropertyType(typeName);
		this.setStartDate(pubDate);
		this.setPubDate(pubDate);
		
		
		if (currencyCode2.equals(Parameters.AD_COUNTRY_CRC)) {
			this.setLocalPrice(dolPrice);
			this.setLocalRentPrice(dolRentPrice);
			this.setCurrencyCode1(currencyCode2);	
			
			this.setLocalCurrency(true);
			
			Float f = dolPrice*Parameters.CHANGE_TO_US;			


			this.setDolPrice(f.longValue());
			
			f = dolRentPrice*Parameters.CHANGE_TO_US;			

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
			
			this.setLocalPrice( dolPrice*Parameters.CHANGE_TO_CR);
						
			this.setLocalRentPrice(dolRentPrice*Parameters.CHANGE_TO_CR);
			
			if (! this.getSale()) {
				this.setLocalPrice(0L);
				this.setDolPrice(0L);
			
			}
			
			if (! this.getRent()) {
				this.setLocalRentPrice(0L);
				this.setDolRentPrice(0L);
			
			}

			
		}
		
		//System.out.println("Sale: "+" Code: " + currencyCode2 + " localPrice: "+this.getLocalPrice()+" localRentPrice: "+this.getLocalRentPrice()+ " dolPrice: "+this.getDolPrice()+ " dolRentPrice: "+this.getDolRentPrice());

		
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
		
		this.setEmail(email);
		
		//this.price = calculatePrice(Parameters.AD_CURRENCY_US);

	}


	public Ad(String firstName, String LastName) {
		this.firstName = firstName;
		this.lastName = lastName;
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

	public String getListingTypeName() {
		if (listingType==null) return "";
		if (listingType.equals("N")) return "Nueva";
		if (listingType.equals("R")) return "Usada";		
		return "";
	}
	

	public void setListingType(String listingType) {
		if (listingType==null) this.listingType="R";
		else this.listingType = listingType;
	}



	public Boolean getLocalCurrency() {
		if (localCurrency==null) return true;
		return localCurrency;
	}




	public void setLocalCurrency(Boolean localCurrency) {
		this.localCurrency = localCurrency;
	}




//	public Long getPrice() {

//		if (getSale()) {
//			return getDolPrice();
//		}
//		if (getRent()) {
//			return getDolRentPrice();
//		}				
//		return getDolPrice();
//	}


	public Long getPrice() {
		
	    Long price = 0l;
	    

		if (getLocalCurrency()) {
			if (getRent()) 	price = getLocalRentPrice();			
			if (getSale()) 	price = getLocalPrice();			
		}
		else {
			if (getRent()) 	price = getDolRentPrice();			
			if (getSale()) 	price = getDolPrice();	
		}
		
    	return price ;		
    	
	}
	
	public Long getRentPrice() {
		
	    Long price = 0l;
	    

		if (getLocalCurrency()) {
			if (getRent()) 	price = getLocalRentPrice();			
		}
		else {
			if (getRent()) 	price = getDolRentPrice();			
		}
		
    	return price ;		
    	
	}
	
	public String getPriceFmt() {
		
	    Long price = 0l;
	    
	    String currency = "\u20a1";

		if (getLocalCurrency()) {
			if (getRent()) 	price = getLocalRentPrice();			
			if (getSale()) 	price = getLocalPrice();			
		}
		else {
			if (getRent()) 	price = getDolRentPrice();			
			if (getSale()) 	price = getDolPrice();	
			currency = "US$";
		}
		
    	return currency+price ;		
    	
	}

	public String getPriceFmtServer() {
		
		
	    Long price = 0l;
	    
	    String currency = "�";

		if (getLocalCurrency()) {
			if (getRent()) 	price = getLocalRentPrice();			
			if (getSale()) 	price = getLocalPrice();			
		}
		else {
			if (getRent()) 	price = getDolRentPrice();			
			if (getSale()) 	price = getDolPrice();	
			currency = "US$";
		}
		
		
		
		
		
    	return currency+price ;		
    	
	}
	

	
	public String getPriceFmt(Boolean sale, Boolean localCurrency) {
		
    	
    	Integer price = getPrice(sale, localCurrency);
    	
    	if (localCurrency) return Parameters.CRC_CURRENCY_DISPLAY+price;
    	else return Parameters.US_CURRENCY_DISPLAY+price;
		
	};

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

	public String getStatuNames() {
		if (status==null) return "";

		if (status.equals("A")) return "Activo";
		if (status.equals("E")) return "Expiro";		
		if (status.equals("D")) return "Eliminado";	
		if (status.equals("S")) return "Detenido";	
		if (status.equals("P")) return "Pendiente";	
		if (status.equals("N")) return "Nuevo";					
		return "";
	}


	public void activate() {
		status = "A";
	}
	
	public void stop() {
		status = "S";
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
		this.setSourceUrl(Parameters.HOST_DOMAIN + Parameters.AD_HASHTAG + sourceKey);

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
		
		if (imageUrl!=null && !imageUrl.isEmpty()) this.hasImage = true;
		else this.hasImage = false;
		
	}

	
	public String getImageUrl(int width, int height) {
		
		return (getIsPortrait()) ? imageUrl+"=s"+height : imageUrl+"=s"+width;
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


	public String getCurrencyFmt(){
		if ( getLocalCurrency()) return Parameters.CRC_CURRENCY_DISPLAY;
		return Parameters.US_CURRENCY_DISPLAY;
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


	public String getPropertyTypeName() {				// L = Lot B = Building H = Home;
		if (propertyType==null) return "";
		if (propertyType.equals("L")) return "Lote";
		if (propertyType.equals("B")) return "Edificio";		
		if (propertyType.equals("H")) return "Casa";		
		return "";
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


	public String getPropertySubTypeName() {				 // For Home = (H)ouse, (A)partment, (C)ondominum
		if (propertySubType==null) return getPropertyTypeName();
		if (propertySubType.equals("H")) return "Casa";
		if (propertySubType.equals("A")) return "Apartamento";		
		if (propertySubType.equals("C")) return "Condominio";		
		if (propertySubType.equals("B")) return "Habitacion";		
		if (propertySubType.equals("L")) return "Lote";		
		if (propertySubType.equals("F")) return "Finca";		
		if (propertySubType.equals("M")) return "Local comercial";		
		if (propertySubType.equals("D")) return "Edificio comercial";		
		if (propertySubType.equals("W")) return "Bodega";		
		if (propertySubType.equals("O")) return "Oficina";		
		if (propertySubType.equals("T")) return "Lotes comerciales";		
		if (propertySubType.equals("V")) return "Casa para vacionar";		
		if (propertySubType.equals("P")) return "Proyecto";		
		
		return getPropertyTypeName();
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
	
	public Integer getPrice(Boolean sale, Boolean localCurrency) {
		
		if ( !sale && !localCurrency && !this.localCurrency) return this.dolRentPrice.intValue();
		if ( !sale && !localCurrency && this.localCurrency) return (int) (this.localRentPrice*Parameters.CHANGE_TO_US);
		if ( !sale && localCurrency && !this.localCurrency) return (int) (this.dolRentPrice*Parameters.CHANGE_TO_CR);
		if ( !sale && localCurrency && this.localCurrency) return this.localRentPrice.intValue();
		if ( sale && !localCurrency && !this.localCurrency) return this.dolPrice.intValue();
		if ( sale && !localCurrency && this.localCurrency) return (int) (this.localPrice*Parameters.CHANGE_TO_US);
		if ( sale && localCurrency && !this.localCurrency) return (int) (this.dolPrice*Parameters.CHANGE_TO_CR);
		if ( sale && localCurrency && this.localCurrency) return this.localPrice.intValue();

		return 0;
	};
	
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
	
	public void updateDistrict(Integer div3) {
		setDistrictId(div3);
		setCountyId(div3 / 100);
		setStateId(div3 / 10000);
	}
	
	public void updateGeo(GeoPt geoPoint) {
		
		if (getGeoCode() != "U") {
			setGeoCode("G");
			setGeoPoint(geoPoint);
		}

	}




	public String getFirstName() {
		return firstName;
	}




	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}




	public String getLastName() {
		return lastName;
	}




	public void setLastName(String lastName) {
		this.lastName = lastName;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}
	
	public float getLatitude() {
		
		if  (getGeoPoint()!=null) return getGeoPoint().getLatitude();
		return 0f;
		
	}
	
	public float getLongitude() {
		
		if  (getGeoPoint()!=null) return getGeoPoint().getLongitude();
		return 0f;
		
	}

	public Boolean getConfirmationSent() {
		return confirmationSent;
	}

	public void setConfirmationSent(Boolean confirmationSent) {
		this.confirmationSent = confirmationSent;
	}
	
    public Integer getFrontSize() {
		return frontSize;
	}




	public void setFrontSize(Integer frontSize) {
		this.frontSize = frontSize;
	}


	public Date getConfirmationSentDate() {
		return confirmationSentDate;
	}


	public void setConfirmationSentDate(Date confirmationSentDate) {
		this.confirmationSentDate = confirmationSentDate;
	}


	public Integer getConfirmationKey() {
		return confirmationKey;
	}


	public void setConfirmationKey(Integer confirmationKey) {
		this.confirmationKey = confirmationKey;
	}
	
	// copia solo los elementos que son usados en el DS
	public void copyFromDS(Ad from) {
			
		setFirstName(from.getFirstName());
		setLastName(from.getLastName());
		setPhone(from.getPhone());
		setEmail(from.getEmail());

		setId(from.getId());
		setSale(from.getSale()); // is this property for sale?
		setRent(from.getRent()); // is this property for rent?
		setTitle(from.getTitle());
		setDescription(from.getDescription());
		setStatus(from.getStatus());

		setShowEmail(from.getShowEmail());
		setShowName(from.getShowName());
		setShowPhone(from.getShowPhone());

		setLocalCurrency(from.getLocalCurrency());

		setLocalPrice(from.getLocalPrice());
		setDolPrice(from.getDolPrice());
		setLocalRentPrice(from.getLocalRentPrice());
		setDolRentPrice(from.getDolRentPrice());
		setLotSize(from.getLotSize());
		setFrontSize(from.getFrontSize());

		setConstructionSize(from.getConstructionSize());
		setBathRooms(from.getBathRooms());
		setPartialBaths(from.getPartialBaths());
		setBedRooms(from.getBedRooms());
		setGarages(from.getGarages());
		setYearBuilt(from.getYearBuilt());
		setFloors(from.getFloors());
		setPubDate(from.getPubDate());
		setListingType(from.getListingType()); // N=New R=Resell
		setPropertyType(from.getPropertyType()); // L = Lot B = Building H =
													// Home;
		setPropertySubType(from.getPropertySubType()); // Home = (H)ouse,
															// (A)partment,
															// (C)ondominum
		setCountryId(from.getCountryId());
		setStateId(from.getStateId());
		setCountyId(from.getCountyId());
		setCity(from.getCity());
		setResidential(from.getResidential());
		setAddress(from.getAddress());
		
		setVideo01(from.getVideo01());
		setVideo02(from.getVideo02());
		setVideo03(from.getVideo03());
	
		setPhotosyn01(from.getPhotosyn01());
		setPhotosyn02(from.getPhotosyn02());
		setPhotosyn03(from.getPhotosyn03());
		
	}


	public String getPhotosyn01() {
		return photosyn01;
	}


	public void setPhotosyn01(String photosyn01) {
		this.photosyn01 = photosyn01;
	}


	public String getPhotosyn02() {
		return photosyn02;
	}


	public void setPhotosyn02(String photosyn02) {
		this.photosyn02 = photosyn02;
	}


	public String getPhotosyn03() {
		return photosyn03;
	}


	public void setPhotosyn03(String photosyn03) {
		this.photosyn03 = photosyn03;
	}


	public String getVideo01() {
		return video01;
	}


	public void setVideo01(String video01) {
		this.video01 = video01;
	}


	public String getVideo02() {
		return video02;
	}


	public void setVideo02(String video02) {
		this.video02 = video02;
	}


	public String getVideo03() {
		return video03;
	}


	public void setVideo03(String video03) {
		this.video03 = video03;
	}
	
	public boolean showVideo() {

		if (video01!=null ) {
			if (!video01.trim().isEmpty()) return true;
		}
		if (video02!=null ) {
			if (!video02.trim().isEmpty()) return true;
		}
		if (video03!=null ) {
			if (!video03.trim().isEmpty()) return true;
		}

		
		return false;
	}
	
	public String getSaleType() {
		if (sale) return "Venta";
		if (rent) return "Alquiler";
		return "ND";
	}
	
	public boolean showPhotoSyn() {
		
		if (photosyn01!=null ) {
			if (!video01.trim().isEmpty()) return true;
		}
		if (photosyn02!=null ) {
			if (!video02.trim().isEmpty()) return true;
		}
		if (photosyn03!=null ) {
			if (!video03.trim().isEmpty()) return true;
		}
		

		return false;
	}


	public Boolean getIsPortrait() {
		return (isPortrait==null) ? false : isPortrait;
	}


	public void setIsPortrait(Boolean isPortrait) {
		this.isPortrait = isPortrait;
	}


	public Boolean getPremium() {
		return premium;
	}


	public void setPremium(Boolean premium) {
		this.premium = premium;
	}


	public Date getStartPayment() {
		return startPayment;
	}


	public void setStartPayment(Date startPayment) {
		this.startPayment = startPayment;
	}


	public Date getNextPayment() {
		return nextPayment;
	}


	public void setNextPayment(Date nextPayment) {
		this.nextPayment = nextPayment;
	}


	public Integer getPaymentSatus() {
		return paymentSatus;
	}


	public void setPaymentSatus(Integer paymentSatus) {
		this.paymentSatus = paymentSatus;
	}

	public Boolean getMarkToDelete() {
		return markToDelete;
	}

	public void setMarkToDelete(Boolean markToDelete) {
		this.markToDelete = markToDelete;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getSaleTypeName() {
		if (this.sale) {
			if (this.rent) return "Venta y alquiler";
			else return "Venta";
		}
		else return "Alquiler";
	}

	public Boolean getHasImage() {
		return hasImage;
	}

	public void setHasImage(Boolean hasImage) {
		this.hasImage = hasImage;
	}
	
	public void setIsAgent(Boolean isAgent) {
		this.isAgent = isAgent;
	}

	public boolean getIsAgent() {
		if (this.isAgent==null) return false;
		return this.isAgent;
	}
	
	public void setAdType(String adType) {
		this.adType = adType;
		setSearchPriority(3);
		if (this.adType.startsWith("P")) setSearchPriority(1);
		if (this.adType.startsWith("E")) setSearchPriority(2);
		if (this.adType.startsWith("B")) setSearchPriority(3);

	}

	public String getAdType() {
		if (this.adType==null) setAdType("B");
		return this.adType;
	}
	
	public String getAdTypeName() {
		if (this.adType.equals("P")) return "Premium";
		if (this.adType.equals("P2")) return "PremiumPlus";

		if (this.adType.equals("E")) return "Exclusivo";
		if (this.adType.equals("E2")) return "ExclusivoPlus";
		
		
		return "Basico";
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public void updatePerson(String email, String firstName, String lastName, Long personId ) {	
		
		
		setEmail(email);
		setFirstName(firstName);
		setLastName(lastName);
		setAgentKey(new Key<Person>(Person.class, personId));
	}
	
	public void init(String email, String firstName, String lastName, Long personId) {
		
		updatePerson(email, firstName, lastName, personId);
		
		setListingType("N");
		setPropertyType("H");
		setSale(true);
		setRent(false);
		setStatus("Z");
		setCreationDate(new Date());
		setUpdateDate(new Date());
		setSourceKey("");
		setHasImage(false);
		setIsPortrait(false);
		setPrice(0l);
		setLocalCurrency(true);
		setLocalPrice(0l);
		setDolPrice(0l);
		setLocalRentPrice(0l);
		setDolRentPrice(0l);
		setShowName(true);
		setShowPhone(true);
		setShowEmail(true);
		setShowAddress(true);
		setViewCount(0);
		setPropertySubType("H");
		setGeoCode("N");
		setAdType("B");
		
		setCountryId(Parameters.AD_COUNTRY_CRC);
		setSource(Parameters.AD_SOURCE_CRCASAS);
		
		setPubDate(new Date());
		setCreationDate(new Date());

		
	}
	
	public Boolean isPremium() {

		return getAdType().startsWith("P");
}
	
	public Boolean isExclusive() {
		return getAdType().startsWith("E");
	}
	public Boolean isBasic() {
		return getAdType().startsWith("B");
	}

	public Integer getSearchPriority() {
		if (this.searchPriority==null) setSearchPriority(3);
		return searchPriority;
	}

	public void setSearchPriority(Integer searchPriority) {
		this.searchPriority = searchPriority;
	}
	
	public boolean notEmpty() {
		if (this.getId() !=null) return true;
		else return false;
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public Integer getPostCount() {
		return postCount;
	}

	public void setPostCount(Integer postCount) {
		this.postCount = postCount;
	}
	
	
		
}
