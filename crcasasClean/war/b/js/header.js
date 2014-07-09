	(function() {
		var po = document.createElement('script');
		po.type = 'text/javascript';
		po.async = true;
		po.src = 'https://apis.google.com/js/plusone.js';
		var s = document.getElementsByTagName('script')[0];
		s.parentNode.insertBefore(po, s);
	})();

function initHeader() {

	
	$('#mainTab a, button').click(function(e) {
		e.preventDefault();
		if (this.id != 'collapse-btn') $(this).tab('show');
		
		//if (this.id == CR.pagesId.SEARCH) location.hash = CR.pagesTag.SEARCH;
		//if (this.id == CR.pagesId.ADVERTISE) location.hash = CR.pagesTag.ADVERTISE;
		//if (this.id == CR.pagesId.HOME) location.hash = CR.pagesTag.HOME;
		//if (this.id == CR.pagesId.WALL) location.hash = CR.pagesTag.WALL;
		//if (this.id == CR.pagesId.ABOUT) location.hash = CR.pagesTag.ABOUT;
		
		if (this.id == CR.pagesId.SEARCH)
			runMasonry();

		if (this.id == CR.pagesId.HOME)
			runMasonry();		

		if (this.id == CR.pagesId.ADVERTISE) 
			window.open($("#advertise-id").attr("data-href"), "_self");

		
		if (this.id == 'anuncio-btn') {
			if (!$("#anuncio-id").val()) {
				alert('No existe un anuncio con ese numero');
			} else {
				if ($("#anuncio-id").val() != CR.current_ad) {
					get_ad($("#anuncio-id").val());
					CR.current_ad = $("#anuncio-id").val();										
				}
				
				location.hash = "!anuncio="+CR.current_ad;
				window.scrollTo(0, 0);
			}
		}

	});

	$("[rel='tooltip']").tooltip();


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
