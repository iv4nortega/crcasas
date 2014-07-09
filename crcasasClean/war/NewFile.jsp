<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DataBaseServicesImpl" %>
<%@ page import="cr.novatec.crcasas.server.rpc.DAO" %>
<%@ page import="cr.novatec.crcasas.client.database.ImageDataBase" %>
<%@ page import="java.util.List" %>

<!--
thks!
http://www.latunyi.com/66/html-css-and-js-for-table-less-forms-with-top-aligned-labels-multiple-fields-per-row-and-error-messages/
http://pruebas.clicsoluciones.net/amintahacmstrac/browser/sites/all/libraries/galleria/themes?rev=1
-->


<html>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
    <head>
    
    	<link type="text/css" rel="stylesheet" href="galleria/themes/twelve/galleria.twelve.css">
    	
    	<style type="text/css">
    		#galleria{height:467px}
    	</style>
		
		<script type="text/javascript" src="galleria/themes/twelve/galleria.twelve.min.js"></script>    
		 <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js"></script>    
  		 <script src="galleria/galleria-1.2.7.min.js"></script>
  		 
  		     <script type="text/javascript"
      src="http://maps.googleapis.com/maps/api/js?sensor=true">
    </script>
    <script type="text/javascript">
      function initialize() {
        var myOptions = {
          center: new google.maps.LatLng(-34.397, 150.644),
          zoom: 8,
          mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        var map = new google.maps.Map(document.getElementById("map_canvas"),
            myOptions);
      }
    </script>

<title>crCasas</title>
<style type="text/css">

<!--
thks!
http://www.latunyi.com/66/html-css-and-js-for-table-less-forms-with-top-aligned-labels-multiple-fields-per-row-and-error-messages/
-->

body,
html {
	margin: 0;
	padding: 0;
	background-color: #CCCCCC;
	font-family: Helvetica,Arial,sans-serif;
	color: #3C404E;
	font-size: 90%;
}

dl {
    margin-bottom:50px;
	color: #666;

}
 
dl dt {
    //background: red;//#5f9be3;
    //color:#fff;
	color: #069;
    float:left;
    font-weight:bold;
    margin-right:10px;
    padding:5px;
    width:100px;
}
 
dl dd {
    margin:2px 0;
    padding:5px 0;
}
#wrap {
	width: 760px;
	margin: 0 auto;
	background-color: #e9f1f6;
	border: 1px solid grey;
}

#titlebox {
width:64%;

}

#header {
	padding: 10px;
	background-color: #e9f1f6;
}

#nav {
	width: 96%;
	float: left;
	margin: 0 0 2px 2px;
	padding: 10px;
	list-style: none;
	background-color: #eaebe4;
	border-bottom: 1px solid #ccc; 
	border-top: 1px solid #ccc;
	border-right: 1px solid #ccc;

	box-shadow: 5px 5px 2px #888888;
	 }
#nav li {
	float: left; }
#nav li a {
	display: block;
	padding: 8px 15px;
	text-decoration: none;
	font-weight: bold;
	color: #069;
	border-right: 1px solid #ccc; }
#nav li a:hover {
	color: #c00;
	background-color: #fff; }

#main {
float:left;
width:64%;
background-color: #FFF;
border: 1px solid grey;
box-shadow: 7px 7px 3px #888888;

margin-left:1px;
margin-right: 1px;
margin-top:5px;
margin-bottom: 5px;
}
#sidebar {
float:right;
width:35%;

}
#footer {
clear:both;
padding: 10px;
}

#leftcontainer {
width:100%;
height:300px;
}

#rightcontainer {
//width:100%;
//height:300px;
 padding: 10px;

}


#container {
 padding: 10px;
}


.alignleft {
	float: left;
}
.alignright {
	float: right;
}

h1 {
	font-size: 150%;
	width: 100%;
	line-height: 1.2em;
	font-weight: bold;
	white-space: nowrap;
	font-style: normal;
	color: #4F4F4F;
}

