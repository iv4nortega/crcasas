/**
 * Global var declarations
 */

if (CR === undefined)
	var CR = {};

CR.debugMode = false;

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
	SEARCH : "buscar",
	AD : "anuncio",
	ADVERTISE : "anunciar",
	HOME : "home",
	WALL : 'muro',
	ABOUT : 'acerca',
	LANDING : 'landing'

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




function advertiseClick(url) {
	window.open(url, "_self");
};

function onConnected() {
	
	showConnectedUser();
	
	$("#btnAdvertiseLogin").hide();

	$("#btnGoToMyAds").show();
	$("#txtPublishGuide").hide();
	
	$("#advertise-id").attr("href", "#anunciar");
	
	$.getJSON("http://www.crcasas.com/restlet/json/hasads/" + CR.userId,
			function(data) {
			    if (!data) { 
			    	
			    	$("#btnLandingAdvertise").attr("href", "publicar.html?adId=0")
			    	$("#advertise-id").html("Crear anuncio");
			    	$("#advertise-id").attr("data-href", "publicar.html?adId=0");
			    }
			    else {
			    	$("#btnLandingAdvertise").attr("href", "misanuncios.html");
			    	$("#advertise-id").html("Mis anuncios");			    	
			    	//$("#advertise-id").click(advertiseClick("\b\misanuncios.html"));
			    	$("#advertise-id").attr("data-href", "misanuncios.html");
			    	
			    }
			});	
};


function onNonAuthorized() {
	clearConnectedUser();
	$("#btnAdvertiseLogin").show();

	$("#btnGoToMyAds").hide();
	
	$("txtPublishGuide").show();
	
	$("#btnLandingAdvertise").attr("href", "\anunciar")
	//$("#advertise-id").unbind(advertiseClick);
	$("#advertise-id").html("Anunciar &raquo;");	
	$("#advertise-id").attr("data-href", "\anunciar");
		
};

function onNotConnected() {
	clearConnectedUser();
	
	$("#btnAdvertiseLogin").show();

	$("#btnGoToMyAds").hide();
	
	$("txtPublishGuide").show();
	
	$("#btnLandingAdvertise").attr("href", "\anunciar")
	$("#advertise-id").html("Anunciar &raquo;");			    	
	$("#advertise-id").attr("data-href", "\anunciar");
	
};

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
					dataSource : data,
					responsive: true,
					wait: true
				});


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
				
				$("#adShowTopRight").html($("#contactTemplate").render(data)

				);
				
				var template = $.templates("#adLeft");
				
				var adLeft = template.render(data);

				$("#adShowLeft").html(adLeft);

				$("#adShowRight").html($("#adRight").render(data)

				);
				
				if (data.showVideo) {
					
					$('#showVideo').show();
					
					var URL = data.video01;

					var VIDEO_ID = youtube_parser(URL);

					var htm = '<iframe src= "http://www.youtube.com/embed/'
							+ VIDEO_ID + '" frameborder="0" ></iframe>';

					$('#video-canvas').html(htm);
					
				} else {
					$('#showVideo').hide();
					$('#video-canvas').empty();
				}
				
				likeThis('.likeThis');

				FB.XFBML.parse(document.getElementById("adComments"));

				FB.XFBML.parse(document.getElementById("facebookLike"));
				
				googleInitialize(data.lat, -1*Math.abs(data.lng));


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




}

function gotoAd(ad) {

	$("#anuncio-id").val(ad);

	get_ad($("#anuncio-id").val());
	CR.current_ad = $("#anuncio-id").val();
	
	$('#anuncio-btn').trigger('click');

	location.hash = "!anuncio="+ad;
	
}

