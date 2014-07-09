package cr.novatec.crcasas.server.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class UrlRewriteServlet implements Filter {

	
	@Override
	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res,
		      FilterChain chain) throws ServletException, IOException {

	        HttpServletRequest request = (HttpServletRequest) req;

	        String requestURI = request.getRequestURI();
	        

	        if (requestURI.equals("/lotes/coronado")) {
	           
        		req.getRequestDispatcher("/propiedades.html").forward(req, res);	

	         	return;

	         	
	        } else {
					chain.doFilter(req, res);
	
	        }
	    }		  		
		

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
}	
	