package cr.novatec.crcasas.client;


public interface Parameters {
	
	// http://www.iconfinder.com/icondetails/42167/32/br_prev_icon
	
	public static final int MIN_AD_ID = 1001;
	
	//  public static final String HOST_DOMAIN = "http://localhost:8888/";
	
	public static final String AD_HASHTAG =  "#!anuncio=";

//  public static final String HOST_DOMAIN = "http://crcasastest1.appspot.com";
	
	public static final String HOST_DOMAIN = "http://www.crcasas.com";


	public static final int MAX_AD_ID = 999999;
	
	public static final String ADSENSE_AD1 = 	"<script type='text/javascript'>"+
												"<!--"+
												"google_ad_client = 'ca-pub-2739622158635605';"+
												"/* Novatec */"+
												"google_ad_slot = '8344340237';"+
												"google_ad_width = 120;"+
												"google_ad_height = 600;"+
												"//-->"+
												"</script>"+
												"<script type='text/javascript'"+
												"src='http://pagead2.googlesyndication.com/pagead/show_ads.js'>"+ 
												"</script>";
	
	public static final String ADSENSE_AD2 = 	"<script type='text/javascript'>"+
	"<!--"+
	"google_ad_client = 'ca-pub-2739622158635605';"+
	"/* Skyscraper wide */"+
	"google_ad_slot = '3213811892';"+
	"google_ad_width = 160;"+
	"google_ad_height = 600;"+
	"//-->"+
	"</script>"+
	"<script type='text/javascript'"+
	"src='http://pagead2.googlesyndication.com/pagead/show_ads.js'>"+
	"</script>";
	
    static int EXCH_RATE = 515;
    
	public static final Boolean DEVELOPMENT = false ;

	public static final Integer CANVAS_TOP_MARGIN = 15 ;

	public static final Integer CANVAS_WIDTH = 1000 ;
	public static final Integer CANVAS_HEIGHT = 950 ;
	public static final Integer CANVAS_TAB_MIN_HEIGHT = 600 ;

	public static final String CANVAS_MARGIN_COLOR = "#EBEBEB" ;
	public static final String CANVAS_BACKGROUND_COLOR = "white" ;

	
	public static final Integer STYLE_TSMENU_WIDTH = 850 ;
	public static final Integer STYLE_PHOTOGRID_WIDTH = 830 ;
	public static final Integer STYLE_ADLISTGRID_WIDTH = 830 ;
	public static final String STYLE_MAP_WIDTH = "800px" ;
	public static final Integer STYLE_DISPLAYTAB_WIDTH = 850 ;

	
	public static final String CRC_CURRENCY = "\u20a1" ;
	public static final String US_CURRENCY = "$" ;

	public static final String CRC_CURRENCY_DISPLAY = "\u20a1" ;
	public static final String US_CURRENCY_DISPLAY = "US$" ;

	public static final Float CHANGE_TO_US = 0.001949F ; // 0.001949F
	public static final Integer CHANGE_TO_CR = 513 ; // 513

	public static final Float EXCHANGE_RATE_US = 0.001949F ;
	public static final Integer EXCHANGE_RATE = 513 ;
	
	public static final String AD_COUNTRY_CRC = "CRC";

	
	public static final Integer AD_SOURCE_CRCASAS = 1;
	public static final Integer AD_SOURCE_NACION = 2;
	public static final Integer AD_SOURCE_CRAIGLIST = 3;
	public static final Integer AD_SOURCE_ENCUENTRA24 = 4;
	public static final Integer AD_SOURCE_INMOBILIARIA = 5;

	public static final String[] SOURCE_NAMES = {
		"indefinido",		
		"crCasas",
		"La Nacion",
		"Craiglist",
		"Encuentra24",
		"Inmobiliaria"		
	};
	

	public static final String AD_CURRENCY_US = "USD";
	public static final String AD_CURRENCY_CRC = "CRC";
	
