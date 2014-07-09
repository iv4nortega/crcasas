package cr.novatec.crcasas.server.blobstore;


import java.io.IOException;


import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@SuppressWarnings("serial")
public class BlobStoreCallback extends HttpServlet {

		private BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();
	


		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
 	
			String blobKey = req.getParameter("blob-key");
			String blobUrl = req.getParameter("blob-url");
			String fileName = req.getParameter("filename");
			String imageId = req.getParameter("image-id");
			String size = req.getParameter("size");
			

		       	 
		    String message = 
		    "<html>"+
		    "<head>"+
		    "<script type='text/javascript'>"+	 
		    "function displaymessage()"+
		    "{"+
		    "window.parent.uploadComplete('"+
		    blobKey+ "'"+", '"+
		    blobUrl+ "'"+", '"+
		    fileName+ "'"+", '"+
		    imageId+
		    "');"+		    
		    "}"+
		    "window.onload=displaymessage();"+
		    "</script>"+
		    "</head>"+
		    "<body>"+
		    ""+
		    "</body>"+
		    "</html>"; 
		    
		    resp.setHeader("Content-Type", "text/html");
		   
		    
		    resp.getWriter().println(message.toString());

		   //resp.getWriter().println("Filename<" + fileName + "> "+
			//	   					"Url<" + blobUrl + "> "	+
			//						"Size<" + size + "> "	);
		   
       	
		}
	}
