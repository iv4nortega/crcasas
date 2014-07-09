package cr.novatec.crcasas.server.load;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cr.novatec.crcasas.server.resources.MyServerLibrary;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")



public class BigTasks extends HttpServlet  {
	

	private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {	
		
		
		db.sentEmail("Delete inactives", "started");
		
		//db.updatePrice();
		
		db.deleteInactive();
  
		//db.sentEmail("Delete inactive ended", "Delete inactive ended");

        
	 }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
	 doGet(req, resp);
	 }	 
	
		
}	