/**
 * Global var declarations
 */

if (CR === undefined)
	var CR = {};

CR.debugMode = true;

CR.userFirstName = '';

CR.userLastName = '';

CR.userEmail = '';

CR.editAd = '0';

CR.userId = '';

CR.showAd = false;

CR.photosRow = 1;
CR.photosCounter = 0;

CR.pagesId = {
	SEARCH : 'search-id',
	AD : 'anuncio-btn',
	ADVERTISE : 'advertise-id',
	HOME : 'crcasas-logo',
	WALL : 'wall-id',
	ABOUT : 'about-id',
	LANDING : 'crcasas-logo'
};

CR.pagesTag = {
	SEARCH : "#buscar",
	AD : "#anuncio",
	ADVERTISE : "#anunciar",
	HOME : "#home",
	WALL : '#muro',
	ABOUT : '#acerca',
	LANDING : '#landing'

};

CR.current_ad = 0;

// Google analytics code
var _gaq = _gaq || [];
_gaq.push([ '_setAccount', 'UA-23115758-1' ]);
_gaq.push([ '_trackPageview' ]);

(function() {
	var ga = document.createElement('script');
	ga.type = 'text/javascript';
	ga.async = true;
	ga.src = ('https:' == document.location.protocol ? 'https://ssl'
			: 'http://www')
			+ '.google-analytics.com/ga.js';
	var s = document.getElementsByTagName('script')[0];
	s.parentNode.insertBefore(ga, s);
})();

