if (CR === undefined)
	var CR = {};



function onConnected() {

	showConnectedUser();
	
	var QueryString = getQueryString();

	
	if ( (QueryString.cm !== undefined) && (QueryString.item_number !== undefined)) {
		
		$('#main').load('/tabs/sucess.html', function(){
			
			$('.thanks').show();
			
			$("#sucessShow").attr("href", "http://www.crcasas.com#!anuncio="+QueryString.cm);
			setPayServlet();
			
			// P P2 E E2
			
			if (QueryString.item_number == 'ADPR01') savePay(QueryString.cm, CR.userId, 'P');
			if (QueryString.item_number == 'ADPR02') savePay(QueryString.cm, CR.userId, 'P2');
			if (QueryString.item_number == 'ADEX01') savePay(QueryString.cm, CR.userId, 'E');
			if (QueryString.item_number == 'ADEX02') savePay(QueryString.cm, CR.userId, 'E2');
					
		});	
		
	} else {
		
		$('#main').load('/tabs/failure.html');
		

		
	}
	

	
	
};


function onNonAuthorized() {
	$('#main').load('/tabs/notconnect.html');
	clearConnectedUser();

};

function onNotConnected() {
	$('#main').load('/tabs/notconnect.html');
	clearConnectedUser();
};

// Facebook login callback 
function loginCallback() {
	
    FB.getLoginStatus(function(response) {
        if (response.status === 'connected') {
        	

        } 
        else  alert('Para ingresar anuncios primero debe ingresar con su Login de Facebook');

       });
	
}

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
	$('#header').load('/tabs/headermini.html', function() {
		initHeaderForPublish();		
	});
	
	$('#footer').load('/tabs/footermini.html');
	
	
  	
    // http://www.crcasas.com/sucess.html?
	// tx=6DN58832WS3857831&st=Completed&amt=7%2e00&cc=USD&cm=hola&item_number=ADPR01
	
	
});
