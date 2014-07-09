function initHeader() {
	$('#mainTab a, button').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
		if (this.id == CR.pagesId.SEARCH)
			runMasonry();
		if (this.id == 'anuncio-btn') {
			if (!$("#anuncio-id").val()) {
				alert('No existe un anuncio con ese numero');
			} else {
				if ($("#anuncio-id").val() != CR.current_ad) {
					get_ad($("#anuncio-id").val());
					CR.current_ad = $("#anuncio-id").val();
					window.scrollTo(0, 0);
				}
			}
		}

	});

	$("[rel='tooltip']").tooltip();

	$('#login-btn').click(function() {
		fbLogin()

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


