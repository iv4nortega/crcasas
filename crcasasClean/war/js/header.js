function initHeader() {
	
	
	// $('a[data-toggle="tab"], button[data-toggle="tab"]').on('shown.bs.tab', function (event) {
    	  
        // DELETE.
    	//alert('hola!!');
    	
	//});
    
	window.onpopstate = function(event) {
		  console.log("location: " + document.location + ", state: " + JSON.stringify(event.state));
		};

    $('.dropdown-toggle').dropdown();
    
     	
	$(".hashpage").click(function(e) {
		
		console.log(".haspage clicked =" + this.id);

		switch (this.id) {
	        case "homeLink":	    
	        	document.title = 'Casas a la venta, lotes y alquiler en Costa Rica';
	        	History.pushState({ page: "home" }, document.title, '#home');	       
	        	
	            break;
	        case "advertise-id":
	        	
	        	// location.hash = '!anunciar';
	        	document.title = 'Publica tu anuncio gratis';
	        	History.pushState({ page: "anunciar" }, document.title, '#anunciar');	        	
	        	
	            break;
	        case "search-id":
	        	// location.hash = '!buscar';
	        	document.title = 'Buscar propiedades a la venta y alquiler en Costa Rica';
	        	History.pushState({ page: "buscar" }, document.title, '#buscar');	        	

	            break;	            
	        case "about-id":
	        	//location.hash = '!acerca';
	        	document.title = 'Acerca de crCasas';
	        	
	        	History.pushState({ page: "acerca" }, document.title, '#acerca');	        	

	            break;
	        case "wall-id":
	        	//location.hash = '!muro';
	       
	        	document.title = 'Escribe en nuestro muro';
	        	History.pushState({ page: "muro" }, document.title, '#muro');	        	

	            break;	            
        }
		

        
	});
		
	//$('#mainTab a, button').click(function(e) {
	//	console.log("maintab a or button clicked " + this.id);
	//});

	// $("[rel='tooltip']").tooltip();

	$('#anuncio-btn').click(function() {
		if (!$("#anuncio-id").val()) {
			alert('No existe un anuncio con ese numero');
		} else {
			if ($("#anuncio-id").val() != CR.current_ad) {
				get_ad($("#anuncio-id").val());
				CR.current_ad = $("#anuncio-id").val();		
			}
			$('#mainTab a[href="#anuncio"]').tab('show');
			window.scrollTo(0, 0);
	        	
 

		}

	});	
	
	$('#mainTab a[href="#anuncio"]').on('shown.bs.tab', function (e) {


		location.hash = "!anuncio="+CR.current_ad;
		
		});
	

	$('#login-btn').click(function() {
		fbLogin()

	});
	
	$('#homeLink').click(function() {
		
		$("li.active").removeClass('active');

	});

	$('#logout-clk').click(function(e) {
		FB.logout(function(response) {

		});

	});

};

function initHeaderForPublish() {

	$('#login-btn').click(function() {
		fbLogin()

	});

	$('#logout-clk').click(function(e) {
		FB.logout(function(response) {

		});

	});

};

function setUserName(userName) {
	$("#user-name").empty().append(userName + "&nbsp;<b class='caret'></b>");
	
}
function showConnectedUser(){
	$("#login-btn").hide();
	$("#logout-btn").show();
	$("#user-picture-canvas").show();
	$("#user-picture").attr("src", CR.userPic);
	setUserName(CR.userName);

}

function clearConnectedUser(){
	$("#login-btn").show();
	$("#logout-btn").hide();
	$("#user-picture-canvas").hide();
}




