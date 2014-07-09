package cr.novatec.crcasas.server.servlet;


import java.io.IOException;


import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")
public class NewAdServlet extends HttpServlet {


		DataBaseServicesImpl db = new DataBaseServicesImpl();

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
				
			 String firstName = req.getParameter("firstNameNewAd");
			 String lastName = req.getParameter("lastNameNewAd");
			 String email = req.getParameter("emailNewAd");

			 String fbId = req.getParameter("fbIdNewAd");
			 			 
			 String sourceKey = db.createNewAd( 
					 email, 
					 firstName, 
					 lastName, 				 
					 fbId);

        	if (sourceKey==null) db.sentEmail( "Problemas con el ingreso de un anuncio" , firstName+" "+lastName + " "+ email); 
             
        	res.setContentType("text/html"); 
		    res.getWriter().println(sourceKey);

		    
		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
		    resp.setHeader("Content-Type", "text/html");
		    resp.getWriter().println("Hello");
		   
		    
        	
		}

}
