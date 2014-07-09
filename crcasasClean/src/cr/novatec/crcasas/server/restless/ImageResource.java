package cr.novatec.crcasas.server.restless;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;


import cr.novatec.crcasas.client.database.Ad;
import cr.novatec.crcasas.client.database.ImageDataBase;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

public class ImageResource extends ServerResource {

	
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();


    @Get
    public Representation retrieve()  {
    	
    	Form queryParams = getRequest().getResourceRef().getQueryAsForm(); 
 	   String callBack = queryParams.getFirstValue("callback");

    	
    	String sourceKey = (String) this.getRequest().getAttributes().get("anuncio");

    	
    	JSONObject jsonObj = new JSONObject();
    	
    	JSONArray jsonArr = new JSONArray();
    	
    	List<ImageDataBase> list;
    	
    	Ad myAd;
    	
    	JSONObject j;
    	
    	try {
    		
    			myAd = db.getAdBySourceKey(sourceKey);   			
    			list = db.getImagesByAdId(myAd.getId());
        		int c = 0;
       		 	if (list==null) {
       		 		
        			if (myAd!=null && myAd.showVideo()) {

        				j = new JSONObject();
        				j.put("video:", myAd.getVideo01());
        				j.put("title", myAd.getTitle());
        				j.put("description", "");
        				//jsonArr.put(c, j);
        				//c++;
        			}
       		 		
       		 		if (c==0){
           				j = new JSONObject();
           				j.put("image", "images/crcasas.png");
           				j.put("title", "");
           				j.put("description", "");
           				j.put("link", "");
           				j.put("layer", "");
          		 		
          		 		jsonArr.put(0, j);    	      		 
       		 			
       		 		}
       		 		
       		 	} else {
           		 	for (ImageDataBase image : list) {    			 
           		 		jsonArr.put(c, toJson(image));    	      		 
           		 		c++;
           		 	}       	
           		 	
        			if (myAd!=null && myAd.showVideo()) {

        				j = new JSONObject();
        				j.put("video:", myAd.getVideo01());
        				j.put("title", myAd.getTitle());
        				j.put("description", "");
        				// jsonArr.put(c, j);
        				//c++;
        			}
       		 	}
    		     		
    		jsonObj.put("imagenes", jsonArr);
   		

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    	if (callBack!=null) return new StringRepresentation(callBack+"("+jsonArr.toString()+")");    	
    	else return new JsonRepresentation(jsonArr);
          
    }
    
	public JSONObject toJson(ImageDataBase image) throws JSONException {
		
		
		JSONObject j = new JSONObject();
		j.put("thumb", image.getImageUrl()+"=s144");
		j.put("thumbnail_url", image.getImageUrl()+"=s144-c");
		j.put("image", image.getImageUrl()+"=s720");
		j.put("big", image.getImageUrl());
		j.put("title", image.getCaption());
		j.put("description", image.getDescription());
		j.put("link", "");
		j.put("layer", "");
		j.put("key", image.getKey());
		j.put("name", image.getFileName());
		j.put("size", image.getSize());
		

		return j;
		
	}
	
    
}