	public static final String AD_STATUS_ACTIVE = "A"; // ACTIVE
	public static final String AD_STATUS_PAUSE = "P"; // PAUSE
	public static final String AD_STATUS_EXPIRED = "E"; // EXPIRED
	public static final String AD_STATUS_DELETED = "D"; // DELETED
	
	
	public static final String AD_LISTINGTYPE_NEW = "N"; // NEW
	public static final String AD_LISTINGTYPE_USED = "U"; // USED

	public static final String PROPERTY_PROPERTYTYPE_LOT = "L"; // LOT
	public static final String PROPERTY_PROPERTYTYPE_HOUSE = "H"; // HOUSE
	public static final String PROPERTY_PROPERTYTYPE_BUILDING = "B"; // BUILDING

	public static final String PROPERTY_PROPERTYSUBTYPE_HOUSE = "H"; // HOUSE
	public static final String PROPERTY_PROPERTYSUBTYPE_APARTMENT = "A"; // HOUSE
	public static final String PROPERTY_PROPERTYSUBTYPE_CONDOMINIUM = "C"; // HOUSE

	public static final String PROPERTY_GEOCODE_USERDEFINED = "U"; // USER DEFINED
	public static final String PROPERTY_GEOCODE_GENERATED = "G"; // GENERATED
	public static final String PROPERTY_GEOCODE_NONE = "N"; // NONE

	public static final String PERSON_MEMBERSTATUS_ACTIVE = "A"; // ACTIVE
	public static final String PERSON_MEMBERSTATUS_NONMEMBER = "N"; // NON MEMBER
	public static final String PERSON_MEMBERSTATUS_INACTIVE = "I"; // NON MEMBER

	public static final String PERSON_GENDER_MAN = "M"; // MEN
	public static final String PERSON_GENDER_WOMEN = "W"; // WOMEN

	
	public static final int LEFT_MARGIN = 25;
	public static final int RIGHT_MARGIN = 25;
	public static final int AD_PANEL_WIDTH = 300;    // 280;

	public static final String TOP_COLOR = "#eceff6";
	
	public static final String[] MENU_ITEMS = {"Inicio", "Buscar","Anunciar","Contactar"};
	
	public static final String[] MENU_SORT = {"Ordenar por precio", "Ordenar por direccion"};

	public static final String[] MENU_FILTER_PRICE = {"Todos los precios", "Bajos", "Medios", "Altos"};

	public static final String[] BOTTON_MENU_ITEMS = {"Ayuda", "Quienes Somos", "Privacidad","Terminos de Uso"};	

	public static final String[] LEFT_MENU_ITEMS = {"Imagenes", "Mapa", "Lista", "separator", "Todos", "Bajos"};	
	
	
	public static final String[] HISTORY_PAGES_LEVEL1 = {"!inicio", "!buscar", "!ver", "!anunciar", "!muro", "!acerca"};
	
	public static final String[] HISTORY_PAGES_TITLE1 = { "Bienes Raices en Costa Rica, Casas a la venta, alquiler, lotes", 
														  "crCasas - Encuentre casas y lotes en San Jose, Alajuela, Heredia, Cartago, Puntarenas, Guanacaste y Limon", 
														  "crCasas - Miles de propiedades en venta o alquiler", 
														  "crCasas - Anuncie hoy su propiedad, casa o lote, gratuitamente", 
														  "crCasas - Escribanos", 
														  "crCasas - La pagina de Bienes Raices de Costa Rica"};

	public static final String WELCOME_TEXT =	"<p>Somos un motor de b&uacute;squeda de bienes ra&iacute;ces que "+
												"le facilita encontrar propiedades y suministra informaci&oacute;n para "+
												"ayudarlo a tomar mejores decisiones. </p><p>Utilice crCasas.com para encontrar "+
												"propiedades en Costa Rica, casas a la venta, alquileres y mucho m&aacute;s. "+
												"crCasas tambi&eacute;n es una herramienta para los due&ntilde;os y los profesionales en bienes raices, "+
												"para listar sus propiedades gratuitamente y promover sus servicios.</p>"+
												"<p>Nuestro objetivo es ser la mejor experiencia de b\u00FAsqueda de bienes "+
												"raices en la Web e innovar y mejorar continuamente.</p><p>"+
												"Si desea m&aacute;s informaci&oacute;n escribanos a ventas@crCasas.com</p><p></p>";

