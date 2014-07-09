package cr.novatec.crcasas.client.database;

import java.io.Serializable;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Indexed;

import cr.novatec.crcasas.client.resources.MyClientLibrary;

public class Encuentra3 implements Serializable {
	
	private static final long serialVersionUID = 2L;

	@Id Long id;

	Integer key; // key in divison 2
	
	@Indexed String name;  // name in encuentra24
	
	
	public Encuentra3( )
	{
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = MyClientLibrary.sanitizeString(name);
	}

	public Encuentra3(Integer key, String name) {
		super();
		this.key = key;
		this.name = MyClientLibrary.sanitizeString(name);
	}
	
}
