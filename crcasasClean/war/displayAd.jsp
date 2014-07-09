<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="cr.novatec.crcasas.server.rpc.DataBaseServicesImpl"%>
<%@ page import="cr.novatec.crcasas.server.rpc.DAO"%>
<%@ page import="cr.novatec.crcasas.client.database.ImageDataBase"%>
<%@ page import="cr.novatec.crcasas.client.database.Ad"%>
<%@ page import="com.google.appengine.api.datastore.GeoPt"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.DecimalFormat"%>


<%
	DecimalFormat formatter = new DecimalFormat("###,###,###");

	DAO dao = new DAO();
	Long adId = null;
	
	String adIdst = "";
	
	String name = "";
	String phone = "";
	String email = "";
	String title = "";
	String description = "";

	Boolean showName = true;
	Boolean showPhone = true;
	Boolean showEmail = true;
	Boolean showAddress = true;
	
	Boolean showGalleria = false;
	Boolean isHouse = true;
	

	Boolean localCurrency = true;
	String price = "US$0";
	String rentPrice = "US$0";
	
	String rentAndSalePrice = "USD 0";
	
	String currency = "USD";
	
	if (localCurrency) currency = "CRC";

	Boolean rent = true;
	Boolean sale = true;
	
	String saleType = "a la venta";
	
	Double priceDouble = 0d;

	
	Integer lotSize = 0;
	Integer frontSize =0;

	Integer constructionSize = 0;
	Integer bathRooms = 0;
	Integer partialBaths = 0;
	Integer bedRooms = 0;
	Integer garages = 0;
	Integer floors = 0;

	Integer yearBuild = 0;
	
	String state = "";
	String county = "";

	String city = "";
	String residential = "";
	String address = "";

	String pubDate = "";

	// PROPERTY SECTION
	
	String listingType = ""; // N=New R=Resell
	String propertyType = ""; // L = Lot B = Building H = Home;
	String propertySubType = ""; // For Home = (H)ouse, (A)partment, (C)ondominum
			
			
	String imageUrl = ""; 	
	
	// LAT LONG
	
	Float lat = 9.616667f;
	Float lng = -84.633331f;
	
	// VIDEOS AND VIRTUAL TOUR
	
	Boolean showVideo = false;
	Boolean showPhotosyn = false;
	
	String video01 = null;
	String video02 = null;
	String video03 = null;
	String photosyn01 = null;
	String photosyn02 = null;
	String photosyn03 = null;
	
	
	Integer viewCount = 1;

	
	DataBaseServicesImpl db = new DataBaseServicesImpl();
	Ad ad = new Ad();
	if (request.getParameter("adId") != null) {
		
		adIdst = request.getParameter("adId");
		adId = Long.parseLong(adIdst);
		if (adId != null ) {
			ad = db.getAdBySourceKey(adIdst);
			adId = ad.getId();
	
	if (ad!= null) {
		//db.addNewVisit(adId);
		
		showName = ad.getShowName();
		showEmail = ad.getShowEmail();
		showAddress = ad.getShowAddress();
		showPhone = ad.getShowPhone();
		
		showVideo = ad.showVideo();
		showPhotosyn = ad.showPhotoSyn();
		
		if (showName) name = ad.getFirstName() + " "+ ad.getLastName();
		if (showPhone) phone = ad.getPhone();
		if (showEmail) email = ad.getEmail();
		
		
		imageUrl = ad.getImageUrl();
		
		listingType = ad.getListingTypeName(); 
		propertyType = ad.getPropertyType(); 
		
		if (propertyType.equals("L")) isHouse = false;
		
		propertySubType = ad.getPropertySubTypeName(); 
		
		
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
		
		//price = ad.getCurrencyFmt()+ad.getPrice().toString();			

		// CARACTERISTICAS
		
		description = ad.getDescription();
		title = ad.getTitle();
		
		lotSize = ad.getLotSize();
		frontSize =ad.getFrontSize();

		constructionSize = ad.getConstructionSize();
		bathRooms = ad.getBathRooms();
		partialBaths = ad.getPartialBaths();
		bedRooms = ad.getBedRooms();
		garages = ad.getGarages();
		floors = ad.getFloors();
		yearBuild = ad.getYearBuilt();
		
		pubDate  = ad.getPubDate().toString();


		// ADDRESS SECTION
		state = db.getDivision1ById(ad.getStateId());
		if (state!=null) state = state.substring(0, 1).toUpperCase() + state.substring(1);
		county = db.getDivision2ById(ad.getCountyId());
		if (county!=null) county = county.substring(0, 1).toUpperCase() + county.substring(1);

		city = ad.getCity();
		residential = ad.getResidential();
		if (showAddress) address = ad.getAddress();

		GeoPt geo = ad.getGeoPoint();
		
		if (geo!=null) {
			lat = geo.getLatitude();
			lng = geo.getLongitude();
			
		} else {
			geo = db.getDivision2Geo(ad.getCountyId());
			
			if (geo!=null) {
				lat = geo.getLatitude();
				lng = geo.getLongitude();					
			}
			
		}
		
		lng = Math.abs(lng) * -1;
		
		// VIDEOS SECTION
		video01 = ad.getVideo01();
		video02 = ad.getVideo02();
		video03 = ad.getVideo03();
		photosyn01 = ad.getPhotosyn01();
		photosyn02 = ad.getPhotosyn02();
		photosyn03 = ad.getPhotosyn03();
		
		viewCount = ad.getViewCount() ;
		
		if (viewCount==null) viewCount = 1;
		
	} 
	
	
		} else {
%>
<jsp:forward page="notfound.html">
	<jsp:param name="adId" value="<%=adId%>" />
</jsp:forward>
<%
	}
		
	} else {
%>
<jsp:forward page="notfound.html">
	<jsp:param name="adId" value="<%=adId%>" />
</jsp:forward>
<%
	}
