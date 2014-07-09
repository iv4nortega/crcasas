package cr.novatec.crcasas.server.servlet;


import java.io.IOException;

import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;

import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")
public class HTMLMailServlet extends HttpServlet {


		DataBaseServicesImpl db = new DataBaseServicesImpl();

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
				
			 // String firstName = req.getParameter("firstNameNewAd");
        	res.setContentType("text/html"); 
		    res.getWriter().println("doPost");

		    
		}
		
		//http://www.google.co.in/search?hl=en&q=java&meta=
		//This is an example of doGet().

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
			 String adId = req.getParameter("adId");
			 String to = req.getParameter("to");
			 String subject = req.getParameter("subject");

			
			db.sendAdByMail(to, to, subject, adId);
			
		    resp.setHeader("Content-Type", "text/html");
		    resp.getWriter().println("doGet Done! " + adId);
		   
		    
        	
		}

}
