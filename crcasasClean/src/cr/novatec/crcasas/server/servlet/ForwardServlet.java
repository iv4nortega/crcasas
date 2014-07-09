package cr.novatec.crcasas.server.servlet;


import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")
public class ForwardServlet extends HttpServlet {


		DataBaseServicesImpl db = new DataBaseServicesImpl();

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
					     
			 String toName1 = req.getParameter("toName1");
			 String toEmail1 = req.getParameter("toEmail1");

			 String toName2 = req.getParameter("toName2");
			 String toEmail2 = req.getParameter("toEmail2");

			 String toName3 = req.getParameter("toName3");
			 String toEmail3 = req.getParameter("toEmail3");

			 String toName4 = req.getParameter("toName4");
			 String toEmail4 = req.getParameter("toEmail4");

			 String toName5 = req.getParameter("toName5");
			 String toEmail5 = req.getParameter("toEmail5");


			 String subject = req.getParameter("subject");
			 
			 String adId = req.getParameter("adId");	

			 String fromEmail = req.getParameter("fromEmail");

			 String sendTo =  "" ;

			 if (fromEmail!=null) sendTo = fromEmail;

			 
			 if (subject==null) subject="Anuncio de la propiedad en crCasas";
			         	
			if (toEmail1!=null)  { 
				if (toName1!=null ) db.sendAdByMail(toEmail1, toName1, subject, adId);
				else db.sendAdByMail(toEmail1, toEmail1, subject, adId);
				sendTo = sendTo + " " + toEmail1;
			};

			if (toEmail2!=null)  { 
				if (toName2!=null ) db.sendAdByMail(toEmail2, toName2, subject, adId);
				else db.sendAdByMail(toEmail2, toEmail2, subject, adId);
				sendTo = sendTo + " " + toEmail2; 
			};
			
			if (toEmail3!=null)  { 
				if (toName3!=null ) db.sendAdByMail(toEmail3, toName3, subject, adId);
				else db.sendAdByMail(toEmail3, toEmail3, subject, adId);
				sendTo = sendTo + " " + toEmail3; 

			};
			
			if (toEmail4!=null)  { 
				if (toName4!=null ) db.sendAdByMail(toEmail4, toName4, subject, adId);
				else db.sendAdByMail(toEmail4, toEmail4, subject, adId);
				sendTo = sendTo + " " + toEmail4; 

			};
			
			if (toEmail5!=null)  { 
				if (toName5!=null ) db.sendAdByMail(toEmail5, toName5, subject, adId);
				else db.sendAdByMail(toEmail5, toEmail5, subject, adId);
				sendTo = sendTo + " " + toEmail5; 

			};
			
			db.sentEmail(adId+" fue reenviado", sendTo);

			 
        	res.setContentType("text/html"); 

		   
		    
		    res.getWriter().println("Done");

		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			

		    resp.getWriter().println("Hello");
		   
		    
        	
		}
		

}