%>




<html>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<head>

<meta name="description" content="<%=title%>. <%=description%>" />
<meta name="keywords"
	content="<%=propertySubType%>, <%=state%>, <%=county%>, venta, alquiler" />
<meta name="title"
	content="<%=county%>, <%=state%> - crCasas" />
<meta name="rating" content="general" />
<meta name="revisit-after" content="1 days" />
<meta name="language" content="es" />
<meta http-equiv="content-language" content="es" />


 <meta name="twitter:card" content="photo">
 <meta name="twitter:title" content="<%=title%>">
 <meta name="twitter:image" content="<%=imageUrl%>=s360">
 <meta name="twitter:title" content="<%=propertySubType%> <%=saleType%> en <%=state%>, <%=county%>">
 <meta name="twitter:description" content="Revisen esta propiedad: 
 			<%	if (sale && !rent) {
 				%>
 					<%=price%>
 				<%
 			}
 				if (rent && !sale) {
 	 			%>
 					<%=rentPrice%>
 				<% 					
			}
 				if (sale && rent) {
 	 	 			%>
 	 					<%=price%> / <%=rentPrice%>
 	 				<% 					
 	 			}	
			%>
 , <%=description%>">

<meta name="twitter:url" content="http://test.crcasas.com/#!anuncio=<%=adId%>">



<link type="text/css" rel="stylesheet" href="/jsps/jsps.css">

<style type="text/css">
.galleria {
	height: 467px;
}
</style>

<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="/galleria/galleria-1.2.9.min.js"></script>

<script type="text/javascript"
	src="http://maps.googleapis.com/maps/api/js?sensor=true"> </script>
<script type="text/javascript">
    	var map;
      	function initialize() {
        	var myOptions = {

        	  	center: new google.maps.LatLng(<%=lat%>, <%=lng%>),
          		zoom: 14,
          		mapTypeId: google.maps.MapTypeId.ROADMAP
        	};
         	var map = new google.maps.Map(document.getElementById("map_canvas"),
            myOptions);

            
      }

        function placeMarker(location) {
            var marker = new google.maps.Marker({
            position: location,
            map: map
        });
        map.setCenter(location);
    }
    </script>

<title><%=propertySubType%> <%=saleType%>
	en <%=county%>, <%=state%>
</title>

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-23115758-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</head>

<body onload="initialize()">

	<div id="fb-root"></div>
	<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=174633095925807";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));
</script>

	<script type="text/javascript">
  window.___gcfg = {lang: 'es'};

  (function() {
    var po = document.createElement('script'); po.type = 'text/javascript'; po.async = true;
    po.src = 'https://apis.google.com/js/plusone.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(po, s);
  })();
