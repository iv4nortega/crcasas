package cr.novatec.crcasas.server.restless;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Message;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.engine.header.Header;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.util.Series;

import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;

import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;



public class FileResourcePro extends ServerResource {

	private final BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
	
	ImagesService imagesService = ImagesServiceFactory.getImagesService();
	
	DataBaseServicesImpl db = new DataBaseServicesImpl();
    
	private static final String HEADERS_KEY = "org.restlet.http.headers";

    @Get
    public Representation getMetaData() throws JSONException {

    	 getMessageHeaders(getResponse()).add(new Header("Access-Control-Allow-Origin", "http://f.crcasas.com"));
    	 getMessageHeaders(getResponse()).add(new Header("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS"));
     	

        Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
 	   String callBack = queryParams.getFirstValue("callback");
    	
    	String key = (String) this.getRequest().getAttributes().get("key");

    	String adNumber = (String) this.getRequest().getAttributes().get("anuncio");
    	
     	JSONArray jsonArr = new JSONArray();
     	JSONObject j = new JSONObject();
     	
     	JSONObject[] files;
     	
    	JSONObject result = new JSONObject();


    	if (adNumber!=null) {
    		
    		List<String> keys = db.getImagesKeysByAdId(Long.parseLong(adNumber));
    		
    		List<JSONObject> jList = new ArrayList<JSONObject>();
    		
    		for (String k: keys) {
    			j = getBlobInfo(k);
    			if (j!=null) jList.add(getBlobInfo(k));
    			//else db.sentEmail("Blobstore key not found", "Key=" + key);
    		};
    		
    		files = jList.toArray(new JSONObject[jList.size()]);
    		
    		result.put("files", files);
    		
    		
    	} else {
    		
    		j =  getBlobInfo(key);

    		files = new JSONObject[]{j};
    		
    		result = j;
    		
    	}
    	
		    	
		
    	Representation representation = new JsonRepresentation(result);
		
   	
    	if (callBack!=null) {
    		
    		representation = new StringRepresentation(callBack+"("+result+")");
    	}
    	
    	representation.setMediaType(MediaType.TEXT_HTML);
    	 

    	return representation;
		
      }
 
    @Delete
    public Representation deleteImage() throws JSONException {
    	
    	
    	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
 	   String callBack = queryParams.getFirstValue("callback");

    	String key = (String) this.getRequest().getAttributes().get("key");

    	
        String url = db.getImageUrlByImageKey(key) ;

        db.sentEmail("delete image", url);
        
        Long id = db.getImageByUrl(url).getId();
        
        db.deleteImage(id);

    	if (callBack!=null) {
    		
    		return new StringRepresentation(callBack+"("+null+")");
    	}

    	return null;
		
      }    
    
    public JSONObject getBlobInfo(String key) throws JSONException {
    	
    	 if (key==null) return null;
    	
    	BlobKey blobKey = new BlobKey(key);
    	
    	
    	
        BlobInfo info = blobInfoFactory.loadBlobInfo(blobKey);
        
        if (info==null) return null;

        String name = info.getFilename();
        long size = info.getSize();
        String url = db.getImageUrlByImageKey(key) ;
        
    	JSONObject j = new JSONObject();
    	
		j.put("name", name);
		j.put("size", size);
		j.put("url", url);
		j.put("thumbnail_url", url + "=s144-c");
		j.put("big", url);
		
		j.put("thumb", url + "=s144");
		


		j.put("delete_url", "http://www.crcasas.com/restlet/file/"+key+"/meta");
		j.put("delete_type", "DELETE");
		j.put("key", key);		

    	
    	return j;
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
