package cr.novatec.crcasas.server.filters;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


public final class CrawlServletBack implements Filter {

	PrintWriter out;

	//private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	
	@Override
	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res,
		      FilterChain chain) throws ServletException, IOException {

			HttpServletRequest request = (HttpServletRequest) req;
		
			String queryString = request.getQueryString();
			
		   // String requestURI = request.getRequestURI();

					
		   if ((queryString != null) && (queryString.contains("_escaped_fragment_"))) {
			   
			   if (queryString.contains("anuncio")) {
				   
			        Pattern p = Pattern.compile("anuncio=(\\d+)");    
			        
			        Matcher m = p.matcher(queryString);     
			        
			        if (m.find())  {
			        	if (m.group(1) != null) {
			        		req.getRequestDispatcher("/displayAd.jsp?adId="+m.group(1)).forward(req, res);	
			        	}
			        } else {
			        	
			        	p = Pattern.compile("anuncio%3D(\\d+)");  
			        	
				        m = p.matcher(queryString);     
				        
				        if (m.find())  {
				        	if (m.group(1) != null) {
				        		req.getRequestDispatcher("/displayAd.jsp?adId="+m.group(1)).forward(req, res);	
				        	}
				        } else {
				        	req.getRequestDispatcher("/displayAd.jsp?adId=2394064").forward(req, res);
				        }
			        	
			        	
			        }
				   
				   
			   } else {
				   
				   if (queryString.contains("buscar")) {
					   req.getRequestDispatcher("/allAds.jsp").forward(req, res);
				   } else chain.doFilter(req, res);
				   
			   }


		       
		     } else {
		        // not an _escaped_fragment_ URL, so move up the chain of servlet (filters)
		        chain.doFilter(req, res);
		    }
		
	}      

	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
}	
	