function get_premiumAds() {

	$
			.getJSON(
					"http://www.crcasas.com/restlet/json/anuncios"
							+ path_to_data,
					function(data) {

						$("#adList").html($("#frontTemplate").render(data));

						if (data.length === 0)
							alert('No se encontraron propiedades. Por favor intente de nuevo variando los criterios de busqueda.');

						runMasonry();

						$(".galItem").click(function() {

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


function getMainAds() {

	$
			.getJSON(
					"http://www.crcasas.com/restlet/json/premium",
					function(data) {

						$("#mainAds").html($("#mainAdsTemplate").render(data));

						$('.flexslider').flexslider();

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

						$("#frontAdList").html($("#frontTemplate").render(data));

						if (data.length === 0)
							alert('No se encontraron propiedades. Por favor intente de nuevo variando los criterios de busqueda.');

						runFrontMasonry();

						$(".galItem").click(function() {

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



function get_ads(path_to_data, template) {

	$
			.getJSON(
					"http://www.crcasas.com/restlet/json/anuncios"
							+ path_to_data,
					function(data) {
								
								$('#search-icon').removeClass("icon-refresh");
								$('#search-icon').removeClass("icon-spin");


						$("#adList").html($(template).render(data));

						if (data.length === 0)
							alert('No se encontraron propiedades. Por favor intente de nuevo variando los criterios de busqueda.');

						runMasonry();

						$(".galItem").click(function() {

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
		itemSelector : '.galItem',
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
		itemSelector : '.galItem',
		gutterWidth : gutter,
		isFitWidth : true
	});

};


function initGalleria() {
	Galleria.loadTheme('galleria/themes/classic/galleria.classic.min.js');

	
	$('#anuncio').on('shown', function(e) {
		
		Galleria.run('#galleria', {
			dataSource : data,
			responsive: true,
			wait: true
		});
		
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
				
				$('#search-icon').addClass("icon-refresh");
				$('#search-icon').addClass("icon-spin");
				


				if ($("#division2 option:selected").val() == '000') {
					get_ads('/' + $("#type option:selected").val() + '/'
							+ $("#subType option:selected").val() + '/'
							+ $("#division1 option:selected").val(), "#listTemplate");

				} else {
					get_ads('/' + $("#type option:selected").val() + '/'
							+ $("#subType option:selected").val() + '/'
							+ $("#division1 option:selected").val() + '/'
							+ $("#division2 option:selected").val(), "#listTemplate");

				}

			});

};

function getTabs() {
	
	$('#header').load('tabs/header.html', function() {
		initHeader();
		readHash();
		$('#insert1').show();
	});
	
	$('#footer').load('tabs/footer.html');

	$('#buscar').load('tabs/search.html', function() {

		initMasonry();
		initSearchForm();
				
		$('#insert1').show();

		
	});
	$('#acerca').load('tabs/aboutus.html', function() {
		initContactForm();
	});
	
	$('#muro').load('tabs/wall.html');

	$('#anunciar').load('tabs/anunciar.html', function() {
		$('#btnAdvertiseLogin').click(function(e) {
			$('#login-btn').trigger('click');
		});
	});

	$('#home').load('tabs/landing.html', function() {
		$('#btnLandingSearch').click(function(e) {
			$('#search-id').trigger('click');
		});
		
		if (typeof(FB) != 'undefined'
		     && FB != null ) {
			FB.XFBML.parse(document.getElementById("fb-like-box"));
		} else {
		    // alert the user
		}

		
		initFrontMasonry();
		get_frontPremiumAds();
		getMainAds();
		
	});

	

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
			$.templates("descriptionTemplate", "#descriptionTemplate");
			$.templates("addressTemplate", "#addressTemplate");
			$.templates("contactMeTemplate", "#contactMeTemplate");
			$.templates("contactTemplate", "#contactTemplate");
			$.templates("adLeft", "#adLeft");
			$.templates("adRight", "#adRight");
			$.templates("mainAds", "#mainAdsTemplate");

			getTabs();

			get_ads('', "#frontTemplate");
			
			initGalleria();
			

			$("#login-btn").show();
			$("#logout-btn").hide();
			$("#user-picture-canvas").hide();

			//$("#advertise-id").show();

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
