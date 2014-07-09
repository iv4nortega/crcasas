<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DataBaseServicesImpl" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DAO" %>
<%@ page import="cr.novatec.crcasas.client.database.ImageDataBase" %>
<%@ page import="cr.novatec.crcasas.client.database.Ad" %>
<%@ page import="java.util.List" %>


<%
	
	DAO dao = new DAO();
	Long adId = null;
	String adIdst = "";
	Boolean showGalleria = false;
	
	DataBaseServicesImpl db = new DataBaseServicesImpl();
	Ad ad = new Ad();
	if (request.getParameter("adId") != null) {
		
		adIdst = request.getParameter("adId");
		adId = Long.parseLong(adIdst);
		if (adId != null ) {
			ad = db.getAdById(adId);
			
			if (ad!= null) {
				
			} else {
			
				%>
			    <jsp:forward page="notfound.html">
			      <jsp:param name="adId" value="<%=adId%>"/>
			    </jsp:forward>
			    <% 
				
			} 
			
			
		} else {
			%>
		    <jsp:forward page="notfound.html">
		      <jsp:param name="adId" value="<%=adId%>"/>
		    </jsp:forward>
		    <% 
			
		}
		
	} else {
		%>
	    <jsp:forward page="notfound.html">
	      <jsp:param name="adId" value="<%=adId%>"/>
	    </jsp:forward>
	    <% 
	}

	
%>




<html>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
    <head>
    
    	<style type="text/css">
    		#galleria{height:467px; }
    	</style>
		
		 <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>    
  		 <script src="galleria/galleria-1.2.7.min.js"></script>
  		 


</head>

  <body >
  

  
<div >

<%
	if (adId != null) {
		

		List<ImageDataBase> records =  dao.ofy().query(ImageDataBase.class).filter("adId", adId).order("displayOrder").list();
		
		if ( ! records.isEmpty() ) {
			showGalleria = true;
			%>
				<div id="galleria">
			<%					
			for (ImageDataBase image: records) {
				%>
					<img src=<%=image.getImageUrl()%>
						     <% if (image.getCaption()!=null) {
						    	 %>
						    	   data-title=<%=image.getCaption()%>
						    	 <% 
						     	}
						     	if (image.getDescription()!=null) {	
							    	 %>
							    	   data-description=<%=image.getCaption()%>
							    	 <% 
						     		
						     	}
						     	
						     %>
					>
				<%		
				}	
			%>
			
			</div>
			<%					
		}		
	}	
%>	



     
</div>







<%
	if (showGalleria ) {
		%>
		<script type="text/javascript">
			$('#gallery').galleria({
			width: 100%,
			height: 100% --I made heights match
			});
		</script>

		<script>
		    Galleria.loadTheme('galleria/themes/classic/galleria.classic.min.js');
		    Galleria.configure({
		    });
		    
		    Galleria.run('#galleria');
		</script>		
		<%		
	}

	
%>  

   
  </body>
</html>