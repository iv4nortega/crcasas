package cr.novatec.crcasas.server.load;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.Query;

import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.CsvFile;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.resources.MyServerLibrary;
import cr.novatec.crcasas.server.rpc.DAO;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")



public class SetHasImage extends HttpServlet  {
	

	private final DAO dao = new DAO();

	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {	
		
		System.out.println("Hasimage Task started");		
		
		
		Query<Ad> query =  dao.ofy().query(Ad.class);
		
		Ad ad;
				
		QueryResultIterator<Ad> iterator = query.iterator();
						
		while (iterator.hasNext() ) {
			
			ad = iterator.next();	
			
			ad.setImageUrl(ad.getImageUrl());

			dao.ofy().put(ad);


		}
        
	 }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
	 doGet(req, resp);
	 }	 
	
		
}	