h2 {
	font-size: 150%;
	font-weight: 400;
	border-bottom: 1px solid;
	display: block;
	-webkit-margin-before: 0.83em;
	-webkit-margin-after: 0.83em;
	-webkit-margin-start: 0px;
	-webkit-margin-end: 0px;
	line-height: 1.5;
	padding: 0;
	border-bottom-color: #25609E;
	padding-bottom: 2px;
	padding-left:4px;
}

/* Rules for rows and fields */

.form{
	background-color: #FFB884;
	margin: 10px;
	border: 1px solid #704834;
}

.form_row {
    margin-bottom: 10px;
}
 
.form_last_row {
    margin-top: 20px;
}
 
.form_field {
    display: inline-block;
    margin-right: 5px;
}
 
.form_field label {
    display: block;
    padding-bottom: 0px;
	color:#555555;
}
 
/* Styling of errors */
.field_errors {
    margin: 10px 0 0px;
    font-size: 11px;
}
 
.field_errors span {
    display: inline-block;
    padding: 5px;
    background-color: #FFDFE0;
    color: #333;
    margin-bottom: 5px;
}
 
.form_input {
    font-size: 13px;
	color:#666666;
    background-color: white; /* light blue */
    padding: 2px;	
	border: 1px solid #999999;
  	margin: 1px;

}
 
.form_input:focus {
    background-color: #FFFBCF; /* light yellow */
    border: 1px solid #999;
}
 
 
.form_input:hover {
  border: 2px solid #006699;
  background-color: #f9f9f9;
  margin: 0;
} 
 
/* Input sizes */

.input_medium {
    width: 100%;
}
.input_large {
    width: 100%;
}

.optional_field {
    background-color: #fff;
}

.form_button{
 background-color: green;	
padding:0px 6px;
border:1px solid #3b6e22;
height:24px; 
line-height:24px;
color:#FFFFFF;
font-size:12px;
margin-right:10px;
display:inline-block;
text-decoration:none;
}

#map_canvas { height: 100% }

@media print {
.noPrint {
    display:none;
}
}

</style>
</head>

  <body onload="initialize()">
  
  <div id="wrap">
<div id="header">
	<div id="titlebox">
    	<h1>
  		<div align="left" style="width:50%; float:left;">Heredia, centro</div>
  		<div align="right" style="width:50%; float:right;">US$500,000</div>
        <div style="clear:both;"></div>
        </h1>
	</div>
</div>
<ul id="nav">
	<li><a href="#descripcion">Descripción</a></li>
	<li><a href="#caracteristicas">Características</a></li>
	<li><a href="#mapa">Mapa </a></li>
	<li><a href="#tour">Tour Virtual</a></li>
	<li><a href="#video">Video</a></li>
    <li><a href="#contactar">Contactar</a></li>

</ul>
<div id="main">
<div style="padding:10px">
<div id="leftcontainer">
<h2 >Galeria de fotos</h2>

<div id="galleria">
<%
	if (request.getParameter("adId") != null) {
		
		String adIdst = request.getParameter("adId");
		Long adId = Long.parseLong(adIdst);
		
		DataBaseServicesImpl db = new DataBaseServicesImpl();
		DAO dao = new DAO();

		List<ImageDataBase> records =  dao.ofy().query(ImageDataBase.class).filter("adId", adId).list();

		for (ImageDataBase image: records) {
		%>
			<img src=<%=image.getImageUrl()%>>
		<%		
		}
		
	}

	
%>

	
</div>

