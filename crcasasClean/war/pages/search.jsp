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




<html>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
    <head>
    
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="description"   	content="Encuentre casas y lotes a la venta y alquiler. Anuncie gratis su propiedad en crCasas.com" />
        <meta name="keywords"      	content="casas Costa Rica, venta de casas, casas, casa, propiedades, lotes, alquileres, bienes raíces, apartamentos, casas, condominios, edificios, lotes, fincas, quintas, san jose, alajuela, heredia, cartago, puntarenas, limon, guanacaste" />
        <meta name="title" 			content="crCasas: La Pagina de Bienes Raices de Costa Rica"/>   
        <meta name="revisit-after" content="1 days" />
	    <meta name="rating"        content="general" />
        <meta name="language"      	content="es" />

        <title>crCasas: La Pagina de Bienes Raices de Costa Rica</title>
		  

</head>

  <body >

  <div class="jumbotron">
    <h1>Encuentre su casa o lote hoy</h1>
    <p class="lead">en la página principal de Bienes Raíces de Costa Rica</p>
    <a id="btnLandingSearch" class="btn btn-large btn-success">Buscar</a>
    <p class="lead">
      <br>
      <a id="btnLandingAdvertise" class="pointer">Quiere anunciar su propiedad? Siga este vinculo. Anunciar es gratis!</a> 
    </p>
  </div>

<%
	


	DAO dao = new DAO();
	Long adId = null;
	
	String adIdst = "";
	
	String title = "";


	Boolean rent = true;
	Boolean sale = true;
	
	String saleType = "a la venta";
	
	
	String state = "";
	String county = "";

	String city = "";
	
	String listingType = ""; // N=New R=Resell
	String propertyType = ""; // L = Lot B = Building H = Home;
	String propertySubType = ""; // For Home = (H)ouse, (A)partment, (C)ondominum
			

	
	DataBaseServicesImpl db = new DataBaseServicesImpl();
	List<Ad> adList = db.getAdList();
	
	for (Ad ad: adList) {
		
		adId = ad.getId();
		title = ad.getTitle();
		propertySubType = ad.getPropertySubTypeName(); 
		if (ad.getSale()) saleType = "a la venta";
		if (ad.getRent()) saleType = "en alquiler";
		if (ad.getSale() && ad.getRent()) saleType = "a la venta y alquiler";

		state = db.getDivision1ById(ad.getStateId());
		county = db.getDivision2ById(ad.getCountyId());
		
		
		%>
		
		<p>
		<a href="http://www.crcasas.com#!anuncio=<%=adId%>" target="_blank"><%=title%>, <%=propertySubType%> <%=saleType%> en <%=county%>, <%=state%></a>		
		</p>
		<%

		
	};
	
	

	
%>





  

  </div>
   
  </body>
</html>