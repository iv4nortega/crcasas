package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Indexed;


public class Messages implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
    Long id;
	private Date date;
	@Indexed private int count;
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private String field5;

	

    
	public Messages() {
		
		setDate(new Date());
	}





	public Date getDate() {
		return date;
	}





	public void setDate(Date date) {
		this.date = date;
	}





	public Messages( String field1, String field2, String field3, String field4,
			String field5) {
		super();
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
	}





	public Long getId() {
		return id;
	}





	public void setId(Long id) {
		this.id = id;
	}





	public int getCount() {
		return count;
	}





	public void setCount(int count) {
		this.count = count;
	}


	public String getField1() {
		return field1;
	}





	public void setField1(String field1) {
		this.field1 = field1;
	}





	public String getField2() {
		return field2;
	}





	public void setField2(String field2) {
		this.field2 = field2;
	}





	public String getField3() {
		return field3;
	}





	public void setField3(String field3) {
		this.field3 = field3;
	}





	public String getField4() {
		return field4;
	}





	public void setField4(String field4) {
		this.field4 = field4;
	}





	public String getField5() {
		return field5;
	}



	public void setField5(String field5) {
		this.field5 = field5;
	}


}