	public static final String LOGIN_TEXT =	
			"<div class='animated bounceInDown'><div class='tabsAd'>"+
			"<p class='tabsHeader1'>Anuncie hoy su propiedad en crCasas. Es gratis!</p>"+
			"<p class='tabsHeader2'>En pocos minutos su anuncio estar&aacute; listo para que lo vean miles de potenciales compradores</p>"+
			"<table class='tabsNormal' border='0'>"+
			"<tr>"+
			"<td valign='top'>"+
			"<img src='http://lh3.ggpht.com/2Y_vIQlEKpsgYs-9lUinqmlAMScn0myqrLYy-g570VH88DjETSu0XsnWZYCMfMeJMejVXTtBtPCjdIB8PR7Q=s250' alt='crCasas' height='200' width='200'>"+
			"  </td>"+
			"<td valign='top'>"+
			"<ul type='square'>"+
			"<li><b>crCasas</b> es uno de los sitios de Bienes Ra&iacute;ces m&aacute;s visitado en Costa Rica seg&uacute;n el sitio web <FONT COLOR='black'><i>Alexa.com</i></FONT></li>"+  
			"<li><b>No necesita crear una cuenta en crCasas</b>, ni acordarse de un nuevo password. Solo ingrese a Facebook desde crCasas y registrese</li>"+
			"  <li><b>Galer&iacute;a de im&aacute;genes</b> para su anuncio, sin l&iacute;mite de im&aacute;genes</li>"+
			"<li><b>Tour virtual</b> usando panoramas de 360&deg; de <FONT COLOR='black'><i>Photosynth</i></FONT></li>"+
			"  <li>Incluya <b>Videos</b> <FONT COLOR='black'><i>YouTube</i></FONT> de su propiedad</li>"+
			"<li><b>Botones de Facebook y Google+</b> para compartir su anuncio con sus amigos</li>"+
			"</ul>"+    
			"</td>"+
			"</tr>"+
			"</table>"+
			"<p class='tabsHeader2'><u>Ingrese desde aqu&iacute; a Facebook para iniciar</u></p>"+
			"</div></div>";	
	
	// class='animated bounceInDown'
	public static final String ABOUT_TEXT =	"<div ><center>crCasas.com es un desarrollo de Novatec Internacional.</br></br>" +
			"Si desea informaci&oacute;n adicional puede contactarnos a:</br></br>" +
			"Martha Cantillano </br></br>" +		
			"Tel&eacute;fono (506) 8332-9197</br></br>" +        				
			"ventas@crCasas.com</br></br>" +
			"Coronado, San Jos&eacute;, Costa Rica</center></div>";
	
	
	public static final String STARTPAGE_TEXT =	"<center>Mas de 20,000 propiedades<br>a la venta y alquiler"+
			"<br>en Costa Rica.<br><br>Encuentra la suya hoy<br>en crCasas.com</center>";


	public static final String STARTPAGE_TEXT2 =	"<center>crCasas es<br>un motor de busqueda<br>que le facilita encontrar propiedades."+
			"<br><br>crCasas busca por Ud.<br>en los principales sitios Web</center>";
	
	public static final String STARTPAGE_TEXT3 =	"<center>Nuestro objetivo es<br>ser la mejor experiencia de busqueda"+
			"<br>de bienes raices<br><br>e innovar y mejorar continuamente</center>";


	public static final String TITLE_TEXT =	"Una forma inteligente de encontrar casa!";
	
