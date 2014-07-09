package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;


public class Global implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    Long id;
	
	private String countryId;
	private Float exchangeToLocal;
	private Float exchangeToInter;
	private Date lastUpdate; 

	
	public Global() {
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}



	public Float getExchangeToLocal() {
		return exchangeToLocal;
	}

	public void setExchangeToLocal(Float exchangeToLocal) {
		this.exchangeToLocal = exchangeToLocal;
	}

	public Float getExchangeToInter() {
		return exchangeToInter;
	}

	public void setExchangeToInter(Float exchangeToInter) {
		this.exchangeToInter = exchangeToInter;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long convertToLocal(Long amount) {
		
		if (amount==null) return 0l;
		
		return (long) (amount*this.exchangeToLocal);
	}
	
	public Long convertToInter(Long amount) {
		
		if (amount==null) return 0l;
	
		return (long) (amount*this.exchangeToInter);
	}
	
}
