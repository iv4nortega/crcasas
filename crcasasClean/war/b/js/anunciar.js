if (CR === undefined)
	var CR = {};


function onConnected() {
	$('.createAd').show();
	$('.fbLogin').hide();
	$('.fbLogout').show();
	
	
};


function onNonAuthorized() {
	$('.createAd').hide();
	$('.fbLogin').show();
	$('.fbLogout').hide();

};

function onNotConnected() {
	$('.createAd').hide();
	$('.fbLogin').show();
	$('.fbLogout').hide();

};

// Facebook login callback 
function loginCallback() {
	
    FB.getLoginStatus(function(response) {
        if (response.status === 'connected') {
        	
        	$('.createAdBtn').html('<i class="icon-refresh icon-spin"></i> Creando anuncio...');
        	
	   	     FB.api('/me', function(response) {				
	   	    	 
					$.getJSON("http://www.crcasas.com/restlet/json/hasads/" + response.id, function(
							data) {

					       if (data) window.open("http://www.crcasas.com/misanuncios.html", "_self");
					       else window.open("http://www.crcasas.com/publicar.html?adId=0", "_self");

					});				       
		     });
        } 
        else  alert('Para ingresar anuncios primero debe ingresar con su Login de Facebook');

       });
	
};

$(document).ready(function() {
	$('.carousel').carousel({
		  interval: 2000
		});
	
	if (isiOS() && isChrome()) $(".chromeMessage").show();
	
	$(".createAd").click(function() {

		loginCallback();
	       

	});
	


	

});
