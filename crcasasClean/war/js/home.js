/**
 * Global var declarations
 */

if (CR === undefined)
	var CR = {};

CR.moveToCreate = false;

CR.searchMasonryReady = false;
CR.frontMasonryReady = false;


CR.FBisLoaded = false;

CR.nextAction = 'none';
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

function showMessForVisitor() {
	$('#btnLandingAdvertise').show();
	$('#btnLandingNew').hide();
	$('#btnLandingAds').hide();	
	
	$('#advertise-id').show();	
	$('#advertise-id-user').hide();	
	$('#advertise-id-agent').hide();	
	
};

function showMessForUser() {
	$('#btnLandingAdvertise').hide();
	$('#btnLandingNew').show();
	$('#btnLandingAds').hide();	
	
	$('#advertise-id').hide();	
	$('#advertise-id-user').show();	
	$('#advertise-id-agent').hide();
};

function showMessForAgent() {
	$('#btnLandingAdvertise').hide();
	$('#btnLandingNew').hide();
	$('#btnLandingAds').show();	
	
	$('#advertise-id').hide();	
	$('#advertise-id-user').hide();	
	$('#advertise-id-agent').show();
};

function onConnected() {
	
	showConnectedUser();	
	
	if (CR.moveToCreate ) {
		$('#btn-newad, #btn-newad2').html('<i class="icon-refresh icon-spin"></i> Creando anuncio...');
		
	     FB.api('/me', function(response) {				
  	    	 
				$.getJSON("/restlet/json/hasads/" + response.id, function(
						data) {

				       if (data) window.open("/misanuncios.html", "_self");
				       else window.open("/publicar.html?adId=0", "_self");

				});				       
	     });
		
	} else {

		$.getJSON("/restlet/json/hasads/" + CR.userId,
				function(data) {
				    if (!data) initPageForUser();			    	
				    else initPageForAgent();			    
				});			
		
	}
	
	

};


function onNonAuthorized() {
	clearConnectedUser();
	initPageForVisitor();
};

function onNotConnected() {
	clearConnectedUser();
	initPageForVisitor();
	// necesito desplegar un mensaje de por que hay que autorizarse
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

	$.getJSON("/restlet/json/imagen/" + adNumber,
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

	$.getJSON("/restlet/json/anuncio/" + adNumber,
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
				
				$('#post-canvas').html(getPostCode(838974916132481));
				
				$('#showPost').show();
				
				if (CR.FBisLoaded) {
					FB.XFBML.parse(document.getElementById("fb-post"));
					FB.XFBML.parse(document.getElementById("adComments"));
					FB.XFBML.parse(document.getElementById("facebookLike"));					
				}

				googleInitialize(data.lat, -1*Math.abs(data.lng));

			});
};

