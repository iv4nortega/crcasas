package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Indexed;
import com.googlecode.objectify.annotation.Unindexed;

@Unindexed

public class Alerts implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    Long id;
	
    private @Indexed Key<Person> member;
    private String name;
    private Date startDate;    
    
	private String countryId;
	private Integer stateId; 
	private Integer countyId;	
	
	private String propertyType; // L = Lot B = Building H = Home C= Comercial;
	private String propertySubType;
  	
	private Boolean sale;   // Is this property on sale?

	private Boolean localCurrency; 
	
	private Long minPrice;    
	private Long maxPrice;    
	
	private Boolean sendEmail;   
	private Boolean sendSMS;  
	private Boolean sendFB;  
	
	private String comments;

	
	public Alerts() {
	}


	public Alerts(Key<Person> member, String name, Date startDate,
			String countryId, Integer stateId, Integer countyId,
			String propertyType, String propertySubType, Boolean sale,
			Boolean localCurrency, Long minPrice, Long maxPrice,
			Boolean sendEmail, Boolean sendSMS, Boolean sendFB, String comments) {
		super();
		this.member = member;
		this.name = name;
		this.startDate = startDate;
		this.countryId = countryId;
		this.stateId = stateId;
		this.countyId = countyId;
		this.propertyType = propertyType;
		this.propertySubType = propertySubType;
		this.sale = sale;
		this.localCurrency = localCurrency;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.sendEmail = sendEmail;
		this.sendSMS = sendSMS;
		this.sendFB = sendFB;
		this.comments = comments;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Key<Person> getMember() {
		return member;
	}


	public void setMember(Key<Person> member) {
		this.member = member;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getStartDate() {
		return startDate;
	}


	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}


	public String getCountryId() {
		return countryId;
	}


	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}


	public Integer getStateId() {
		return stateId;
	}


	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}


	public Integer getCountyId() {
		return countyId;
	}


	public void setCountyId(Integer countyId) {
		this.countyId = countyId;
	}


	public String getPropertyType() {
		return propertyType;
	}


	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}


	public String getPropertySubType() {
		return propertySubType;
	}


	public void setPropertySubType(String propertySubType) {
		this.propertySubType = propertySubType;
	}


	public Boolean getSale() {
		return sale;
	}


	public void setSale(Boolean sale) {
		this.sale = sale;
	}


	public Boolean getLocalCurrency() {
		return localCurrency;
	}


	public void setLocalCurrency(Boolean localCurrency) {
		this.localCurrency = localCurrency;
	}


	public Long getMinPrice() {
		return minPrice;
	}


	public void setMinPrice(Long minPrice) {
		this.minPrice = minPrice;
	}


	public Long getMaxPrice() {
		return maxPrice;
	}


	public void setMaxPrice(Long maxPrice) {
		this.maxPrice = maxPrice;
	}


	public Boolean getSendEmail() {
		return sendEmail;
	}


	public void setSendEmail(Boolean sendEmail) {
		this.sendEmail = sendEmail;
	}


	public Boolean getSendSMS() {
		return sendSMS;
	}


	public void setSendSMS(Boolean sendSMS) {
		this.sendSMS = sendSMS;
	}


	public Boolean getSendFB() {
		return sendFB;
	}


	public void setSendFB(Boolean sendFB) {
		this.sendFB = sendFB;
	}


	public String getComments() {
		return comments;
	}


	public void setComments(String comments) {
		this.comments = comments;
	}
	

}
