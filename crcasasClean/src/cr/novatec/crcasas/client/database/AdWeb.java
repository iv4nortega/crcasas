package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Indexed;

import cr.novatec.crcasas.client.Parameters;


public class AdWeb implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
    Long id;
	private @Indexed String listingType; // N=New R=Resell
	private @Indexed String propertyType; // L = Lot B = Building H = Home C= Comercial;
	  	
	private @Indexed Boolean sale;   // Is this property on sale?
	private @Indexed Boolean rent;   // Is this property for rent?
	
	private String firstName;
	private String lastName;
	private String phone;
	private @Indexed String email;	

	private Date pubDate; 
	private Date expDate; 
	private Date creationDate; 
	private Date updateDate; 
	
	private @Indexed Integer source; // nacion (2) crcasas (1) craiglist (3) encuentra24 (4)
	private @Indexed String sourceKey; 
	private String sourceUrl; 
	private String imageUrl; 
	
	private String title; 
	private String description; 
	private @Indexed Long price;   // price used for sort operations // i think may be deprecated
	private Boolean localCurrency; // define si se usa el local currency o no
	private @Indexed Long localPrice;     // currencycode1
	private @Indexed Long dolPrice;       // currencycode2

	private @Indexed Long localRentPrice;   //currencycode1    
	private @Indexed Long dolRentPrice;     // currencycode2    

	
    private @Indexed String propertySubType;  // For Home = (H)ouse, (A)partment, (C)ondominum (B) Habitaciones (V) Vacaciones
                                              // For Lots = (L) Lote (F) Finca, (C) Lotes comerciales
    										  // For comercial =  (B) Edificios (W) Bodegas (V) Bovedas (O) Oficinas
    private Integer constructionSize;
    private Integer lotSize;

	private Integer bedRooms;
    private Integer bathRooms;
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


    
	public AdWeb() {
		
		setCreationDate(new Date());
		
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



	public String getCurrencyFmt(){
		if ( getLocalCurrency()) return Parameters.CRC_CURRENCY_DISPLAY;
		return Parameters.US_CURRENCY_DISPLAY;
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



	public String getPropertySubType() {
		return propertySubType;
	}


	public String getPropertySubTypeName() {				 // For Home = (H)ouse, (A)partment, (C)ondominum
		if (propertySubType==null) return "";
		if (propertySubType.equals("H")) return "Casa";
		if (propertySubType.equals("A")) return "Apartamento";		
		if (propertySubType.equals("C")) return "Condominio";		
		return "";
	}


	public void setPropertySubType(String propertySubType) {
		this.propertySubType = propertySubType;
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

	
	public String getSaleType() {
		if (sale) return "Venta";
		if (rent) return "Alquiler";
		return "ND";
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}



	public Date getPubDate() {
		return pubDate;
	}



	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}



	public Date getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	
	
}
