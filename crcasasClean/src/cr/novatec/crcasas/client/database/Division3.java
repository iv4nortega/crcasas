package cr.novatec.crcasas.client.database;

import java.io.Serializable;

import javax.persistence.Id;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Indexed;

import cr.novatec.crcasas.client.Parameters;
import cr.novatec.crcasas.client.resources.MyClientLibrary;


public class Division3 implements Serializable {

	private static final long serialVersionUID = 2L;

	@Id Long id;

	@Indexed Integer key;
	
	@Indexed String name;
	
	String d2Name;
		
	private GeoPt geoPoint;
	
	float area;
	
	int population;
	
	Integer houses;
	
	Integer lots;
	
	Integer rents;


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

	public Division3( )
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

	public Division3(Integer key, String name) {
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

	
	public String getD2Name() {
		return d2Name;
	}

	public void setD2Name(String d2Name) {
		this.d2Name = d2Name;
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
		
		int d2 = this.key/100;
		int d1 = d2/100;
		
		System.out.println("Division3 key: " + key + " division2: " + d2 + " division1: " + d1);
		
		String d1Name = null;
		
		if (d1 > 0 && d1 < 8) d1Name = Parameters.DIVISION1_NAMES[d1-1];
		if (d1Name==null) return this.name;
		else return this.name+" , " + this.d2Name+" , "+d1Name;
	}
	
}
