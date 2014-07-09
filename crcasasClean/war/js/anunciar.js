if (CR === undefined)
	var CR = {};

CR.FBisLoaded = false;
CR.moveToCreate = false;

function onConnected() {
	if (CR.moveToCreate ) {
		$('.createAdBtn').html('<i class="icon-refresh icon-spin"></i> Creando anuncio...');
		
	     FB.api('/me', function(response) {				
  	    	 
				$.getJSON("/restlet/json/hasads/" + response.id, function(
						data) {

				       if (data) window.open("/misanuncios.html", "_self");
				       else window.open("/publicar.html?adId=0", "_self");

				});				       
	     });
		
	}
	

};


function onNonAuthorized() {
};

function onNotConnected() {
};

// Facebook login callback 
function loginCallback() {
	
    FB.getLoginStatus(function(response) {
    	CR.moveToCreate = true;
    	getFBLoginStatus(response);
    	
       });
	
};

$(document).ready(function() {
	
	  // Inicia el SDK de Facebook y obtiene el status
	  // 
	  $.getScript('//connect.facebook.net/en_UK/all.js', function(){
		    FB.init({
		      appId: '174633095925807',
		    });  
		    
		    CR.FBisLoaded = true;
		    
			FB.getLoginStatus(function(response) {
				getFBLoginStatus(response);
				}, true);
			
			FB.Event.subscribe('auth.statusChange', function(response) {
				
				onFBLoginStatusChange(response);
				});	
			
			FB.XFBML.parse();
			
		    onFBInit();
		    
		  });
	
	$('.carousel').carousel({
		  interval: 2000
		});
	
	if (isiOS() && isChrome()) $(".chromeMessage").show();
	
	$(".createAd").click(function() {

		loginCallback();
	       

	});
	


	

});
