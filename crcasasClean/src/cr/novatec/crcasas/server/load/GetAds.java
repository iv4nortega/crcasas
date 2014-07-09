package cr.novatec.crcasas.server.load;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.resources.MyServerLibrary;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")



public class GetAds extends HttpServlet  {
	

	private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {	
		
		Ad ad = db.getAdById(1800063l);
			
		Person person = db.getPersonByAd(ad);
		
		 resp.setContentType("text/xml");
	     resp.getWriter().println(db.createXMLAd(ad, person));
        
	 }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
	 doGet(req, resp);
	 }	 
	
		
}	