	// http://developers.facebook.com/setup/done?id=188014121246704&locale=es_LA
	// Nombre de la aplicación:	crCasas
	// URL de la aplicación:	http://127.0.0.1:8888/
	// identificación de aplicación:	188014121246704
	// Código secreto de la aplicación:	ec19ef98669623a070e684753930967d
		
	
	public static final Integer PHOTO_GRID_WIDTH = 186; 
	public static final Integer PHOTO_GRID_HEIGHT = 120; 
	
	public static final Integer PHOTO_PUBLISH_GRID_WIDTH = 155; 
	public static final Integer PHOTO_PUBLISH_GRID_HEIGHT = 100; 


	public static final String APPNAME = "http://www.crcasas.com"; 
	
	
	//public static final String APPNAME = "http://www.crcasas.com"; 

	//public static final String APPNAME = "http://2.crcasas1.appspot.com/; 

	//public static final String APPNAME = "http://2.novatec2014.appspot.com"; 

	//public static final String APPNAME = "http://localhost:8888/"; 

	//public static final String APPID = "114733831990534"; // ID FOR 2.NOVATEC2014.APPSPOT.COM


	public static final String NEWADSID = "ecdce63f0f33b09a9112e9c61303b83f";
	public static final String APICMKEY = "890c221fc934c17c53172d7fc1a444c6"; // ID FOR CRCASAS
	public static final String APICMCRCASASKEY = "e6e0f4205870704606bd448d31e81757";
	public static final String APINEWSALES = "2e3b56046348d7ea0e85e527be928d83";
	public static final String CMFACEBOOKID = "5c562cfa4daba3798d342f47ab859555";
	
	//public static final String APPID = "374821432573366"; // DELETED!!
	
	public static final String APPID = "174633095925807"; // ID FOR CRCASAS

	//public static final String APPID = "104922512993964"; // ID FOR CRCASAS1

	
	//public static final String APPID = "263825913721404"; // facebook developer id for for http://localhost/

	
	//Map key for http://novatec2014.appspot.com/
 	
	public static final String MAPSKEY ="ABQIAAAAjamdQgRyPynKaopa5AeeOxRn4mKIvlqiWXMHrSadE2zSG3BhjhSyb6dlUkwOIx2_qZnbSG17D765eA";
	
	//public static final String MAPSKEY = ""; // development 

	
	public static final String FB_FACEPILE = "<div id='fb-root'></div>"+
					"<script>(function(d, s, id) {"+
					  "var js, fjs = d.getElementsByTagName(s)[0];"+
					  "if (d.getElementById(id)) {return;}"+
					  "js = d.createElement(s); js.id = id;"+
					  "js.src = '//connect.facebook.net/en_US/all.js#xfbml=1';"+
					  "fjs.parentNode.insertBefore(js, fjs);"+
					"}(document, 'script', 'facebook-jssdk'));</script>"+
				"	<div align='center' class='fb-facepile' data-href='novatec2014.appspot.com' data-width='500' data-max-rows='2'></div>";
			
	public static final String FB_COMMSDEV =	"<div id='fb-root'></div><script src='http://connect.facebook.net/en_US/all.js#appId=175242679182841&amp;xfbml=1'></script>"+
												"<fb:comments href='http://127.0.0.1:8888/Serialization.html' num_posts='1' height='250' width='850'>"+
												"</fb:comments>";
	
	
	public static final String FB_ACTIVITYDEV =	"<div id='fb-root'></div><script src='http://connect.facebook.net/en_US/all.js#appId=175242679182841&amp;xfbml=1'>"+
												"</script><fb:live-stream event_app_id='175242679182841' "+
												"width='280' height='600' xid='' always_post_to_friends='false'></fb:live-stream>";
	
	public static final String FB_LIKEBOX =	"<div id='fb-root'></div>"+
											"<script>(function(d, s, id) {"+
											  "var js, fjs = d.getElementsByTagName(s)[0];"+
											  "if (d.getElementById(id)) {return;}"+
											  "js = d.createElement(s); js.id = id;"+
											  "js.src = '//connect.facebook.net/en_US/all.js#appId="+ APPID+ "&xfbml=1';"+
											  "fjs.parentNode.insertBefore(js, fjs);"+
											"}(document, 'script', 'facebook-jssdk'));</script>"+
											"<div align='center' class='fb-like' data-href='"+	APPNAME +"' data-send='true' data-width='180' data-show-faces='true'></div>";
	
