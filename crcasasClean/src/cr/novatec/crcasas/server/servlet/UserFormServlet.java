package cr.novatec.crcasas.server.servlet;


import java.io.IOException;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")
public class UserFormServlet extends HttpServlet {


		DataBaseServicesImpl db = new DataBaseServicesImpl();

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
			
		    res.setHeader("Access-Control-Allow-Origin", "http://f.crcasas.com");
		    res.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		     res.setHeader("Access-Control-Max-Age", "1728000");
		     
			 String fbId = req.getParameter("idF01");
			 String email = req.getParameter("emailF01");

			 String firstName = req.getParameter("firstNameF01");

			 String lastName = req.getParameter("lastNameF01");
			         	
        	
        	Person person = db.getFBUser(fbId);
        	
        	if (person==null) {
        		
        		person = db.saveFBUser(fbId, email, firstName, lastName, "","");
        		
        	};
        	
        	res.setContentType("text/html"); 

		    String response = "";
		    
		    if (person!=null) response = person.getId().toString();
		    else response = "null";
		    
		    res.getWriter().println(response);

		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
		    resp.setHeader("Access-Control-Allow-Origin", "http://f.crcasas.com");
		    resp.setHeader("Content-Type", "text/html");

		    resp.getWriter().println("Hello");
		   
		    
        	
		}
		
	protected Integer stringToInteger(String s) {
		
		Integer result = 0;
		
		if (s==null) return result;
		
		try 
		{
			result = Integer.parseInt(s);
		}
		
		catch( NumberFormatException e)
		{
			result = 0;
		};
		
		return result;
				
	};
	
	protected Long stringToLong(String s) {
		
		Long result = 0l;
		
		if (s==null) return result;

		
		try 
		{
			result = Long.parseLong(s);
		}
		
		catch( NumberFormatException e)
		{
			result = 0l;
		};
		
		return result;
				
	};

	
	protected Float stringToFloat(String s) {
		
		Float result = 0f;
		
		if (s==null) return result;

		
		try 
		{
			result = Float.parseFloat(s);
		}
		
		catch( NumberFormatException e)
		{
			result = 0f;
		};
		
		return result;
				
	}
	
	
	protected Boolean stringToBoolean(String s) {
		
		Boolean result = false;
		if (s==null) return result;
		
		if (s.toUpperCase().equals("TRUE") ) result = true;
		if (s.toUpperCase().equals("ON") ) result = true;
				
		return result;
				
	}
	
	protected Person getPersonFromFbId(String fbId) {
		return null;
	}
}
