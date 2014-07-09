package cr.novatec.crcasas.server.blobstore;


import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;


import cr.novatec.crcasas.client.database.Errors;
import cr.novatec.crcasas.client.database.ImageDataBase;
import cr.novatec.crcasas.server.rpc.DAO;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")
public class BlobStoreServlet extends HttpServlet {

		private BlobstoreService blobstoreService = BlobstoreServiceFactory
				.getBlobstoreService();

		private final DAO dao = new DAO();


		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
			
		    res.setHeader("Access-Control-Allow-Origin", "http://f.crcasas.com");
		    res.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		     res.setHeader("Access-Control-Max-Age", "1728000");
			
			try {
				
				int len = req.getContentLength();
				int mb = (1024 * 1024) * 4;
				//if (len > mb) { 
				//	throw new RuntimeException("Sorry that file is too large. Try < 4MB file");
				//}
				
		        Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		        
		        // cambio el 26feb2013
		        //BlobKey blobKey = blobs.get("fileItem");

		        BlobKey blobKey = blobs.get("files[]");

		        
		        
		        if (blobKey == null) {
		            res.sendRedirect(
		        	
	    			"/crcasas/blobstoreservlet?"+
	    			"image-id=0" 
	    			
	    			);

		        } else {
		        	      	
			        ImagesService imagesService = ImagesServiceFactory.getImagesService();
			        String imageUrl = imagesService.getServingUrl(blobKey);
			        
			        Image image = ImagesServiceFactory.makeImageFromBlob(blobKey);
			        image = imagesService.applyTransform(ImagesServiceFactory.makeCrop(0, 0, 1, 1), image);
			        
			        int height = image.getHeight();
			        int width  = image.getWidth();
			        			        
			        
		        	BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
		        	
		        	
		        	
		        	String fileName = blobInfo.getFilename();
		        	if (fileName==null) fileName = "";


			        String newimageUrl;
			        
			        Pattern pattern = Pattern.compile("0.0.0.0");		
			        newimageUrl = pattern.matcher(imageUrl).replaceFirst("127.0.0.1");
			        
			        
			        
		        	ImageDataBase record = new  ImageDataBase();	        	
		        	record.setKey(blobKey.getKeyString());	  
		        	record.setImageUrl(newimageUrl);
		        	record.setDate(new Date());
		        	//record.setSize(blobInfo.getSize());
		        	record.setFileName(fileName);
		        	record.setContentType(req.getContentType());
		        	record.setHeight(height);
		        	record.setWidth(width);
		        	record.setDisplayOrder(99);
		        		        	
		        

		        	dao.ofy().put(record);
		        	assert record.getId() != null; 


	        	// /crcasas/blobstoreservlet  //blobstorecallback
		        //	res.sendRedirect(
		        //			"/crcasas/blobstoreservlet?"+
		        //			"image-id=" + record.getId().toString()
		        //			
		        //	);
		        	
		        // CAMBIO EL 26FEB2013

		        	res.sendRedirect("/restlet/file/" + blobKey.getKeyString() + "/meta");	        	
		        	
		        	
		        	
		        }				
				
			} 
			catch (Exception e) {
	        	res.sendRedirect(
	        			"/crcasas/blobstoreservlet?"+
	        			"image-id=0");
	        	
	        	DataBaseServicesImpl db = new DataBaseServicesImpl();
	        	db.sentEmail("Updated BlobstoreServlet exception", e.getMessage());
	        	db.saveError(new Errors(new Date(), e.getMessage(), e.getLocalizedMessage(), "BlobStoreServlet", "ServletException, IOException"));
	        	
	        	
			}


			
		}

		@Override
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			
		    resp.setHeader("Access-Control-Allow-Origin", "http://f.crcasas.com");
		    resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		     resp.setHeader("Access-Control-Max-Age", "1728000");
 	
		    //BlobKey blobKey = new BlobKey(req.getParameter("blob-key"));
		    //blobstoreService.serve(blobKey, resp);
		  
			String imageId = req.getParameter("image-id");
			
		    resp.setHeader("Content-Type", "text/html");
		    resp.getWriter().println(imageId);
		   
		    
        	
		}
	}