function getFrontAds(adId, selector) {
	$.getJSON("/restlet/json/anuncio/" + adId, function(
			data) {
		
		if (data.length) {
			$(selector).html($("#frontAdsTemplate").render(data));
		}
		else {
		    //alert("no front ads data");
		}
		

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
	
	History.pushState({ page: "anuncio" }, document.title, location.hash);	        	

	
}

function get_premiumAds() {

	$
			.getJSON(
					"/restlet/json/anuncios"
							+ path_to_data,
					function(data) {

						$("#adList").html($("#frontTemplate").render(data));

						//if (data.length === 0)
							//alert('No se encontraron propiedades. Por favor intente de nuevo variando los criterios de busqueda.');

						runSearchMasonry();

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
					"/restlet/json/premium",
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



function getThreeAds() {

	$
			.getJSON(
					"/restlet/json/three",
					function(data) {

						$("#threeAds").html($("#threeAdsTemplate").render(data));


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
					"/restlet/json/anuncios",
					function(data) {

						$("#frontAdList").html($("#frontTemplate").render(data));

						//if (data.length === 0)
							//alert('No se encontraron propiedades. Por favor intente de nuevo variando los criterios de busqueda.');

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
					"/restlet/json/anuncios"
							+ path_to_data,
					function(data) {
								

						$('#search-btn').button('reset');

						numRecs = data.length;
						
						$("#adList").html($(template).render(data));
						
						if (template=='#listTemplate') {
							
							
							
							if ((numRecs === undefined) || (numRecs == 0) ) resultmessage = 'No se encontro ninguna propiedad';
							else  resultmessage = "<p>Se encontraron <em>"+numRecs+"</em> propiedades.</p>";
							$( "#search-result" ).html( resultmessage );
							$("#search-result").show();
						} 

						
						//if (data.length === 0)
							//alert('No se encontraron propiedades. Por favor intente de nuevo variando los criterios de busqueda.');

						runSearchMasonry();

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

function runSearchMasonry() {
	var $container = $('#adList');
	// initialize Masonry after all images have loaded  

	if (CR.searchMasonryReady)  $container.masonry('destroy');
	
	$container.imagesLoaded( function() {
	  $container.masonry();
	  CR.searchMasonryReady = true;
	});
	

}

function initSearchMasonry() {
	
	var $container = $('#adList');
	$container.masonry();
	CR.searchtMasonryReady = true;

};

function runFrontMasonry() {
	
	var $container = $('#frontAdList');
	
	if (CR.frontMasonryReady)  $container.masonry('destroy');

	// initialize Masonry after all images have loaded  
	$container.imagesLoaded( function() {
	  $container.masonry();
	  CR.frontMasonryReady = true;

	});

}

function initFrontMasonry() {

	var $container = $('#frontAdList');
	$container.masonry();
	CR.frontMasonryReady = true;

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
				
			    var $btn = $(this);
			    $btn.button('loading');

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


	    	$('#mainTab a[href="#home"]').on('shown.bs.tab', function (e) {
		    	  runFrontMasonry();
		    });
		    
		    $('#mainTab a[href="#buscar"]').on('shown.bs.tab', function (e) {
		    	runSearchMasonry();
		    })
	    	
	});
	
	$('#footer').load('tabs/footer.html');	
	$('#anuncio').load('tabs/anuncio.html', function() {
		initGalleria();
	});

	$('#buscar').load('tabs/search.html', function() {
		initSearchMasonry();
		get_ads('', "#frontTemplate");
		initSearchForm();
		$('#search-result').hide();
		
	});
	$('#acerca').load('tabs/aboutus.html', function() {
		initContactForm();
	});
	
	$('#muro').load('tabs/wall.html');

	$('#anunciar').load('tabs/new.html', function() {
		$('#btn-newad, #btn-newad2').click(function(e) {

			CR.moveToCreate = true;
			$('#login-btn').trigger('click');
		});
	});

	$('#home').load('tabs/landing.html', function() {
		$('#btnLandingSearch').click(function(e) {
			$('#search-id').trigger('click');
		});
		
		
		if (CR.FBisLoaded) {
			FB.XFBML.parse();			
		}

		initFrontMasonry();
		get_frontPremiumAds();

		initSellerForm();
		
	});



};

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
	
			if (hash) {
				
				if (hash!='#home') {
					$('#mainTab a[href="' + hash + '"]').tab('show'); 
					$('#mainTab button[href="' + hash + '"]').tab('show');				
				}
				
			} else {
				// $('#mainTab a[href="#home"]').tab('show'); 
				


				
			}
			
		}

	};
	

};

function initSellerForm() {
	
	
    $('#sellerForm').ajaxForm(function() { 
        alert("Gracias por su consulta!"); 
    });
	
	
	
};

function initPageForVisitor() {
	CR.moveToCreate = false;
	showMessForVisitor();
}

function initPageForUser() {
	showMessForUser();
	
}

function initPageForAgent() {
	showMessForAgent();
}

function initPage() {
	// Prepara la página para ser mostrada a cualquier usuario
	

	getTabs();


	// $('body').tooltip({
	// 	selector : '[rel=tooltip]'
	// });	
	

	
    $(window).bind('hashchange', function (event) {
    	readHash();

     });
    


}

$(document).ready(
		function() {
			
			// 104922512993964  crcasastest1
			// 399492506851789 localhost
			// 174633095925807 crcasas
			

			 //$.ajaxSetup({ cache: true });
			
			//$.ajaxSetup({
			//    'beforeSend' : function(xhr) {
			//        xhr.overrideMimeType('text/html; charset=ISO-8859-1');
			//    },
			//});
			
			//$.ajaxSetup({ scriptCharset: "utf-8" , contentType: "application/json; charset=utf-8"});
			
			// 263825913721404  facebook id for test.crcasas1.appspot.com 
			// 174633095925807 facebook id for crcasas.com
			//  399492506851789 facebook id for localhost
			
			
			window.onerror = function(message, file, line) {
				   _gaq.push(['_trackEvent', 'JS Error', file + ':' + line + '\n\n' + message]);
				};
				
			
			$.ajax({ 
				  url: '//connect.facebook.net/en_UK/all.js',
				  timeout: 1000,
				  dataType: 'script',
				  success: function(data) {
					  
					    FB.init({
					      appId: '174633095925807',
					    });  
					  
						FB.getLoginStatus(function(response) {
							CR.FBisLoaded = true;
							FB.XFBML.parse();
						    onFBInit();
							getFBLoginStatus(response);
							}, true);
						
						FB.Event.subscribe('auth.statusChange', function(response) {
							
							onFBLoginStatusChange(response);
							});	

				  },
				    error: function(x, t, m) {
				        // Site is blocked probably
				        if(t==="timeout") {
				            $('#fbunabletoconn').removeClass('hidden');
				        } else {
				        	$('#fbunabletoconn').removeClass('hidden');
				        }
				    }
				});
			
			
			  //$.getScript('//connect.facebook.net/en_UK/all.js', function(){
			  //  FB.init({
			  //    appId: '174633095925807',
			  //  });     
			    
			    
			    
			    
			//	FB.getLoginStatus(function(response) {
			//		CR.FBisLoaded = true;
			//		FB.XFBML.parse();
			//	    onFBInit();
			//		getFBLoginStatus(response);
			//		}, true);
				
			//	FB.Event.subscribe('auth.statusChange', function(response) {
					
			//		onFBLoginStatusChange(response);
		//			});	

			    
			//  });
			  
			$.templates("basicTemplate", "#basicTemplate");
			$.templates("exclusiveTemplate", "#exclusiveTemplate");
			$.templates("premiumTemplate", "#premiumTemplate");
			$.templates("descriptionTemplate", "#descriptionTemplate");
			$.templates("addressTemplate", "#addressTemplate");
			$.templates("contactMeTemplate", "#contactMeTemplate");
			$.templates("contactTemplate", "#contactTemplate");
			$.templates("adLeft", "#adLeft");
			$.templates("adRight", "#adRight");
			
			initPage();

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
