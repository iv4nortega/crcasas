var CR = {};

function payServletResponse(responseText, statusText, xhr, $form) {
	

	var response = parseInt(responseText);

	
	if (response == 1) $('.thanks').show();	
	else {
		alert('Uhm.. Su pago fue recibido pero por algun problema tecnico no fue posible asociarlo a un anuncio. Por favor pongase en contacto con admin@crcasas.com para ayudarlo a resolver la situacion.');
		
	}
};

function setPayServletValues(adId, fbId, type) {
	$('#adIdPay').val(adId);
	$('#fbIdPay').val(fbId);
	$('#typePay').val(type);
	return true;
};

function savePay(adId,  fbId, type) {
	setPayServletValues(adId, fbId, type);
	
	//var queryString = $('#payServlet').formSerialize();
	//$.post('http://www.crcasas.com/forms/pagar', queryString);	
	
	$('#payServletSubmit').trigger('click');
};

function setPayServlet() {
	
	$('#payServlet').ajaxForm({
		success : payServletResponse
	});
	
};

$(document).ready(function() {
	var QueryString = getQueryString();
	//alert(QueryString.cm);
	$('#header').load('/w/tabs/headermini.html', function() {
		initHeaderForPublish();		
	});
	
	$('#footer').load('/w/tabs/footermini.html');
	
	$('#main').load('/w/tabs/sucess.html', function(){
		
		
		$('.thanks').hide();
		
		if ( (QueryString.cm !== undefined) 
				&& ($.cookie("status") !== undefined) 
				&& ($.cookie("uid") !== undefined)
				&& (QueryString.item_number !== undefined)				
				&& ($.cookie("status") == 'login')) {
			
			$("#sucessShow").attr("href", "/w/home.html?anuncio="+QueryString.cm);
			setPayServlet();
			
			// P P2 E E2
			
			if (QueryString.item_number == 'ADPR01') savePay(QueryString.cm, $.cookie("uid"), 'P');
			if (QueryString.item_number == 'ADPR02') savePay(QueryString.cm, $.cookie("uid"), 'P2');
			if (QueryString.item_number == 'ADEX01') savePay(QueryString.cm, $.cookie("uid"), 'E');
			if (QueryString.item_number == 'ADEX02') savePay(QueryString.cm, $.cookie("uid"), 'E2');
			
		} else {
			alert('No se realizo ningun pago por Paypal, pero su anuncio fue publicado con la opcion basica gratuita. Puede intentar de nuevo ascender su anuncio a premium o exclusivo editando su anuncio');
			$("#sucessShow").hide();
		}
		
				
	});	
  	
    // http://www.crcasas.com/w/sucess.html?
	// tx=6DN58832WS3857831&st=Completed&amt=7%2e00&cc=USD&cm=hola&item_number=ADPR01
	
	
});
