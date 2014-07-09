package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;



public class Errors implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
    Long id;
	private Date date; 
	private String message;
	private String localizedMessage;
	private String module;
	private String type;
	

    
	public Errors() {
	}

	public Errors(Date date, String message, String localizedMessage,
			String module, String type) {
		super();
		this.date = date;
		this.message = message;
		this.localizedMessage = localizedMessage;
		this.module = module;
		this.type = type;
	}


	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}



	public String getLocalizedMessage() {
		return localizedMessage;
	}



	public void setLocalizedMessage(String localizedMessage) {
		this.localizedMessage = localizedMessage;
	}



	public String getModule() {
		return module;
	}



	public void setModule(String module) {
		this.module = module;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}

	public Object getId() {
		// TODO Auto-generated method stub
		return this.id;
	}






}