</div>
<div id="leftcontainer">
<h2 ><A name="descripcion"></A>Vendo linda casa en Heredia Centro</h2>
<p>Linda vista cerca de todos los servicios, bodegas y almacenes en la ciudad. No se pierda esta gran oportunidad!</p>
<A name="caracteristicas"></A>
<table width="100%" border="0">
  <tr>
    <td>
        <dl>
        <dt>Precio: </dt>
        <dd>US$500,000</dd>
         
        <dt>Provincia: </dt>
        <dd>San Jose</dd>
     
        <dt>Cantón: </dt>
        <dd>Vazquez de Coronado</dd>
     
        <dt>Uso:</dt>
        <dd>Casa</dd>
        
        <dt>Construcción:</dt>
        <dd>150 mts2</dd>
    
        <dt>Lote:</dt>
        <dd>208 mts2</dd>           
        </dl>
    </td>
    <td>
        <dl>
        <dt>Precio: </dt>
        <dd>US$500,000</dd>
         
        <dt>Provincia: </dt>
        <dd>San Jose</dd>
     
        <dt>Cantón: </dt>
        <dd>Vazquez</dd>
     
        <dt>Uso:</dt>
        <dd>Casa</dd>
        
        <dt>Construcción:</dt>
        <dd>150 mts2</dd>
    
        <dt>Lote:</dt>
        <dd>208 mts2</dd>           
        </dl>
    
    </td>
  </tr>
</table>

</div>
<div id="leftcontainer">
<A name="mapa"></A><h2>Mapa</h2>
<div id="map_canvas" style="width:100%; height:100%"></div>
</div>
<div id="leftcontainer">
<A name="tour"></A><h2>Tour virtual</h2>
<iframe frameborder="0" src="http://photosynth.net/embed.aspx?cid=e9cef6b3-c36a-484b-90d3-5fbc602d4309&delayLoad=true&slideShowPlaying=false" width="100%" height="300">
</iframe>
</div>
<div id="leftcontainer">
<A name="video"></A><h2>Video</h2>
</div>
</div>
</div>
<div id="sidebar">
<div id="container">
<dl>
    <dt>Precio: </dt>
    <dd>US$500,000</dd>
     
    <dt>Provincia: </dt>
    <dd>San Jose</dd>
 
    <dt>Cantón: </dt>
    <dd>Vazquez de Coronado</dd>
 
    <dt>Uso:</dt>
    <dd>Casa</dd>
    
    <dt>Construcción:</dt>
    <dd>150 mts2</dd>

    <dt>Lote:</dt>
    <dd>208 mts2</dd>
        
</dl>
</div>
     <div id="rightcontainer">
    <h2><A name="contactar"></A>Contactar al vendedor</h2>

<fieldset class="form noPrint">
	<div>
    <p>Para una respuesta inmediata llame ahora al 83&nbsp;329197</p>
    </div>
    <div class="form_row">
         <div class="form_field">
            <label for="nombre">Nombre</label>
            <input class="form_input input_medium" type="text" name="nombre"  />
        </div>
    </div>    
    <div class="form_row">
        <div class="form_field">
            <label for="telefono">Telefono</label>
            <input class="form_input input_medium" type="text" name="telefono"  />
        </div>
    </div>
    <div class="form_row">
        <div class="form_field">
            <label for="correo">Correo</label>
            <input class="form_input input_large" type="text" name="correo" />
        </div>
    </div>
    <div class="form_row">
        <div class="form_field">
            <label for="content">Asunto</label>
            <textarea class="form_input input_large" name="content" rows="3" cols="32" ></textarea>
        </div>
    </div>
     <div class="form_row form_last_row">
        <div class="form_field">
            <input  type="submit" id="submit_account" class="form_button input_medium" value="Enviar" />
        </div>
    </div>
</fieldset>



</div>
</div>
<div id="footer">
<p>Footer</p>
</div>
</div>
</body>
  

<script type="text/javascript">
$('#gallery').galleria({
width: 100%,
height: 100% --I made heights match
});
</script>

<script>
    Galleria.loadTheme('galleria/themes/twelve/galleria.twelve.min.js');
    Galleria.run('#galleria');
    </script>    

<%
    
%>
  </body>
</html>