package cr.novatec.crcasas.server.servlet;


import java.io.IOException;

import java.util.Date;


import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.Key;

import cr.novatec.crcasas.client.Parameters;
import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.Division2;
import cr.novatec.crcasas.client.database.Person;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")
public class ContactFormServlet extends HttpServlet {


		DataBaseServicesImpl db = new DataBaseServicesImpl();
		

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
		     
			 String email = req.getParameter("email");

			 String name = req.getParameter("name");

			 String phone = req.getParameter("phone");
			         	
			 String message = req.getParameter("message");
			 
			 
	        	db.sentEmail("Solicitud de informacion a crCasas ",
	        			"Nombre: " + name + "<br>" +
	        			"Telefono: " + phone + "<br>" +
	        			"Correo: " + email + "<br>" +
	        			"Mensaje: " + message + "<br>"
	        			);
	 
        	        	
        	res.setContentType("text/html"); 

		   
		    
		    res.getWriter().println("Enviado");

		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
		    resp.getWriter().println("Hello");
		   
		    
        	
		}
		

}
