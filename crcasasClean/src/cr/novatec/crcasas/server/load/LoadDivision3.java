package cr.novatec.crcasas.server.load;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cr.novatec.crcasas.client.database.Division2;
import cr.novatec.crcasas.client.database.Division3;
import cr.novatec.crcasas.server.resources.MyServerLibrary;
import cr.novatec.crcasas.server.rpc.DataBaseServicesImpl;

@SuppressWarnings("serial")



public class LoadDivision3 extends HttpServlet  {
	

	MyServerLibrary library = new MyServerLibrary();
	private final DataBaseServicesImpl db = new DataBaseServicesImpl();

	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	 throws IOException {	
		
		System.out.println("Load division 3 started");
		
		URL url = new URL("http://efiquicia.roqz.net/media/codigopostalcr.html");

		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
		
        String line;
        String lines;
        lines = "";
        
        while ((line = reader.readLine()) != null) {      	
        	lines = lines + line;
        }
        
        db.deleteAllDivision2();
        db.deleteAllDivision3();
        // <b>21302</b> - Aguas Claras<br>
        String pattern = "<b>(\\d{5})</b>...([^<]+)<br>";
        
        PatSearch1(lines, pattern);
		
        System.out.println("Load division 2 ended");
      
		System.out.println("Load division 3 started");

        // <b style="font-size: large; padding-top: 50px;">Escazú</b><br> <div id="102">
        String pattern2 = "<b.style=\"font-size:.large;.padding-top:.50px;\">([^<]+)</b><br>\\s*<div.id=\"([^\"]+)\">";
        
        PatSearch2(lines, pattern2);
        
		System.out.println("Load division 2 names in division 3");
      
		db.loadDivision3Names();
  
		System.out.println("Load division 3 ended");

        
	 }
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	 throws ServletException, IOException {
	 doGet(req, resp);
	 }	 
	
	//

	
	

	public void PatSearch1(String source, String pattern){
        Pattern p = Pattern.compile(pattern);                       
        Matcher m = p.matcher(source);     
        
        while (m.find()) {
        	if (m.group(1)!= null) System.out.println(m.group(1));
           	if (m.group(2)!= null) System.out.println(m.group(2));
           	
           	if ( (m.group(1)!= null) && (m.group(2)!=null )) {
           		Division3 d3 = new Division3(Integer.parseInt(m.group(1)), m.group(2));
           		db.saveDivision3(d3);
           	}
                 	 
        }

	}
	
	public void PatSearch2(String source, String pattern){
        Pattern p = Pattern.compile(pattern);                       
        Matcher m = p.matcher(source);     
        
        while (m.find()) {
        	if (m.group(1)!= null) System.out.println(m.group(1));
           	if (m.group(2)!= null) System.out.println(m.group(2));
           	
           	if ( (m.group(1)!= null) && (m.group(2)!=null )) {
           		Division2 d2 = new Division2(Integer.parseInt(m.group(2)), m.group(1));
           		db.saveDivision2(d2);
           	}
                 	 
        }

	}
		
}	