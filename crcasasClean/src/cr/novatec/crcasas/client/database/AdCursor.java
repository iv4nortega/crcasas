package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Id;

public class AdCursor implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    Long id;
	List<AdWeb> adList;
	String cursor;
	Integer counter;
	Integer countAll;

    
	public AdCursor() {
	}

	public AdCursor(List<AdWeb> adList, String cursor, Integer countAll, Integer counter) {
		this.adList = adList;
		this.cursor = cursor;
		this.countAll = countAll;
		this.counter = counter;
	}

	public List<AdWeb> getAdList() {
		return adList;
	}

	public void setAdList(List<AdWeb> adList) {
		this.adList = adList;
	}

	public String getCursor() {
		return cursor;
	}

	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		if (counter==null) this.counter = 0;
		else this.counter = counter;
	}

	public Integer getCountAll() {
		return countAll;
	}

	public void setCountAll(Integer countAll) {
		if (countAll==null) this.countAll = 0;
		else this.countAll = countAll;
	}

	
}
