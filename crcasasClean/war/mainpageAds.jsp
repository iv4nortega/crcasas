<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DataBaseServicesImpl" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DAO" %>
<%@ page import="cr.novatec.crcasas.client.database.ImageDataBase" %>
<%@ page import="cr.novatec.crcasas.client.database.Ad" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.DecimalFormat" %>



<%

	DecimalFormat formatter = new DecimalFormat("###,###,###");

	DAO dao = new DAO();
		
	String name = "";
	String title = "";
	String description = "";

	Boolean showName = true;

	String imageUrl = "";
	String sourceUrl = "";

	Boolean localCurrency = true;
	String price = "US$0";
	String rentPrice = "US$0";
	
	String rentAndSalePrice = "US$0";
	
	String currency = "USD";
	
	if (localCurrency) currency = "CRC";

	Boolean rent = true;
	Boolean sale = true;
	
	String saleType = "a la venta";
	
	Double priceDouble = 0d;
	
	// ADDRESS SECTION
	String state = "";
	String county = "";


	String pubDate = "";

	// PROPERTY SECTION
	
	String listingType = ""; // N=New R=Resell
	String propertyType = ""; // L = Lot B = Building H = Home;
	String propertySubType = ""; // For Home = (H)ouse, (A)partment, (C)ondominum
			
	Integer viewCount = 1;
	
	DataBaseServicesImpl db = new DataBaseServicesImpl();
	

		
	
%>




<html>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<head>
    
        <meta name="description"   	content="Casas a la venta, lotes a la venta, casas y apartamentos en alquiler, anuncie su propiedad gratuitamente - crCasas" />
    	<meta name="keywords"      	content="biene raices, casas, apartamentos, lotes, venta, alquiler, anuncios, gratis" />
    	<meta name="title" 			content="Bienes Raices en Costa Rica, Casas y lotes a la venta, alquileres - crCasas"/>
		<meta name="rating"        content="general" />  
   		<meta name="revisit-after" content="1 days" />
		<meta name="language"      content="es" />
		<meta http-equiv="content-language" content="es" />

 		  
    	<link type="text/css" rel="stylesheet" href="/CSS3fx.css">
    	
    	<script type="text/javascript">

	
			    
		</script>
    	
    	
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>    
<script src="/js/jquery.masonry.min.js"></script>    
   		 

<title>Bienes Raices en Costa Rica, Casas y lotes a la venta, alquileres - crCasas</title>


</head>

 <body>
  
<div id="container"> 
<% 

List<Ad> adList = db.get15Ad();

ImageDataBase image;

float heightF;
int height;

for (Ad ad: adList) {

	imageUrl = ad.getImageUrl();
	sourceUrl = ad.getSourceUrl();
	title = ad.getTitle();
	state = db.getDivision1ById(ad.getStateId());
	county = db.getDivision2ById(ad.getCountyId());
	
	// PRICE
	localCurrency = ad.getLocalCurrency();
	sale = ad.getSale();
	rent = ad.getRent();
	
	if (!sale) saleType = "en alquiler";
				
	price = ad.getCurrencyFmt()+formatter.format(ad.getPrice().doubleValue());		
	rentPrice = ad.getCurrencyFmt()+formatter.format(ad.getRentPrice().doubleValue());		
	
	if (sale) priceDouble = ad.getPrice().doubleValue();
	else priceDouble = ad.getRentPrice().doubleValue();

	if (sale) rentAndSalePrice = price;
	else rentAndSalePrice = rentPrice;
	
	// GET IMAGE WIDTH

	image = db.getFirstImage(ad.getId());
	
	height = 180;
	
	if (image!=null && image.getWidth()>0) {
		heightF = (float) 180 / image.getWidth();	
		
		heightF = image.getHeight() * heightF;
		
		height = (int) heightF;
	
	}
	%>
		<div class="item">
		<a 	href="javascript: void(0)" 
			onclick="window.open('<%=sourceUrl%>', 'crCasas','width=900, height=650');
			_gaq.push(['_trackEvent', 'HomeView', 'Click', 'PhotoCollage']);
			return false;" >
			<img src="<%=imageUrl%>=s180"  width="180" height="<%=height%>" alt="Bienes Raices, crCasas">			
		</a>
		<p><%=title%></p>
    	<p class="capitalize"><%=state%>, <%=county%></p>
    	<p><%=rentAndSalePrice%></p>
    	
		</div>
	<%
			
}	

%>

</div>  



<script>

$(function(){

    var $container = $('#container');
  
    $container.imagesLoaded( function(){
      $container.masonry({
 	    itemSelector : '.item'
      });
    });
  
  });


</script>
   
  </body>
</html>