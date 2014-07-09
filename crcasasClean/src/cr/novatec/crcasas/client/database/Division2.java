package cr.novatec.crcasas.client.database;

import java.io.Serializable;

import javax.persistence.Id;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Indexed;

import cr.novatec.crcasas.client.Parameters;
import cr.novatec.crcasas.client.resources.MyClientLibrary;

public class Division2 implements Serializable {
	
	private static final long serialVersionUID = 2L;

	@Id Long id;

	@Indexed Integer key;
	
	@Indexed String name;
	
	private GeoPt geoPoint;

	float area;
	
	int population;

	Integer houses;
	
	Integer lots;
	
	Integer rents;

	public Division2( )
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

	public String getNameFmt() {

		
		return name.toLowerCase();
	

	}
	
	public void setName(String name) {
		this.name = MyClientLibrary.sanitizeString(name);
	}

	public Division2(Integer key, String name) {
		super();
		this.key = key;
		this.name = MyClientLibrary.sanitizeString(name);
	}

	public GeoPt getGeoPoint() {
		return geoPoint;
	}

	public void setGeoPoint(GeoPt geoPoint) {
		this.geoPoint = geoPoint;
	}
	
	

	public float getArea() {
		return area;
	}

	public void setArea(float area) {
		this.area = area;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public String getAddress() {
		
		int d1 = this.key/100;
		
		String d1Name = null;
		
		System.out.println("Division2 key: " + key + " division1: " + d1);
		
		if (d1 > 0 && d1 < 8) d1Name = Parameters.DIVISION1_NAMES[d1-1];
		if (d1Name==null) return this.name;
		else return this.name+" , " + d1Name;
	}

	public Integer getHouses() {
		return houses;
	}

	public void setHouses(Integer houses) {
		this.houses = houses;
	}

	public Integer getLots() {
		return lots;
	}

	public void setLots(Integer lots) {
		this.lots = lots;
	}

	public Integer getRents() {
		return rents;
	}

	public void setRents(Integer rents) {
		this.rents = rents;
	}
	
	
	
}
