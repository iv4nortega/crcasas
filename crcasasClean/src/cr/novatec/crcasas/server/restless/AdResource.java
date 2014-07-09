package cr.novatec.crcasas.server.restless;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;



public interface AdResource {

		@Get
	    public Representation retrieve() ;

}