	public static final String FB_LIKEBOX_XFBML = "<script src='http://connect.facebook.net/en_US/all.js#xfbml=1'></script>"+
												"<fb:like-box href='http://www.facebook.com/pages/CrCasascom/192110587485587'"+
												"width='280' show_faces='true' stream='true' header='true'></fb:like-box>";

	public static final String FB_AUTH = "user_about_me, email";


	public static final String CONTACT_TEXT = 	"<div class='contactUs'><b>Contactenos</b><br><br>Si tiene algun comentario, pregunta o sugerencia con relacion a nuestros servicios, "+
												"o sobre este sitio Web, por favor complete el formulario siguiente y le responderemos por correo electronico "+
												"en menos de dos dias habiles.<br><br>Para su conveniencia muchas de las respuestas a las preguntas frecuentes "+
												"estan siempre disponibles en el siguiente vinculo.</div>";

	public static final String CONTACTUS_TEXT = 	"<div class='contactUs'><b>Contactenos por telefono!</b><br><br>"+													
													"<b>Martha Cantillano</b><br>"+
													"<b>crCasas.com</b><br>"+
													"marthaveater@gmail.com<br><br>"+
													"Celular (506) 83-948608<br><br>"+
													"POBOX 511-2200 Coronado,<br><br>"+
													"San Jose, Costa Rica.</div>";
	
	public static final String PUBLISH_TEXT = 	"<div class='displayText'><font size = '+2'><strong>Publique sus anuncios en crCasas.com GRATUITAMENTE!</strong></font><br>"+													
													"<ul><li><strong>Exposicion gratuita</strong> a miles de personas que buscan casas<br>"+
													"<li><strong>Notificaciones por correo</strong> de personas interesadas en sus anuncios<br>"+
													"<li><strong>Anuncios con su informacion de contacto</strong>, fotos ilimitadas y video<br>"+
													"<li><strong>Reportes de actividad</strong> en todos sus anuncios, vistas, clicks y leads</ul>";

	public static final String SALE_TEXT = 	"<div class='displayText'><b>Publique sus anuncios de VENTA de casas y lotes</b><br>"+													
												"<font size = '-1'>Forma rapida y facil de publicar sus anuncios en crCasas.com. "+
												"Publique todos los anuncios que necesite. "+
												"Sus anuncios apareceran publicados en crCasas en menos de 24 horas.</font>";

	public static final String RENT_TEXT = 	"	<div class='displayText'><b>Publique sus anuncios de ALQUILER de casas</b><br>"+													
												"<font size = '-1'>Forma rapida y facil de publicar sus alquileres en crCasas.com. "+
												"Publique todos los anuncios que necesite. "+
												"Sus anuncios apareceran publicados en crCasas en menos de 24 horas.</font>";

	public static final String AD1_TEXT =		"<script type='text/javascript'><!--"+
												"google_ad_client = 'ca-pub-2739622158635605';"+
												"/* Test */"+
												"google_ad_slot = '3626960281';"+
												"google_ad_width = 300;"+
												"google_ad_height = 250;"+
												"//-->"+
												"</script>"+
												"<script type='text/javascript'"+
												"src='http://pagead2.googlesyndication.com/pagead/show_ads.js'>"+
												"</script>";
	
	public static final String CRCASAS_AD1 =	"<div class='crCasasAd'><font size = '+1'>crCasas.com</font><br>"+
												"La forma inteligente de encontrar casa y lote en Costa Rica!</div>";

	public static final String CRCASAS_AD2 =	"<div class='crCasasAd'>"+
												"Nuestro objetivo es ser la mejor experiencia de b\u00FAsqueda de bienes "+
												"raices en la Web e innovar y mejorar continuamente.</div>";

