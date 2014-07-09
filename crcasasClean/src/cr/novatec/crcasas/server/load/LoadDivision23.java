package cr.novatec.crcasas.server.load;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.QueryResultIterator;

import com.googlecode.objectify.Query;

import cr.novatec.crcasas.client.database.CsvFile;
import cr.novatec.crcasas.client.database.Division2;
import cr.novatec.crcasas.client.database.Division3;
import cr.novatec.crcasas.client.database.Messages;
import cr.novatec.crcasas.server.rpc.DAO;


@SuppressWarnings("serial")

public class LoadDivision23 extends HttpServlet  {
	
	
	
	private final DAO dao = new DAO();

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {	
		
		System.out.println("LoadDivision23 Task started");		
		
		
		Query<CsvFile> query =  dao.ofy().query(CsvFile.class).order("count");
		
		CsvFile row;
		
		CsvFile row2;

		
		Division3 div3 = new Division3();
		Division2 div2 = new Division2();
		
		QueryResultIterator<CsvFile> iterator = query.iterator();
		
		int div2Key = 0;
		
		Messages mess = new Messages();
		
		mess.setField1("start loaddivision23");
		
		dao.ofy().put(mess);
		assert mess.getId() != null; 	

		
		int count = 1;
				
		while (iterator.hasNext() ) {
			
			row = iterator.next();	

			
			if (row.isRowDiv2()) {
				div2 = new Division2();
				div2.setName(row.getDiv2Name());
				
				div2Key = row.getDiv2Key();
				
				div2.setKey(div2Key);
				div2.setGeoPoint(row.getGeo());
				div2.setArea(row.getArea());
				div2.setPopulation(row.getPopulation());	
				
				
				row2 = iterator.next();	
				
				if (row2.isRowDiv3()) {
					div3 = new Division3();
					div3.setName(row2.getDiv3Name());
					div3.setKey(div2Key*100+row2.getDiv3Key());
					div3.setGeoPoint(row2.getGeo());
					div3.setArea(row2.getArea());
					div3.setPopulation(row2.getPopulation());				
					dao.ofy().put(div3);
					assert div3.getId() != null; 					
				}

				div2.setGeoPoint(row2.getGeo());

				
				dao.ofy().put(div2);
				assert div2.getId() != null; 	
				

				
			} else if (row.isRowDiv3()) {
				
				div3 = new Division3();
				div3.setName(row.getDiv3Name());
				div3.setKey(div2Key*100+row.getDiv3Key());
				div3.setGeoPoint(row.getGeo());
				div3.setArea(row.getArea());
				div3.setPopulation(row.getPopulation());				
				dao.ofy().put(div3);
				assert div3.getId() != null; 	
				
				mess = new Messages();
	
				mess.setField1(row.getField4());
				mess.setField2(row.coorTodecDebug(row.getField4()));
				
				mess.setField3(row.getField5());				
				mess.setField4(row.coorTodecDebug(row.getField5()));
				
				mess.setCount(count);

				dao.ofy().put(mess);
				assert mess.getId() != null; 	
				count++;
				
				
			}
		}
		
		System.out.println("Mashup Task ended");

		mess = new Messages();
		
		mess.setField1("loaddivision23 finish");
		
		dao.ofy().put(mess);
		assert mess.getId() != null; 	


	 }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
	 doGet(req, resp);
	 }	 
	

}	