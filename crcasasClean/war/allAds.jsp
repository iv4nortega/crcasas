<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DataBaseServicesImpl" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DAO" %>
<%@ page import="cr.novatec.crcasas.client.database.Ad" %>
<%@ page import="java.util.List" %>



<%


	DAO dao = new DAO();
		
	String sourceKey = "";
	String title = "";
	String state = "";
	String county = "";
	String type = "";
	String property = "";
	String propertySubType = ""; // For Home = (H)ouse, (A)partment, (C)ondominum
	String saleType = "a la venta";
	Boolean sale = true;
	

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


<title>Bienes Raices en Costa Rica, Casas y lotes a la venta, alquileres - crCasas</title>
<link rel="stylesheet" href="css/bootstrap.min.css"  media="screen">
<link rel="stylesheet" href="css/bootstrap-responsive.min.css">

</head>

 <body>
  
<div id="container"> 
<% 

List<Ad> adList = db.getAdList();


for (Ad ad: adList) {

	sourceKey = ad.getSourceKey();
	title = ad.getTitle();
	state = db.getDivision1ById(ad.getStateId());
	county = db.getDivision2ById(ad.getCountyId());
	type = ad.getAdTypeName();
	property = ad.getPropertyTypeName();
	propertySubType = ad.getPropertySubTypeName(); 
	sale = ad.getSale();
	
	if (!sale) saleType = "en alquiler";
	
	%>
		<div>
		<h3><%=title%></h3>		
		<a 	href="http://www.crcasas.com#!anuncio=<%=sourceKey%>"  >
			http://www.crcasas.com#!anuncio=<%=sourceKey%>
		</a>
		<p><%=propertySubType%> <%=saleType%> en <%=state%>, <%=county%></p>   	
		</div>
	<%
			
}	

%>

</div>  




   
  </body>
</html>