function findAdTag(hashString) {

	var patt1 = /(#anuncio)=\d+/i;

	var match = hashString.match(patt1);

	if (match != null) {
		var result = match[1];
		if (result)
			return true;
	}
	;

	return false;
};

function findAdId(hashString) {

	var patt1 = /#anuncio=(\d+)/i;

	var match = hashString.match(patt1);

	if (match != null) {
		var result = match[1];
		if (result)
			return parseInt(result);
	}
	;

	return 0;
};

function cleanHash(hashString) {

	var result = hashString;
	var patt1 = /#!/i;
	var match = hashString.match(patt1);
	if (match != null) {
		var result = hashString.replace('#!', '#');
	}
	;
	return result;
};

function get_ad(adNumber) {

	$('#galleria').show();

	$.getJSON("http://www.crcasas.com/restlet/json/imagen/" + adNumber,
			function(data) {

				Galleria.run('#galleria', {
					dataSource : data
				});

				// if ($('#galleria').data('galleria'))
				// $('#galleria').data('galleria').load(data)
				// else $('#galleria').galleria( { dataSource: data });

				if (data[0].image == 'images/crcasas.png')
					$('#galleria').hide();

			}).success(function() {
	}).error(function(jqXHR, textStatus, errorThrown) {
		console.log("error " + textStatus);
		console.log("incoming Text " + jqXHR.responseText);
		if (CR.debugMode)
			alert('Error loading galleria json: check console log')
	}).complete(function() {
	});

	$.getJSON("http://www.crcasas.com/restlet/json/anuncio/" + adNumber,
			function(data) {

				$("#adTitleShow").html($("#adTitleTemplate").render(data)

				);

				$("#adShow").html($("#adTemplate").render(data)

				);

				FB.XFBML.parse(document.getElementById("adComments"));

				FB.XFBML.parse(document.getElementById("facebookLike"));

			});
};

function getFrontAds(adId, selector) {
	$.getJSON("http://www.crcasas.com/restlet/json/anuncio/" + adId, function(
			data) {
		$(selector).html($("#frontAdsTemplate").render(data)

		);

	});

};

function init_ad_click(item) {

	var ad = $(item).attr('id');
	
	gotoAd(ad);

	//$("#anuncio-id").val(ad);

	//get_ad($("#anuncio-id").val());
	//CR.current_ad = $("#anuncio-id").val();
	
	//$('#anuncio-btn').trigger('click');


}

function gotoAd(ad) {

	$("#anuncio-id").val(ad);

	get_ad($("#anuncio-id").val());
	CR.current_ad = $("#anuncio-id").val();
	
	$('#anuncio-btn').trigger('click');


}

function get_premiumAds() {

	$
			.getJSON(
					"http://www.crcasas.com/restlet/json/anuncios"
							+ path_to_data,
					function(data) {

						$("#adList").html($("#listTemplate").render(data));

						if (data.length === 0)
							alert('No se encontraron propiedades. Por favor intente de nuevo variando los criterios de busqueda.');

						runMasonry();

						$(".item").click(function() {

							init_ad_click(this);

						});

					}).success(function() {
			}).error(
					function(jqXHR, textStatus, errorThrown) {
						console.log("error " + textStatus);
						console.log("incoming Text " + jqXHR.responseText);
						if (CR.debugMode)
							alert('Error: check console log ' + textStatus
									+ " " + jqXHR.responseText)
					}).complete(function() {
			});

}

function get_frontPremiumAds() {

	$
			.getJSON(
					"http://www.crcasas.com/restlet/json/anuncios",
					function(data) {

						$("#frontAdList").html($("#listTemplate").render(data));

						if (data.length === 0)
							alert('No se encontraron propiedades. Por favor intente de nuevo variando los criterios de busqueda.');

						runFrontMasonry();

						$(".item").click(function() {

							init_ad_click(this);

						});

					}).success(function() {
			}).error(
					function(jqXHR, textStatus, errorThrown) {
						console.log("error " + textStatus);
						console.log("incoming Text " + jqXHR.responseText);
						if (CR.debugMode)
							alert('Error: check console log ' + textStatus
									+ " " + jqXHR.responseText)
					}).complete(function() {
			});

}



function get_ads(path_to_data) {

	$
			.getJSON(
					"http://www.crcasas.com/restlet/json/anuncios"
							+ path_to_data,
					function(data) {

						$("#adList").html($("#listTemplate").render(data));

						if (data.length === 0)
							alert('No se encontraron propiedades. Por favor intente de nuevo variando los criterios de busqueda.');

						runMasonry();

						$(".item").click(function() {

							init_ad_click(this);

						});

					}).success(function() {
			}).error(
					function(jqXHR, textStatus, errorThrown) {
						console.log("error " + textStatus);
						console.log("incoming Text " + jqXHR.responseText);
						if (CR.debugMode)
							alert('Error: check console log ' + textStatus
									+ " " + jqXHR.responseText)
					}).complete(function() {
			});

}

function runMasonry() {
	var $container = $('#masonry');

	$container.imagesLoaded(function() {
		$container.masonry('reload');
	});

}

function initMasonry() {

	var $container = $('#masonry');
	var gutter = 8;

	$container.masonry({
		itemSelector : '.item',
		gutterWidth : gutter,
		isFitWidth : true
	});

};

function runFrontMasonry() {
	var $container = $('#frontMasonry');

	$container.imagesLoaded(function() {
		$container.masonry('reload');
	});

}

function initFrontMasonry() {

	var $container = $('#frontMasonry');
	var gutter = 8;

	$container.masonry({
		itemSelector : '.item',
		gutterWidth : gutter,
		isFitWidth : true
	});

};


function initGalleria() {
	Galleria.loadTheme('/w/galleria/themes/classic/galleria.classic.min.js');
	$('#anuncio').on('shown', function(e) {
		Galleria.run('#galleria');
		
	});

};

function contactFormResponse(responseText, statusText, xhr, $form) {
	$('#contactForm').clearForm();
	alert('Su mensaje ha sido enviado.');
};

function contactFormBefore(formData, jqForm, options) {
	return $('#contactForm').valid();
};

function initContactForm() {
	
	$('#contactForm').ajaxForm({
		beforeSubmit:  contactFormBefore,
		success : contactFormResponse
	});
	
	
	$('#contactForm').validate(
			{
				rules : {
					name : {
						required : true
					},
					message : {
						required : true,
						minlength: 10
					},
					phone : {
						digits : true
					},
					email : {
						required : true,
						email : true
					}

				},
				messages : {
					name : "Por favor ingrese su nombre",
					message : "Por favor ingrese un mensaje de al menos 10 caracteres",
					phone : "Por favor ingrese su telefono",
					email : "Por favor ingrese su correo electronico"
				},
				highlight : function(label) {
					$(label).closest('.control-group').removeClass('success')
							.addClass('error');
				},
				success : function(label) {
					label.text('OK!').addClass('valid').closest(
							'.control-group').removeClass('error').addClass(
							'success');
				}
			});

	
};

function initSearchForm() {
	getDiv2AsOptions(1, 'select#division2', false);

	$("select#division1").change(function() {
		getDiv2AsOptions($(this).val(), "select#division2", false);
	});

	$('#search-btn').click(
			function(e) {

				if ($("#division2 option:selected").val() == '000') {
					get_ads('/' + $("#type option:selected").val() + '/'
							+ $("#subType option:selected").val() + '/'
							+ $("#division1 option:selected").val());

				} else {
					get_ads('/' + $("#type option:selected").val() + '/'
							+ $("#subType option:selected").val() + '/'
							+ $("#division1 option:selected").val() + '/'
							+ $("#division2 option:selected").val());

				}

			});

};

function getTabs() {

	$('#header').load('/w/tabs/header.html', function() {
		initHeader();
		readHash();
	});
	
	$('#footer').load('/w/tabs/footer.html');

	$('#buscar').load('/w/tabs/search.html', function() {

		initMasonry();
		initSearchForm();
				

		
	});
	$('#acerca').load('/w/tabs/aboutus.html', function() {
		initContactForm();
	});
	
	$('#muro').load('/w/tabs/wall.html');

	$('#anunciar').load('/w/tabs/anunciar.html', function() {
		$('#btnAdvertiseLogin').click(function(e) {
			$('#login-btn').trigger('click');
		});
	});

	$('#home').load('/w/tabs/landing.html', function() {
		$('#btnLandingSearch').click(function(e) {
			$('#search-id').trigger('click');
		});

		$('#btnLandingAdvertise').click(function(e) {
			$('#advertise-id').trigger('click');
		});
		
		initFrontMasonry();
		get_frontPremiumAds();
		
	});

	//getFrontAds(1803063, '#landingLeft');
	//getFrontAds(2045068, '#landingCenter');
	//getFrontAds(2346069, '#landingRight');
	
	

};

/**
 * Page set up
 */

function readHash() {
	var hash = window.location.hash;

	hash = cleanHash(hash);

	if (findAdTag(hash)) {
		var hashId = findAdId(hash);

		if (hashId > 0) {
			
			gotoAd(hashId);
		}
		;

	} else {
		var adIdParam = getURLParameter('anuncio');
		if (adIdParam != 'null' && $.isNumeric(adIdParam)) {
			gotoAd(adIdParam);



		} else {
			// $('#mainTab a[href="' + hash + '"]')('show');

		}

	};
};

$(document).ready(
		function() {
			
			$.templates("basicTemplate", "#basicTemplate");
			$.templates("exclusiveTemplate", "#exclusiveTemplate");
			$.templates("premiumTemplate", "#premiumTemplate");

			getTabs();

			get_ads('');
			
			initGalleria();

			$("#login-btn").show();
			$("#logout-btn").hide();
			$("#user-picture").hide();

			$("#advertise-id").show();

			$('body').tooltip({
				selector : '[rel=tooltip]'
			});

			


			try {

				window.addEventListener("popstate", function(e) {
					var hash = window.location.hash;
					// $('#mainTab a[href="' + hash + '"]')('show');
				});

			} catch (ex) {

				console.log('window.addEventListener popstate not supported '
						+ ex.message);
			}
			;

		});
