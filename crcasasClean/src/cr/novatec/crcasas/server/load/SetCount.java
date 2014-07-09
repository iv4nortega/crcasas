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

public class SetCount extends HttpServlet  {
	
	
	
	private final DAO dao = new DAO();

	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {	
		
		System.out.println("SetCount Task started");		
		
		
		Query<CsvFile> query =  dao.ofy().query(CsvFile.class);
		
		CsvFile row;
				
		QueryResultIterator<CsvFile> iterator = query.iterator();
						
		while (iterator.hasNext() ) {
			
			row = iterator.next();	
			
			if (row.getField0()!=null) {
				row.setCount(Integer.parseInt(row.getField0()));
				
				dao.ofy().put(row);
				assert row.getId() != null; 	

				
			}


		}

	 }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
	 doGet(req, resp);
	 }	 
	

}	