	public static final String CRCASAS_AD3 =	"<div class='crCasasAd'><font size = '-1'>Somos un motor de busqueda de bienes raices que "+
												"le facilita encontrar propiedades y suministra informacion para "+
												"ayudarlo a tomar mejores decisiones.</font></div>";

	
	public static final String HOME_CRCASAS =	"crCasas.com<br>"+
												"La forma inteligente de encontrar casa y lote en Costa Rica";

	public static final String HOME_BUYERS_TITLE =	"<a class='fbtab'>Compradores</a>";

	public static final String HOME_BUYERS =	"<br><br><br><a class='fbtab'>Compradores</a><br><br>Diganos que necesita y nosotros buscaremos en la Web por Ud "+
												"y le suministraremos informacion que le ayude a tomar una mejor decision.</div>";

	public static final String HOME_SELLERS =	"<br><br><br><a class='fbtab'>Vendedores</a><br><br>"+
												"Anuncie su propiedad en crCasas.com, "+
												"de forma gratuita. Su anuncio estara expuesto a miles de visitantes. ";
												

	public static final String HOME_AGENTS =	"<br><br><br><a class='fbtab'>Agentes</a><br><br>"+
												"Subscribase a crCasas.com, e "+
												"incremente sus ventas aprovechando al maximo la tecnologia Web.<br>"+
												"Pronto MLS (Multiple Listing Service) y listing syndication";

	public static final String HOME_DEVELOPERS ="<br><br><br><a class='fbtab'>Desarrolladores</a><br><br>"+
												"Publique su desarrollo con nosotros, y sea parte "+
												"del directorio mas completo de propiedades en Costa Rica.";
	
	
	public static final String NOT_LOGGED = "Para ingresar nuevas propiedades es requisito tener una cuenta en facebook."+
											"Si ya tiene cuenta en facebook utilice el boton de login de arriba para ingresar. Si todavia no tiene cuenta en facebook "+
										    "puede utilizar el mismo boton para solicitar una cuenta.";

	public static final String END_PUBLISH = "Felicitaciones! Ha incluido toda la informacion necesaria"+
											 " para publicar el anuncio. Presione el boton de abajo para guardar su anuncio."+
											 " Si lo necesita, puede modificar el anuncio en cualquier momento.";

	
 
    
    public static final String WELCOME_VIDEO = "<object width='425' height='270'>"+
    											"<param name='movie' value='http://www.youtube.com/v/f6tVKlyYhhM?version=3&autohide=1&showinfo=0&loop=1&playlist=f6tVKlyYhhM'></param>"+
    				    						"<embed src='http://www.youtube.com/v/f6tVKlyYhhM?version=3&autohide=1&showinfo=0&loop=1&playlist=f6tVKlyYhhM' type='application/x-shockwave-flash' allowscriptaccess='always'"+
    											"width='425' height='270'></embed></object>";

    public static final String WELCOME_VIDEO2 = "<iframe title='YouTube video player' width='400' height='300' src='http://www.youtube.com/embed/f6tVKlyYhhM?version=3&autoplay=1&controls=0&disablekb=1&showinfo=0&loop=1&playlist=f6tVKlyYhhM' frameborder='0' allowfullscreen></iframe>";
 
    public static final String WELCOME_VIDEO3 = "http://www.youtube.com/embed/f6tVKlyYhhM?version=3&autoplay=1&controls=0&disablekb=1&showinfo=0&loop=1&playlist=f6tVKlyYhhM";
    
    public static final String WELCOME_VIDEO4 = "<video width='320'  height='240' >"+
    											"<source src='http://dl.dropbox.com/u/51225/forsale.webm' type='video/webm' / >"+
    											"<source src='http://dl.dropbox.com/u/51225/forsale.mp4' type='video/mp4' / >"+			
    											"Your browser does not support the video tag."+
    											"</video>";
    

	public static final String ADMANAGE_TITLE =	"<div class='headerText'>Administrar anuncios</div>";

