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
	
	String rentAndSalePrice = "US$0";
	
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
	
	// VIDEOS AND VIRTUAL TOUR
	
	Boolean showVideo = false;
	
	String addressTable = " ";
	String detailsTable = " ";
	String contactTable = " ";
	
	Integer viewCount = 1;

	
	DataBaseServicesImpl db = new DataBaseServicesImpl();
	Ad ad = new Ad();
	if (request.getParameter("adId") != null) {
		
		adIdst = request.getParameter("adId");
		adId = Long.parseLong(adIdst);
		if (adId != null ) {
	ad = db.getAdById(adId);
	
	if (ad!= null) {
		db.addNewVisit(adId);
		
		showName = ad.getShowName();
		showEmail = ad.getShowEmail();
		showAddress = ad.getShowAddress();
		showPhone = ad.getShowPhone();
		
		showVideo = ad.showVideo();
		
		imageUrl = ad.getImageUrl();
		
		if (showName) name = ad.getFirstName() + " "+ ad.getLastName();
		if (showPhone) phone = ad.getPhone();
		if (showEmail) email = ad.getEmail();
		
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
		county = db.getDivision2ById(ad.getCountyId());

		city = ad.getCity();
		residential = ad.getResidential();
		if (showAddress) address = ad.getAddress();

		addressTable = db.createAddressContent(ad);
		detailsTable = db.createDetailsContent(ad);
		contactTable = db.createContactContent(ad);
		
		viewCount = ad.getViewCount() ;
		
		if (viewCount==null) viewCount = 1;
		
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

<meta name="description" content="<%=description%>" />
<meta name="keywords"
	content="<%=propertySubType%>, <%=state%>, <%=county%>, venta, alquiler" />
<meta name="title"
	content="<%=propertySubType%> <%=saleType%> en <%=state%>, <%=county%> - crCasas" />
<meta name="rating" content="general" />
<meta name="revisit-after" content="1 days" />
<meta name="language" content="es" />
<meta http-equiv="content-language" content="es" />

<script type="text/javascript">
			window.onload = function() {
			    if (parent) {
			        var oHead = document.getElementsByTagName("head")[0];
			        var arrStyleSheets = parent.document.getElementsByTagName("style");
			        for (var i = 0; i < arrStyleSheets.length; i++)
			            oHead.appendChild(arrStyleSheets[i].cloneNode(true));
			    }
			}
			</script>




<title><%=propertySubType%> <%=saleType%> en <%=state%>, <%=county%>
	- crCasas</title>

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

<link rel="stylesheet" href="styles.css" type="text/css">


</head>

<body>
	<!-- Wrapper/Container Table: Use a wrapper table to control the width and the background color consistently of your email. Use this approach instead of setting attributes on the body tag. -->
	<table cellpadding="0" cellspacing="0" border="0" id="backgroundTable">
    <tr class="height14">
    </tr>
	<tr>
		<td>
		<!-- Tables are the most common way to format your email consistently. Set your table widths inside cells and in most cases reset cellpadding, cellspacing, and border to zero. Use nested tables as a way to space effectively in your message. -->
		<table cellpadding="0" cellspacing="0" border="0" align="center">
			<tr>
				<td class="colorGrey" width="10" valign="top"></td>
				<td  width="580" valign="top">
                    <table cellpadding="0" cellspacing="0" border="0" align="center" id="canvasTable">
                        <tr bgcolor="white"><td>&nbsp;</td></tr>
                        <tr bgcolor="white">
                            <td  style="padding-top:0px;padding-bottom:40px; padding-left: 20px; padding-right: 20px">
                                <p>
                                <span class="floatLeft"><a href="http://www.crCasas.com" target="_blank"><img src="http://icons.iconarchive.com/icons/double-j-design/super-mono-3d/48/home-icon.png" width="48" height="48" border="0" alt="crCasas" style="margin-bottom:-8px; margin-right:-2px;padding:0"></a><span class="logo">crCasas<span class="dotComm">.com</span></span></span>
                                <span class="floatRight"><a href="http://www.crCasas.com" target="_blank"><img src="http://icons.iconarchive.com/icons/yootheme/social-bookmark/32/social-facebook-button-blue-icon.png" width="32" height="32" border="0" alt="crCasas" style="margin-top:8px; padding:0" ></a></span>
                                </p>
                            </td>
                        </tr>
                        <tr bgcolor="white">
                            <td align="left" style="padding-top:0px;padding-bottom:40px">
                                    <p class="mainTitle"><%=title%></p>
                            </td>
                        </tr>
                        <tr bgcolor="white"><td>&nbsp;</td></tr>
                           <tr><td align="center" bgcolor="white">
                                    <img class="img_fix img-thumbnail" src="<%=imageUrl%>=s520" width="80%" class="img-thumbnail center" alt="crcasas"/>
                            </td></tr>
                         <tr bgcolor="white" class="mainText"><td>
                            <p><%=rentAndSalePrice%><br/><%=address%></p>
                         </td></tr>
                         <tr bgcolor="white" ><td>
                            <table width="100%" cellspacing="0" cellpadding="5">
                                  <tbody><tr>
                                    <td  class="iconsTd" align="center" >
                                        <a href="tel:[<%=phone%>]" ><img src="http://icons.iconarchive.com/icons/martz90/circle/64/phone-icon.png" border="0" width="64" height="64" alt=""/><br/>Llame hoy!</a>
                                    </td>
                                    <td    class="iconsTd" align="center" >
                                        <a href="sms:<%=phone%>" ><img src="http://icons.iconarchive.com/icons/martz90/circle/64/facebook-messenger-icon.png" border="0" width="64" height="64" alt=""/><br/>SMS</a> 
                                    </td>
                                    <td    class="iconsTd" align="center" >
                                        <a href="mailto:<%=email%>" >  
                                            <img src="http://icons.iconarchive.com/icons/martz90/circle/64/email-icon.png" border="0" width="64" height="64" alt=""/>
                                        <br/>Email</a>                           
                                    </td>
                                </tr>
                              </tbody></table>
                         </td></tr>
                        <tr bgcolor="white"><td>&nbsp;</td></tr>
                        <tr bgcolor="white" class="detailsText"><td>
                            <p><%=description%></p>
                         </td></tr>
                        <tr bgcolor="white"><td>&nbsp;</td></tr>
                        <tr bgcolor="white"><td>&nbsp;</td></tr>
                         <tr bgcolor="white"><td align="center">
                            <table width="80%" border="0" cellspacing="0" cellpadding="10">
                                <thead>
                                    <tr>
                                        <th class="detailsTh" colspan="2">Información de contacto</th>
                                    </tr>
                                </thead>
                                <tbody>
						        <%=contactTable%>    
                                </tbody>
                            </table>
                         </td></tr>
                       <tr bgcolor="white"><td>&nbsp;</td></tr>
                        <tr bgcolor="white"><td>&nbsp;</td></tr>
                        <tr bgcolor="white"><td>&nbsp;</td></tr>
                         <tr bgcolor="white"><td align="center">
                            <table width="80%" border="0" cellspacing="0" cellpadding="10">
                                <thead>
                                    <tr>
                                        <th class="detailsTh" colspan="2">Características</th>
                                    </tr>
                                </thead>
                                <tbody>
						        <%=detailsTable%>    
                                </tbody>
                            </table>
                         </td></tr>
                       <tr bgcolor="white"><td>&nbsp;</td></tr>
                        <tr bgcolor="white"><td>&nbsp;</td></tr>
                        <tr bgcolor="white"><td>&nbsp;</td></tr>
                         <tr bgcolor="white"><td align="center">
                            <table width="80%" border="0" cellspacing="0" cellpadding="10">
                                <thead>
                                    <tr>
                                        <th class="detailsTh" colspan="2">Dirección</th>
                                    </tr>
                                </thead>
                                <tbody>
						        <%=addressTable%>   
   
                                </tbody>
                            </table>
                         </td></tr>
                        <tr bgcolor="white"><td>&nbsp;</td></tr>
                            <tr><td align="center" bgcolor="white">
                                    <p><a class="btn" href="http://www.crCasas.com#![ADID]" target="_blank">Ver más</a></p>
                            </td></tr>
                            <tr bgcolor="white"><td>&nbsp;</td></tr>
                            <tr>
                            <td>
                                <table bgcolor="#2e3237" width="100%" cellpadding="30" style="background:#2e3237"><tbody>
                                    <tr>
                                        <td align="center" style="padding:30px">
                                            <p style="font-family:'Helvetica','Arial',sans-serif;color:#ccc;font-size:14px;line-height:24px;text-align:center">
                                                <a href="http://www.crcasas.com" style="text-decoration:none;color:#ffffff" target="_blank">Desinscribirse</a>&nbsp;|&nbsp; 
                                                <a href="http://www.crcasas.com" style="text-decoration:none;color:#ffffff" target="_blank">Buscar</a>&nbsp;|&nbsp; 
                                                <a href="http://www.crCasas.com/anunciar" style="text-decoration:none;color:#ffffff" target="_blank">Anunciar</a>&nbsp;|&nbsp; 
                                                <a href="mailto:ventas@gmail.com" style="text-decoration:none;color:#ffffff" target="_blank">Contáctenos</a>
                                            </p>
                                            <p style="font-family:'Helvetica','Arial',sans-serif;color:#ccc;font-size:14px;text-align:center">
                                                © 2013 Novatec Internacional, San José, Costa Rica</p>
                                        </td>
                                    </tr>
                                </tbody></table>
                            </td>
                            </tr>
                            <tr>
                                <td>
                                    <table width="100%" cellpadding="30" style="margin-top:10px">
                <tbody><tr>
                    <td align="center">
                                                <h3 style="padding:0;margin:0;text-align:center;font-family:'Helvetica Neue','Helvetica','Arial',sans-serif;font-size:24px;font-weight:300;letter-spacing:-0.05em;text-align:center;color:#fe6d4c">Anuncie su propiedad por correo</h3>
                        <p style="padding:0;margin:0;font-family:'Helvetica Neue','Helvetica','Arial',sans-serif;font-size:16px;line-height:24px;font-weight:300;letter-spacing:-0.05em;text-align:center;color:#666"><br>Consiga más clientes. Envíe por correo un anuncio profesional de su propiedad, con este nuevo servicio gratuito de crCasas<br><a href="http://www.crcasas.com/anunciar" style="text-decoration:none;color:#000" target="_blank">Crear anuncio →</a></p>

                        <div style="padding-top:20px;padding-bottom:10px;margin:0"><hr style="margin:0;min-height:0px;zoom:1;overflow:hidden;border-top:1px solid #dedede;border-bottom:1px solid #fff"></div>
                                                
                        <p style="margin-top:10px;font-family:'Helvetica Neue','Helvetica','Arial',sans-serif;font-size:14px;font-weight:300;line-height:24px;text-align:center;color:#666">crCasas suministra tecnología para ayudarlo a<br>comprar, vender o alquilar propiedades en Costa Rica.<br>Conozca más en <a href="http://www.crCasas.com/" style="text-decoration:none;color:#000" target="_blank">www.crCasas.com</a>.
                        </p>
                        <p style="margin:0;padding:10px 0;font-family:'Helvetica Neue','Helvetica','Arial',sans-serif;color:#999;text-align:center;font-size:12px;line-height:16px">Por favor no responda a este correo.</p>
                    </td>
                </tr>
            </tbody></table>
                                </td>
                            </tr>
		            </table>
                </td>
				<td class="colorGrey" width="10" valign="top"></td>
			</tr>
		</table>
		<!-- End example table -->
		</td>
	</tr>
    <tr class="height14">
    </tr>
	</table>
	<!-- End of wrapper table -->
</body>
</html>

