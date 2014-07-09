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

public final class CrawlServlet implements Filter {

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
				         	return;

			        	}
			        } else {
			        	
			        	p = Pattern.compile("anuncio%3D(\\d+)");  
			        	
				        m = p.matcher(queryString);     
				        
				        if (m.find())  {
				        	if (m.group(1) != null) {
				        		req.getRequestDispatcher("/displayAd.jsp?adId="+m.group(1)).forward(req, res);	
					         	return;

				        	}
				        } else {
				        	req.getRequestDispatcher("/displayAd.jsp?adId=2394064").forward(req, res);
				         	return;

				        }
			        	
			        	
			        }
				   
				   
			   } else {
				   
				   
				//   java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
				//     java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);

			       // use the headless browser to obtain an HTML snapshot
			      // final WebClient webClient = new WebClient(BrowserVersion.FIREFOX_17);
			    //     webClient.getOptions().setThrowExceptionOnScriptError(false);
			    //    webClient.getOptions().setCssEnabled(false);
			       
			       
			       
			       // webClient.getOptions().setJavaScriptEnabled(false);
			       
			       
			    //   StringBuffer requestURL = request.getRequestURL();
			    //    if (request.getQueryString() != null) {
			    //        requestURL.append("?").append(request.getQueryString());
			    //    }
			    //    String completeURL = requestURL.toString();			       
	
			    //    HtmlPage page = webClient.getPage(rewriteUrl(completeURL));

			       // important!  Give the headless browser enough time to execute JavaScript
			       // The exact time to wait may depend on your application.
			    //   webClient.waitForBackgroundJavaScript(4000);

			       // return the snapshot
			    //    PrintWriter out = res.getWriter();
			    //    out.println(rewriteUrl(page.asXml()));
			       
			       req.getRequestDispatcher("/home.jsp").forward(req, res);
		         	return;

			   }


		       
		     } else {
		        // not an _escaped_fragment_ URL, so move up the chain of servlet (filters)
		        chain.doFilter(req, res);
		    }
		
	}      

	public String rewriteUrl(String url) {
		
		String result = "";
		url = url.replace("%3F", "?");
		url = url.replace("%3D", "=");

		result = url.replace("?_escaped_fragment_=", "#!");
				

		return result;
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
	
}	
	