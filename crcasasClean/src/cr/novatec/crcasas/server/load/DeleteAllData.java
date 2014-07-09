package cr.novatec.crcasas.server.load;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cr.novatec.crcasas.server.resources.MyServerLibrary;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")



public class DeleteAllData extends HttpServlet  {
	

	MyServerLibrary library = new MyServerLibrary();
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {	
		
		System.out.println("Delete all data started");
		
		db.deleteAllAd();
		//db.deleteAllDivision1();
		//db.deleteAllDivision2();
		//db.deleteAllDivision3();
		
		
		
  
		System.out.println("Delete all data ended");

        
	 }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
	 doGet(req, resp);
	 }	 
	
		
}	