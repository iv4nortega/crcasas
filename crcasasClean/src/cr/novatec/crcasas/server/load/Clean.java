package cr.novatec.crcasas.server.load;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cr.novatec.crcasas.server.resources.MyServerLibrary;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")



public class Clean extends HttpServlet  {
	

	MyServerLibrary library = new MyServerLibrary();
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {	
		
		System.out.println("Clean started");
		
		db.clean();
  
		System.out.println("Clean ended");
	 }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
	 doGet(req, resp);
	 }	 
	
		
}	