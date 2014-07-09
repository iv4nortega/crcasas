package cr.novatec.crcasas.server.servlet;


import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")
public class WelcomeServlet extends HttpServlet {


		DataBaseServicesImpl db = new DataBaseServicesImpl();

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
					     
			 String adId = req.getParameter("adId");	
			 
			 db.sendNewWelcomeMess(adId);			 

			
        	res.setContentType("text/html"); 
		    res.getWriter().println("Done");

		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {

			String adId = req.getParameter("adId");	
			 
			 db.sendNewWelcomeMess(adId);			 

			
       	resp.setContentType("text/html"); 
		    resp.getWriter().println("Done");
			
        	
		}
		

}
