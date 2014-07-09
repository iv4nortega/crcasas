package cr.novatec.crcasas.server.servlet;


import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")
public class PhotoFormServlet extends HttpServlet {


		DataBaseServicesImpl db = new DataBaseServicesImpl();

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
					     
			 String sourceKey = req.getParameter("sourceKeyPhoto");
			 String id = req.getParameter("idPhoto");
			 String key = req.getParameter("keyPhoto");
			 String position = req.getParameter("positionPhoto");
			 String fbId = req.getParameter("fbIdPhoto");
			 String action = req.getParameter("actionPhoto");

			 Long imageId = db.getImageIdByImageKey(key);
			 
			 
			 // (imageId.compareTo(stringToLong(id))==0) &&
			 
			 if ( (imageId!=null) &&  (action.equals("S"))) {
				 
				 Ad ad = db.getAdBySourceKey(sourceKey);				 				 
				 db.saveImageAdId(imageId, ad.getId(), stringToInteger(position));
				 
			 };
			 
			 if ( (imageId!=null) && action.equals("D")) {
				 db.deleteImage(imageId);
				 
			 }
			 
        	
        	res.setContentType("text/html"); 

		   
		    
		    res.getWriter().println("saved");

		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			

		    resp.getWriter().println("Hello");
		           	
		}

		
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

		protected Integer stringToInteger(String s) {
			
			Integer result = 0;
			
			if (s==null) return result;
			
			try 
			{
				result = Integer.parseInt(s.trim());
			}
			
			catch( NumberFormatException e)
			{
				result = 0;
			};
			
			return result;
					
		};

}
