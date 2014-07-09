package cr.novatec.crcasas.server.restless;

import java.util.concurrent.ConcurrentMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Message;
import org.restlet.data.Form;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;


public class UrlResource extends ServerResource {

	private final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
	private static final String HEADERS_KEY = "org.restlet.http.headers";


    @Get
    public Representation getCallbackUrl() throws JSONException {
    	
    	
        /* this is /_ah/upload and it redirects to its given path */
    	
   	 getMessageHeaders(getResponse()).add(new Header("Access-Control-Allow-Origin", "http://f.crcasas.com"));
   	 getMessageHeaders(getResponse()).add(new Header("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS"));

   	 
       	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
  	   String callBack = queryParams.getFirstValue("callback");

  	 String url;
 	  
  	 if (callBack!=null) {
  		 
  		url = blobstoreService.createUploadUrl("/crcasas/blobcross");
  		 
  	 } else  url = blobstoreService.createUploadUrl("/crcasas/blobstoreservlet");
        
    	JSONObject jsonObj = new JSONObject();
    	
    	jsonObj.put("url", url );

    	if (callBack!=null) {
    		
    		//return new JsonRepresentation(jsonObj);
    		
    		return new StringRepresentation(callBack+"("+jsonObj.toString()+")");
    	}
    	
    	
        return new JsonRepresentation(jsonObj);
      }
    
    @SuppressWarnings("unchecked")
    static Series<Header> getMessageHeaders(Message message) {
        ConcurrentMap<String, Object> attrs = message.getAttributes();
        Series<Header> headers = (Series<Header>) attrs.get(HEADERS_KEY);
        if (headers == null) {
            headers = new Series<Header>(Header.class);
            Series<Header> prev = (Series<Header>) 
                attrs.putIfAbsent(HEADERS_KEY, headers);
            if (prev != null) { headers = prev; }
        }
        return headers;
    }
         
}