</script>

	<div id="wrap" class="shadow" itemscope
		itemtype="http://schema.org/Offer">
		<meta itemprop='name' content='<%=propertySubType%> <%=saleType%> en <%=state%>, <%=county%>'/> 
		
		<div id="header">
			<div class="adNumber" align="left" style="width: 50%; float: left;">
				Anuncio No.
				<%=adIdst%>
			</div>
			<div class="adNumber" align="right" style="width: 50%; float: right;">
				<a href="http://www.crcasas.com" target="_blank">crCasas.com</a>
			</div>
			<div align="left" style="width: 50%; float: left;" class="capitalize">
				<h1><%=state%>,
					<%=county%></h1>
			</div>
			<%
				if (sale && !rent) {
			%>
			<div align="right" style="width: 50%; float: right;">
				<h1><%=price%></h1>
			</div>
			<div style="clear: both;"></div>
			<%
				} 
																	if (rent && !sale) {
			%>
			<div align="right" style="width: 50%; float: right;">
				<h1 itemprop="price"><%=rentPrice%></h1>
			</div>
			<div style="clear: both;"></div>
			<%
				}
																	
																	if (rent && sale) {
			%>
			<div align="right" style="width: 50%; float: right;">
				<h1 ><%=price%> / <%=rentPrice%></h1>
			</div>
			<div style="clear: both;"></div>
			<%
				} 
																	if (!rent && sale) {
			%>
			<div align="right" style="width: 50%; float: right;">
				<h1 ><%=price%></h1>
			</div>
			<div style="clear: both;"></div>
			<%
				}
			%>


			<%
				if (adId != null) {
					

					List<ImageDataBase> records =  dao.ofy().query(ImageDataBase.class).filter("adId", adId).order("displayOrder").list();
					
					if ( ! records.isEmpty() ) {
				showGalleria = true;
			%>
			<div class="galleria">
				<%
					for (ImageDataBase image: records) {
				%>
				<img itemprop="image" src=<%=image.getImageUrl()%>
					<%if (image.getCaption()!=null) {%>
					data-title=<%=image.getCaption()%>
					<%}
						     	if (image.getDescription()!=null) {%>
					data-description=<%=image.getCaption()%> <%}%> alt="<%=propertySubType%> <%=saleType%> en <%=state%>, <%=county%> - crCasas">
				<%
					}
				%>

			</div>
			<%
				}		
				}
			%>


			<ul id="nav">
				<li><a href="#descripcion">Descripción</a></li>
				<li><a href="#caracteristicas">Características</a></li>
				<li><a href="#vendedor">Vendedor</a></li>
				<li><a href="#mapa">Mapa </a></li>

				<%
					if (showPhotosyn ) {
				%>
				<li><a href="#tour">Tour Virtual</a></li>
				<%
					}
				%>

				<%
					if (showVideo ) {
				%>
				<li><a href="#video">Video</a></li>
				<%
					}
				%>

			</ul>


			<br> <br> <br>
			<ul id="nav2">

				<li><a href="http://www.crCasas.com">Encuentre mas
						propiedades en crCasas.com</a></li>


			</ul>

		</div>



		<div id="main" class="shadow">
			<div style="padding: 10px">

				<div class="leftcontainer" style="width: 100%">

					<div>
						<h2>
							<A name="descripcion"></A><%=title%></h2>
						<p itemprop="description"><%=title%>. <%=description%></p>
						<A name="caracteristicas"></A>
					</div>
				</div>
				<div class="leftcontainer" style="width: 100%; height: 500px;">

					<A name="mapa"></A>
					<h2>Mapa</h2>
					<div id="map_canvas"></div>
				</div>

				<%
					if (showVideo ) {
				%>
				<div class="leftcontainer">
					<A name="video"></A>
					<h2>Video</h2>

					<%
						if (video01!=null ) {
					%>
					<iframe width="430" height="245"
						src="http://www.youtube.com/embed/<%=video01%>" frameborder="0"
						allowfullscreen> </iframe>

					<%
						}
							if (video02!=null ) {
					%>
					<iframe width="430" height="245"
						src="http://www.youtube.com/embed/<%=video02%>" frameborder="0"
						allowfullscreen> </iframe>

					<%
						}
							if (video03!=null ) {
					%>
					<iframe width="430" height="245"
						src="http://www.youtube.com/embed/<%=video03%>" frameborder="0"
						allowfullscreen> </iframe>
					<%
						}
					%>
				</div>
				<%
					}
				%>



				<%
					if (showPhotosyn ) {
				%>
				<div id="leftcontainer">
					<A name="tour"></A>
					<h2>Tour virtual</h2>

					<%
						if (photosyn01!=null ) {
					%>
					<iframe frameborder="0"
						src="http://photosynth.net/embed.aspx?cid=<%=photosyn01%>&delayLoad=true&slideShowPlaying=false"
						width="430" height="245"> </iframe>
					<%
						}	

							if (photosyn02!=null ) {
					%>
					<iframe frameborder="0"
						src="http://photosynth.net/embed.aspx?cid=<%=photosyn02%>&delayLoad=true&slideShowPlaying=false"
						width="430" height="245"> </iframe>
					<%
						}	

							if (photosyn03!=null ) {
					%>
					<iframe frameborder="0"
						src="http://photosynth.net/embed.aspx?cid=<%=photosyn03%>&delayLoad=true&slideShowPlaying=false"
						width="430" height="245"> </iframe>
					<%
						}
					%>
				</div>

				<%
					}
				%>



			</div>
		</div>


		<div id="sidebar">

			<div class="rightcontainer">
				<div class="fb-like" style="float: left"
					data-href="http://www.crcasas.com/anuncio/<%=adIdst%>"
					data-send="true" data-layout="button_count" data-width="135"
					data-show-faces="false"></div>
				<div style="float: right">
					<g:plusone></g:plusone>
				</div>
			</div>
			<br />
			<div class="rightcontainer">
				<table>
					<tr>
						<td class="title">Visitas:</td>
						<td class="text"><%=viewCount%></td>
					</tr>

				</table>
				<br />
			</div>
			<div class="rightcontainer" itemprop="seller" itemscope
				itemtype="http://schema.org/Person">
				<h3>
					<A name="vendedor"></A>Vendedor
				</h3>

				<table>
					<tr>
						<td class="title">Vendedor:</td>
						<td class="text" itemprop="name"><%=name%></td>
					</tr>
					<tr>
						<td class="title">Teléfono:</td>
						<td class="text" itemprop="telephone"><%=phone%></td>
					</tr>
					<tr>
						<td class="title">Correo:</td>
						<td class="text" itemprop="email"><%=email%></td>
					</tr>
				</table>
			</div>

			<div class="container">
				<h3>
					<A name="caracteristicas"></A>Caracteristicas
				</h3>
				<table>

					<%
						if (sale) {
					%>
					<tr>
						<td class="title">Precio venta:</td>
						<meta itemprop="priceCurrency" content="<%=currency%>" /> 
						<td itemprop="price" class="text"><%=price%></td>
					</tr>
					<%
						}
					%>

					<%
						if (rent) {
					%>
					<tr>
						<td class="title">Alquiler:</td>
						<meta itemprop="priceCurrency" content="<%=currency%>" />
						<td itemprop="price" class="text"><%=rentPrice%></td>
					</tr>
					<%
						}
					%>


					<tr>
						<td class="title">Provincia:</td>
						<td class="text capitalize"><%=state%></td>
					</tr>
					<tr>
						<td class="title">Cantón:</td>
						<td class="text capitalize"><%=county%></td>
					</tr>

					<%
						if (isHouse ) {
					%>
					<tr>
						<td class="title">Uso:</td>
						<td class="text"><%=propertySubType%></td>
					</tr>
					<tr>
						<td class="title">Construcción:</td>
						<td class="text"><%=constructionSize%> mts2</td>
					</tr>
					<%
						}
					%>

					<tr>
						<td class="title">Lote:</td>
						<td class="text"><%=lotSize%> mts2</td>
					</tr>

					<tr>
						<td class="title">Frente:</td>
						<td class="text"><%=frontSize%> mts2</td>
					</tr>

					<%
						if (isHouse ) {
					%>
					<tr>
						<td class="title">Baños:</td>
						<td class="text"><%=bathRooms%></td>
					</tr>
					<tr>
						<td class="title">Medios Baños:</td>
						<td class="text"><%=partialBaths%></td>
					</tr>
					<tr>
						<td class="title">Habitaciones:</td>
						<td class="text"><%=bedRooms%></td>
					</tr>

					<tr>
						<td class="title">Garages:</td>
						<td class="text"><%=garages%></td>
					</tr>
					<tr>
						<td class="title">Pisos:</td>
						<td class="text"><%=floors%></td>
					</tr>
					<tr>
						<td class="title">Año de construcción:</td>
						<td class="text"><%=yearBuild%></td>
					</tr>
					<%
						}
					%>


				</table>

			</div>



			<div class="rightcontainer">
				<h3>
					<A name="contactar"></A>Contactar al vendedor
				</h3>

				<form action="http://www.crcasas.com/sendMail.jsp" method="post"
					target="result">
					<fieldset class="form noPrint">
						<%
							if (showPhone ) {
						%>
						<div>
							<p class="form_text">
								Para una respuesta inmediata llame ahora al
								<%=phone%></p>
						</div>
						<%
							}
						%>
						<div class="form_row">
							<div class="form_field">
								<label for="nombre">Nombre</label> <input
									class="form_input input_medium" type="text" name="name" />
							</div>
						</div>
						<div class="form_row">
							<div class="form_field">
								<label for="telefono">Telefono</label> <input
									class="form_input input_medium" type="text" name="phone" />
							</div>
						</div>
						<div class="form_row">
							<div class="form_field">
								<label for="correo">Correo</label> <input
									class="form_input input_large" type="text" name="from" /> <input
									class="form_input input_large" type="hidden" name="to"
									value="<%=email%>" /> <input class="form_input input_large"
									type="hidden" name="adId" value="<%=adIdst%>" />

							</div>
						</div>
						<div class="form_row">
							<div class="form_field">
								<label for="content">Asunto</label>
								<textarea class="form_input input_large" name="subject" rows="3"
									cols="32"></textarea>
							</div>
						</div>
						<div class="form_row form_last_row">
							<div class="form_field">
								<input type="submit" id="submit_account" class="button"
									value="Enviar" />
							</div>
						</div>
					</fieldset>
				</form>


				<iframe NAME="result" style="visibility: hidden; display: none"
					width="3" height="3"> </iframe>

			</div>
			
			<div  itemprop="availableAtOrFrom" itemscope itemtype="http://schema.org/Place">
	

			<div class="container" itemprop="address" itemscope itemtype="http://schema.org/PostalAddress">
				<h3>
					<A name="direccion"></A>Direccion
				</h3>
				<meta itemprop='addressCountry' content='CRC'/>
				<table>
					<tr>
						<td class="title">Provincia:</td>
						<td itemprop="addressRegion" class="text capitalize"><%=state%></td>
					</tr>
					<tr>
						<td class="title">Cantón:</td>
						<td itemprop="addressLocality" class="text capitalize"><%=county%></td>
					</tr>

					<%
						if (city!=null ) {
					%>
					<tr>
						<td class="title">Ciudad:</td>
						<td class="text"><%=city%></td>
					</tr>
					<%
						}
					%>

					<%
						if (residential!=null ) {
					%>
					<tr>
						<td class="title">Residencial:</td>
						<td class="text"><%=residential%></td>
					</tr>
					<%
						}
					%>


					<%
						if (address!=null ) {
					%>

					<tr>
						<td class="title">Dirección:</td>
						<td class="text"><%=address%></td>
					</tr>
					<%
						}
					%>

				</table>

			</div>

			<div itemprop="geo" itemscope itemtype="http://schema.org/GeoCoordinates">				      
				<meta itemprop="latitude" content="<%=lat%>" />					      
				<meta itemprop="longitude" content="<%=lng%>" />				    
			</div>
			
			</div>

			<div class="container">
				<h3>
					<A name="direccion"></A>Ver más Propiedades
				</h3>
				<p>
					Vea más de 10,000 propiedades en <a href="http://www.crcasas.com"
						target="_blank">crCasas.com</a>
				</p>
				<p>
					Anuncie su propiedad gratuitamente en <a
						href="http://www.crcasas.com" target="_blank">crCasas.com</a>
				</p>

			</div>


		</div>
		<div id="footer">
			<hr style="color: #996600; background-color: #996600; height: 4px" />
			<p>&copy; By Ventas crCasas. www.crCasas.com Derechos reservados.
				Consultas a ventas@crCasas.com. Encuentre más propiedades en
				crCasas.com</p>

		</div>
	</div>

	<%
		if (showGalleria ) {
	%>

	<script>
		    Galleria.loadTheme('/galleria/themes/classic/galleria.classic.min.js');
		    Galleria.configure({
				width: 700,
				height: 467 //--I made heights match		    
		    });
		    
		    Galleria.run('.galleria');
		</script>
	<%
		}
	%>

</body>
</html>