	public static final String ADMANAGE_DESCRIPTION =	"<div class='normalText'>Utilice esta seccion para crear nuevos anuncios, asi como actualizar o borrar anuncios existentes.</div>";

	public static final String ADWINDOW_TITLE =	"<div class='headerText'><center>Incluir o modificar anuncios</center></div>";

	public static final String ADWINDOW_HELP =	"<div class='normalText'><center>Incluya los datos solicitados y presione siguiente.</center></div>";

	public static final String[] DIVISION1_NAMES = {
		
		"San Jose",
		"Alajuela",
		"Cartago",
		"Heredia",
		"Guanacaste",
		"Puntarenas",
		"Limon"		
	};
	
	
	public static final int FIRST_DIV2 = 101;

	public static final int LAST_DIV2 = 706;
	
	public static final String ADFORM_PHOTO_INSTRUCTION =	"<hr /><b>Fotos</b><br>"+
															"Utilice esta seccion para agregar o actualizar las fotos de su anuncio.";

	public static final String ADFORM_PROPERTY_INSTRUCTION =	"<hr /><b>Propiedad</b><br>"+
			"Informacion sobre el tipo de propiedad a vender o alquilar.";

	public static final String ADFORM_ADDRESS_INSTRUCTION =	"<hr /><b>Ubicacion</b><br>"+
			"Informacion sobre la ubicacion de la propiedad.";

	public static final String ADFORM_DETAILS_INSTRUCTION =	"<hr /><b>Caracteristicas</b><br>"+
			"Caracteristicas de la propiedad.";
	
	public static final String ADFORM_PRICE_INSTRUCTION =	"<hr /><b>Precio</b><br>"+
			"Precio de venta o alquiler.";

	public static final String ADFORM_ADDETAILS_INSTRUCTION =	"<hr /><b>Anuncio</b><br>"+
			"Informacion sobre el anuncio.";

	public static final String ADFORM_PRIVACY_INSTRUCTION =	"<hr /><b>Privacidad</b><br>"+
			"Caracteristicas de privacidad del anuncio.";

	public static final String ADFORM_CONTACT_INSTRUCTION =	"<hr /><b>Contacto</b><br>"+
			"Informacion de contacto. Mas adelante, en la seccion Privacidad, puede indicar si prefiere no mostrar su nombre, telefono o correo.";

	public static final String ADFORM_END_INSTRUCTION =	"<hr /><b>Guardar el anuncio</b><br>"+
			"Por favor revise que la informacion este correcta y presione el boton de guardar.<br>"+
			"Despues le llegara un mensaje su correo electronico para confirmar su anuncio.<br>"+
			"Una vez aprobado su anuncio se publicara en un plazo maximo de 1 dia habil.<br><hr />";

	public static final String ADFORM_VIDEO_INSTRUCTION =	"<hr /><b>Video y Tour Virtual</b><br>"+
			"Incluir videos de youTube y panoramos de PhotoSynth.net";

	public static final String ADFORM_INSTRUCTION =	"<b>Ingresar y actualizar anuncios</b><br>"+
			"Recuerde guardar el anuncio despues de completar el formulario.<br>";

	public static final String ADFORM_IMAGE_INSTRUCTION =	"<b>Fotos</b><br>"+
			"Seleccione la foto que desea incluir en su anuncio. Despues presione el " +
			"el boton 'Subir' para subir la foto.<br>" +
			"Espere unos segundos mientras la foto sube hasta nuestro servidor. " +
			"Una vez subida la foto su imagen aparecera en la tabla de abajo. <br>" +
			"Puede repetir el proceso y subir las fotos que desee." +
			"Tambien puede agregar un titulo a la foto o borrar las fotos subidas.<br>";

	public static final String YOUTUBE_ID_REGEX =	 ".*([a-zA-Z0-9\\-\\_]{11}).*";
	public static final String PHOTOSYNTH_ID_REGEX =	 "([a-zA-Z0-9\\-]+)";

	// 
 
}
