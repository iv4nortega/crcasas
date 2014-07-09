package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Indexed;

import cr.novatec.crcasas.client.resources.MyClientLibrary;

public class Jobs implements Serializable {
	
	private static final long serialVersionUID = 2L;

	@Id Long id;
	
	Date date;

	Integer source; // Encuentra = 4, Nacion = 2, crCasas = 1;
	
	String url;  
	
	Integer count;
	
	Integer read;

	Integer save;
	
	Integer startPage;

	Integer endPage;
	
	String status; // Start = S;  End= E; 

	Date finishDate;
	
	String propertyType; // L = Lot B = Building H = Home;
	
	Boolean sale;

	
	public Jobs( )
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Jobs(Date date, Integer source, String url, Integer count,
			String status, String propertyType, Boolean sale, Integer startPage) {
		super();
		this.date = date;
		this.source = source;
		this.url = url;
		this.count = count;
		this.status = status;
		this.propertyType = propertyType;
		this.sale = sale;
		this.startPage = startPage;
	}
	
	public Jobs(String message) {
		this.status = message;
		this.date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getRead() {
		return read;
	}

	public void setRead(Integer read) {
		this.read = read;
	}

	public Integer getSave() {
		return save;
	}

	public void setSave(Integer save) {
		this.save = save;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public Boolean getSale() {
		return sale;
	}

	public void setSale(Boolean sale) {
		this.sale = sale;
	}

	public Integer getStartPage() {
		return startPage;
	}

	public void setStartPage(Integer startPage) {
		this.startPage = startPage;
	}

	public Integer getEndPage() {
		return endPage;
	}

	public void setEndPage(Integer endPage) {
		this.endPage = endPage;
	}


	
}
