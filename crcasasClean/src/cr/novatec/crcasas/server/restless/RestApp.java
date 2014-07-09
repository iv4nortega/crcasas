package cr.novatec.crcasas.server.restless;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class RestApp  extends Application {


	    @Override
	    public Restlet createInboundRoot() {
	        // Create a router Restlet that routes each call to a
	        // new instance 
	        Router router = new Router(getContext());

	        // Defines only one route
	        //router.attachDefault(HelloWorldResource.class);

	        //router.attachDefault(new Directory(getContext(), "war:///"));

	        //router.attach("restlet/ad/123", AdServerResource.class);
	        
	        
	        //router.attachDefault(AdServerResource.class);
	        
	        // crcasas.com/buscar/casas/venta/san_jose?subtipo=apartamento&canton=1100&preciomin=100&preciomax=200
	        //  crcasas.com/buscar/{tipo}/{modalidad}/{provincia}
	
	        
	        router.attach("/json/imagen/{anuncio}",
	        		ImageResource.class);

	        
	        router.attach("/json/anuncio/{anuncio}",
	        		AdServerResource.class);	                
	        
	        router.attach("/json/anuncio/{anuncio}/form",
	        		AdFormResource.class);

	        router.attach("/json/anuncio/{anuncio}/{key}/form",
	        		MyAdResource.class);
	        
	        router.attach("/json/anuncio/{anuncio}/form/{action}/{key}",
	        		AdServerResource.class);

	        router.attach("/json/premium",
	        		PremiumAdsResource.class);

	        router.attach("/json/three",
	        		ThreeAdsResource.class);
	        
	        router.attach("/json/premium/{type}",
	        		PremiumAdsResource.class);
	        
	        router.attach("/json/anuncios",
	        		AdsResource.class);

	        router.attach("/json/anuncios/{type}/{mode}/{division1}",
	        		AdsResource.class);

	        router.attach("/json/anuncios/{type}/{mode}/{division1}/{division2}",
	        		AdsResource.class);
	        
	        
	        router.attach("/json/user/{user}",
	        		MyAdsResource.class);

	        router.attach("/json/hasads/{user}",
	        		HasAdsResource.class);
	        
	        
	        router.attach("/json/division1",
	        		Division1Resource.class);
	        
	        router.attach("/json/division2",
	        		Division2Resource.class);

	        router.attach("/json/division2/{key}/geo",
	        		Division2Geo.class);
	        
	        router.attach("/json/division2/{key}",
	        		Division2Resource.class);

	        router.attach("/json/division2/geo",
	        		Division2Geo.class);
 
	        router.attach("/file/url",
	        		UrlResource.class);

	        router.attach("/file/url2",
	        		UrlFile.class);

	        router.attach("/file/{key}/metapro",
	        		FileResourcePro.class);

	        
	        router.attach("/file/{key}/meta",
	        		FileResource.class);

	        router.attach("/file/anuncio/{anuncio}/meta",
	        		FileResource.class);

	        
	        return router;
	        	    	
	        	}
	        	

	        
	    }
	