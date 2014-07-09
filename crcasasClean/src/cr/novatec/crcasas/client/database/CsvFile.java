package cr.novatec.crcasas.client.database;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Id;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Indexed;

import cr.novatec.crcasas.client.Parameters;

// Esta clase sirve para recibir datos crudos de bulkloader. La clase esta customizada
// para recibir datos de los cantones y provincias de Costa Rica tomadas de la gaceta.

public class CsvFile implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
    Long id;
	@Indexed private String field0;
	@Indexed private int count;
	private String field1;
	private String field2;
	private String field3;
	private String field4;
	private String field5;

	

    
	public CsvFile() {
	}





	public CsvFile(String field0, String field1, String field2, String field3, String field4,
			String field5) {
		super();
		this.field0 = field0;

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





	public String getField0() {
		return field0;
	}





	public void setField0(String field0) {
		this.field0 = field0;
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

	public boolean isRowDiv2() {		
		
		Pattern p = Pattern.compile("CANTON\\s*([0-9][0-9][0-9])(.+)");     //CANTON 101 San JosE      
		Matcher m = p.matcher(field1.toUpperCase());
		
		while (m.find()) { 			
			String n = m.group(1);
			
			try {
				if (Integer.parseInt(n)>= Parameters.FIRST_DIV2 && Integer.parseInt(n) <= Parameters.LAST_DIV2 )
					return true;
			}
			catch (NumberFormatException e) {
                return false;
            }

			
		}
		return false;	
	
	}
	
	public String getDiv2Name() {
				
		Pattern p = Pattern.compile("CANTON\\s*([0-9][0-9][0-9])(.+)");     //CANTON 101 San JosE      
		Matcher m = p.matcher(field1.toUpperCase());
		
		while (m.find()) { 
			return m.group(2);
		}
		return null;			
	}
	
	public Integer getDiv2Key() {
		
		Pattern p = Pattern.compile("CANTON\\s*([0-9][0-9][0-9]).(.+)");     //CANTON 101 San JosE      
		Matcher m = p.matcher(field1.toUpperCase());
		
		while (m.find()) { 
			try {
				return Integer.parseInt(m.group(1));
			}
			catch (NumberFormatException e) {
                return null;
            }
		}
		return null;			
	}
	
	public boolean isRowDiv3() {		
	
		Pattern p = Pattern.compile("([0-9][0-9])(.+)");     // 01 EscazU      
		Matcher m = p.matcher(field1.toUpperCase());
		
		while (m.find()) { 
			//digits = digits + m.group(1);
			return true;
		}
		return false;		
	}
	
	public String getDiv3Name() {		
		
		Pattern p = Pattern.compile("([0-9][0-9])(.+)");     // 01 EscazU      
		Matcher m = p.matcher(field1.toUpperCase());
		
		while (m.find()) { 
			return m.group(2);
		}
		return null;		
	}
	
	public Integer getDiv3Key() {		
		
		Pattern p = Pattern.compile("([0-9][0-9])(.+)");     // 01 EscazU      
		Matcher m = p.matcher(field1.toUpperCase());
		
		while (m.find()) { 
			try {
				return Integer.parseInt(m.group(1));
			}
			catch (NumberFormatException e) {
                // nothing
            }

		}
		return null;		
	}
	
	public  GeoPt getGeo() {
		GeoPt g = null;

		if ((field4!=null) && (field5!=null)) {
			try {
				
				Float lat = coorTodec(field4);
				Float lon = coorTodec(field5);
				if (lat!=null&& lon != null)				
					g = new GeoPt(lat, lon);				
			}
			catch (NumberFormatException e) {
                // nothing
            }
			
		}
		return g;
	}

	
	public Float coorTodec(String coordinate) {
		
		
		Pattern p = Pattern.compile("([0-9]+)\\.([0-9][0-9])([0-9][0-9])");        
		Matcher m = p.matcher(coordinate);
		
		while (m.find()) { 
			String secondsStg = m.group(3);
			String minutesStg = m.group(2);
			String degreesStg = m.group(1);
			
			float decimal = 0f;
			try {
				
				float degrees = Integer.parseInt(degreesStg);
				float minutes = Integer.parseInt(minutesStg);
				float seconds = Integer.parseInt(secondsStg);
				
				decimal = degrees;
				decimal = decimal + ( minutes / 60 );
				decimal = decimal + (seconds / 3600);
				
				
				return decimal;
				
			} catch (NumberFormatException e) {
	            return null;
	        }

		}
		
		return null;
		
	};
	
	public String coorTodecDebug(String coordinate) {
		Pattern p = Pattern.compile("([0-9]+)\\.([0-9][0-9])([0-9][0-9])");        
		Matcher m = p.matcher(coordinate);
		
		while (m.find()) { 
			
			String secondsStg = m.group(3);
			String minutesStg = m.group(2);
			String degreesStg = m.group(1);
			
			float decimal = 0f;
			try {
				
				float degrees = Integer.parseInt(degreesStg);
				float minutes = Integer.parseInt(minutesStg);
				float seconds = Integer.parseInt(secondsStg);
				
				decimal = degrees;
				decimal = decimal + ( minutes / 60 );
				decimal = decimal + (seconds / 3600);
				
				
				return degreesStg +  " " + minutesStg + " "  + secondsStg;
				
			} catch (NumberFormatException e) {
	            return null;
	        }

		}
		
		return null;
		
	};
	
	
	public float getArea() {
		if (field2!=null)
			try {
				return Float.parseFloat(field2);
			}
			catch (NumberFormatException e) {
                return 0;
            }
		else return 0;
		
	}
	
	public int getPopulation() {
		
		
		if (field3!=null)
			try {
				
				field3= field3.replaceAll("\\s+", "");
				return Integer.parseInt(field3);
			}
			catch (NumberFormatException e) {
                return 0;
            }

		else return 0;
		
	}
}
