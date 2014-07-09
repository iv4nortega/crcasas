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
    
	<meta name="description"	content="Encuentre casas a la venta, lotes y alquiler en Costa Rica. Anuncie gratis su propiedad en crCasas.com" />
	<meta name="keywords"	content="casas Costa Rica, venta de casas, casas, casa, propiedades, lotes, alquileres, bienes raíces, apartamentos, casas, condominios, edificios, lotes, fincas, quintas, san jose, alajuela, heredia, cartago, puntarenas, limon, guanacaste" />
	<meta name="title"	content="Casas a la venta, lotes y alquileres en Costa Rica" />
	<meta name="revisit-after" content="1 days" />
	<meta name="rating" content="general" />
	<meta name="language" content="es" />

	<title>Casas a la venta, lotes y alquileres en Costa Rica</title>

	<link rel="stylesheet" href="css/bootstrap.min.css"  media="screen">
	<link rel="stylesheet" href="css/bootstrap-responsive.min.css">

</head>

 <body itemscope itemtype ="http://schema.org/Offer">

   <div id="jumbo" class="jumbotron">
      <div>
        <div class="container">
          <h1>
            Encuentre su casa o lote hoy
          </h1>
          <p>
            en la p&aacute;gina principal de Bienes Ra&iacute;ces de Costa Rica.
          </p>
          <p>
          </p>
          <p>
            Miles de las mejores propiedades a la venta y alquiler.
          </p>
          <p>
            Miles de las mejores propiedades a la venta y alquiler.
          </p>
          <a id="btnLandingSearch" class="btn btn-large btn-success animated rotateInRightUp btn-lg">Buscar</a>
          <h3>
            <br>
            <a id="btnLandingAdvertise" class="animated fadeIn" href="/anunciar">&#x00bf;Quiere anunciar
					su propiedad? Siga este v&iacute;nculo. Anunciar es gratis!</a>
            <a id="btnLandingNew" hidden="" class="animated fadeIn" href="/publicar.html?adId=0">&#x00bf;Necesita vender o alquilar su propiedad? Siga este v&iacute;nculo para ingresar su anuncio</a>
            <a id="btnLandingAds" hidden="" class="animated fadeIn" href="/misanuncios.html">Actualizar sus anuncios o ingresar nuevos</a>
          </h3>
        </div>
      </div>
    </div>
    <div class="spacing">
      <div class="container">
        <div class="row">
          <div class="col-2">
          </div>
          <div class="col-4">
            <div class="text-right">
              <br>
              <br>
              <br>
              <br>
              <br>
              <h2>
                Si te gusta crCasas, presiona Like!
              </h2>
            </div>
          </div>
          <div class="col-4">
            <div class="text-center">
              <div class="fb-like-box" data-href="https://www.facebook.com/crcasas"
              data-width="292" data-show-faces="true" data-header="true" data-stream="false"
              data-show-border="true">
              </div>
            </div>
          </div>
          <div class="col-2">
          </div>
        </div>
      </div>
    </div>
    <div class="spacing">
      <div class="container">
        <div class="row hidden-xs hidden-sm">
          <h2 class="text-center">
            Recomendaciones
          </h2>
          <div class="title-hr">
          </div>
        </div>
        <div class="row hidden-xs hidden-sm">
          <div class="col-2">
          </div>
          <div class="col-8">
            <div class="flexslider">
              <ul id="mainAds" class="slides">
              </ul>
            </div>
          </div>
          <div class="col-2">
          </div>
        </div>
      </div>
      <div class="row text-center">
        <div class="title-hr">
        </div>
        <br>
      </div>
      <div id="frontMasonry" style="margin: 0 auto">
        <div id="frontAdList">
        </div>
      </div>
    </div>

<div class="container">
  <h1>Acerca de <span itemprop="name">crCasas.com</span></h1>
  <hr>
  <div class="row">
    <div class="span6">
      <div class="alert">
        <legend>&iquest;Qui&eacute;nes somos?</legend><span itemprop="description">crCasas es una p&aacute;gina web de Bienes Ra&iacute;ces que
        suministra listados de propiedades a la venta y alquiler en Costa Rica.</span>
        <br>
        <br> El objetivo de crCasas es convertirse en la p&aacute;gina preferida por las personas
        interesadas en comprar o alquilar propiedades en Costa Rica.
        <br> 
      </div>
      <div class="alert">
        <legend>&iquest;Necesita comprar o alquilar una propiedad?</legend>Este sitio es para
        Ud.
        <br>
        <br> En crCasas trabajamos todos los d&iacute;as para encontrar las &uacute;ltimas propiedades,
        casas y lotes a la venta y alquiler en Costa Rica.
        <br>
        <br> Encuentre en crCasas los mejores anuncios, con la informaci&oacute;n m&aacute;s completa
        sobre cientos de propiedades en el mercado.</div>
      <div class="alert">
        <legend>&iquest;Necesita vender o alquilar su propiedad?</legend>Tambi&eacute;n este sitio es
        para Ud.
        <br>
        <br> Sea Ud. el due&ntilde;o de una propiedad, o corredor de bienes ra&iacute;ces crCasas le puede ayudar,
        publique en crCasas sus anuncios, 100% gratis!
        <br> 
      </div>
    </div>
    <div class="span6">
      <div class="alert alert-block" itemprop="seller" itemscope itemtype="http://schema.org/Person">
        <legend itemprop="name">Martha Lucia Cantillano</legend>
        <address>
          <em>Socia Fundadora</em>
          	<div itemprop="workLocation" itemscope itemtype="http://schema.org/Place">
				<div itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
			         <br><strong>crCasas.com</strong>
         			 <br> <span itemprop="addressLocality">Coronado</span>, <span itemprop="addressRegion">San Jose</span>
          			 <br> Costa Rica, America Central
          			 <br>	
          			 <meta itemprop='addressCountry' content='CRC'/> 			
	  			</div>
	  		</div>
	  			
          <abbr title="Telefono" itemprop="telephone">Tel: </abbr>(506) 8332-9197
          <br>
          <abbr title="Correo electronico">Correo: </abbr>
          <a href="mailto:#" itemprop="email">ventas@crcasas.com</a>
          <br> 
        </address>
      </div>
      <form class="alert form-horizontal alert-success alert-block " id="contactForm" method="post" action="//www.crcasas.com/forms/contact">
        <legend>Cont&aacute;ctenos</legend>
        <br>
        <div class="control-group">
          <label class="control-label">Nombre</label>
          <div class="controls">
            <input type="text" name="name" placeholder="Nombre Apellido" class="input-medium"> 
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">Tel&eacute;fono</label>
          <div class="controls">
            <input class="input-medium" type="tel" name="phone" placeholder="22924227"> 
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">Correo</label>
          <div class="controls">
            <input type="email" name="email" placeholder="name@domain.com" class="input-medium"> 
          </div>
        </div>
        <div class="control-group">
          <label class="control-label">Mensaje</label>
          <div class="controls">
            <textarea name="message"></textarea>
          </div>
        </div>
        <div></div>
        <div class="control-group">
          <label class="control-label"></label>
          <div class="controls">
            <button type="submit" name="submit" id="submit" class="btn btn-primary ">Enviar</button>
          </div>
        </div>
      </form>
    </div>
  </div>
</div>
  
<div id="container"> 
	  <h1>Anuncios premium</h1>
	
<% 

List<Ad> adList = db.getAdPremiumList();


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


<div id="container"> 
	  <h1>Todos los anuncios</h1>

<% 


List<Ad> adList2 = db.getAdList();


for (Ad ad: adList2) {

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