if (CR === undefined)
	var CR = {};

CR.photosRow = 1;
CR.photosCounter = 0;

function showHouseLike() {
	$('.landLikeCG').hide();
	$('.commLikeCG').hide();
	$('.houseLikeCG').fadeIn();
};

function showLandLike() {
	$('.commLikeCG').hide();
	$('.houseLikeCG').hide();
	$('.landLikeCG').fadeIn();

};

function showCommLike() {
	$('.landLikeCG').hide();
	$('.houseLikeCG').hide();
	$('.commLikeCG').fadeIn();
};

function checkPropertySubConditions(propertySubTypeAd) {
	var s = "HACBVP";
	if (s.indexOf(propertySubTypeAd) > -1)
		showHouseLike();
	var s = "LFT";
	if (s.indexOf(propertySubTypeAd) > -1)
		showLandLike();
	var s = "MDWEO";
	if (s.indexOf(propertySubTypeAd) > -1)
		showCommLike();
};

function loadAd(adNumber) {

	CR.adId = parseInt(adNumber);
	$('#adIdAd').val(adNumber);
	

	var cookieUid = $.cookie("uid");
	var cookieLogin = ($.cookie("status") == 'login') ? true : false;

	if (cookieLogin) {

		$.getJSON('http://www.crcasas.com/restlet/json/anuncio/' + adNumber
				+ '/' + cookieUid + '/form', function(data) {

			$('#loadingWrapper').remove();
			$("#main").show();
			
			setFormTitle('Editando anuncio No.'+adNumber,data.titleAd);
			
			CR.adType = data.adTypeAd;
			
			$.each(data, function(key, value) {

				var type = $(':input[name="' + key + '"]').attr('type');

				if (type == 'checkbox') {
					if (value.toUpperCase() == 'TRUE') {
						$(':input[name="' + key + '"]').prop('checked', true);
					} else {
						$(':input[name="' + key + '"]').prop('checked', false);
					}
					;

				} else {

					if (type == 'radio') {
						if (value.toUpperCase() == 'TRUE') {
							$(
									"input[name=" + key + " ][value="
											+ value.toLowerCase() + "]").prop(
									'checked', true);
						} else {
							$(
									"input[name=" + key + " ][value="
											+ value.toLowerCase() + "]").prop(
									'checked', true);
						}
						;

					} else {
						
						$(':input[name="' + key + '"]').val(value);
						
					}

				}
				;

			});
			
			
			var propertyTypeAd = $('#propertyTypeAd').val();

			var options = getPropertySubTypeOptions(propertyTypeAd);
			
			$("#propertySubTypeAd").html(options);


			// $.getJSON("http://www.crcasas.com/restlet/file/anuncio/" +
			// adNumber
			// + "/meta" , function(data) {

			// document.getElementById("myFilesContainer").innerHTML = tmpl(
			// "template-ads", data);

			// });

		});

	}
	;

};

$(function() {
	$("select#division1").change(function() {

		getDiv2AsOptions($(this).val(), "select#division2", false);

	})
});

$(function() {
	getDiv2AsOptions(1, 'select#division2', false);

});

function initialize() {

	CR.marker = new google.maps.Marker({
		position : new google.maps.LatLng(9.93555545807, -84.0749969482),
		title : 'Ubicacion de la propiedad',
		map : CR.map,
		draggable : true
	});

	var mapOptions = {
		center : CR.marker.position,
		zoom : 15,
		mapTypeId : google.maps.MapTypeId.ROADMAP
	};

	if (CR.map == undefined) {
		CR.map = new google.maps.Map(document.getElementById("map_canvas"),
				mapOptions);

	}

	google.maps.event.addListenerOnce(CR.map, "bounds_changed", function() {
		google.maps.event.trigger(CR.map, "resize");
	});

	CR.marker.setMap(CR.map);
	CR.marker.setDraggable(true);

	google.maps.event.addListener(CR.marker, 'dragend', function(a) {
		var point = CR.marker.getPosition();
		CR.map.panTo(point);

		$('#latAd').val(a.latLng.lat());
		$('#lngAd').val(a.latLng.lng());

		// a.latLng contains the co-ordinates where the marker was dropped

	});

};

function loadScript() {

	if (!(typeof google === 'object' && typeof google.maps === 'object')) {

		var script = document.createElement("script");
		script.type = "text/javascript";
		script.src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyCCQSUeeH5o-VTopo7LBGLlgIdYj4KRko0&sensor=true&callback=initialize";
		document.body.appendChild(script);
	}

};

function youtube_parser(url) {
	var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
	var match = url.match(regExp);
	if (match && match[7].length == 11) {
		return match[7];
	} else {
		alert("Url incorrecta");
	}
};

function setupFormValidation() {

	$('#formAd').validate(
			{
				rules : {
					priceAd : {
						number : true,
						required : true,
						min : 1,
						rangelength: [1, 10]
					},
					titleAd : {
						required : true,
						minlength: 3
					},
					yearBuiltAd : {
						minlength : 4,
						number : true,
						required: false
					},
					garagesAd : {
						number : true
					},
					floorsAd : {
						number : true
					},
					partialBathsAd : {
						number : true
					},
					bedRoomsAd : {
						number : true
					},
					constructionSizeAd : {
						number : true
					},
					frontSizeAd : {
						number : true
					},
					lotSizeAd : {
						number : true
					}

				},
				messages : {
					priceAd : "Ingrese el precio de la propiedad",
					titleAd : "Ingrese un titulo para su anuncio",
					yearBuiltAd : {
						number : "Ingrese un numero",
						minlength : "Ingrese un numero de 4 cifras o deje en blanco"
					},
					garagesAd : "Por favor ingrese un numero",
					floorsAd : "Por favor ingrese un numero",
					partialBathsAd : "Por favor ingrese un numero",
					bedRoomsAd : "Por favor ingrese un numero",
					constructionSizeAd : "Por favor ingrese un numero",
					frontSizeAd : "Por favor ingrese un numero",
					lotSizeAd : "Por favor ingrese un numero"
				},
							  
				 highlight: function(element) {
					    $(element).closest('.control-group').removeClass('success').addClass('error');
					  },
					  success: function(element) {
						  $(element).closest('.control-group').removeClass('error').addClass('sucess');
					  }
			});

};


function getPropertySubTypeOptions(propertyTypeAd) {
	var options = '';

	if (propertyTypeAd == 'H') {
		options += '<option value="H">Casa</option>';
		options += '<option value="A">Apartamento</option>';
		options += '<option value="C">Condominio</option>';
		options += '<option value="B">Habitaciones</option>';

	}
	;

	if (propertyTypeAd == 'L') {
		options += '<option value="L">Lotes</option>';
		options += '<option value="F">Fincas</option>';
	}
	;

	if (propertyTypeAd == 'C') {
		options += '<option value="M">Local</option>';
		options += '<option value="D">Edificios</option>';
		options += '<option value="W">Bodegas</option>';
		options += '<option value="E">Bovedas</option>';
		options += '<option value="O">Oficinas</option>';
		options += '<option value="T">Lotes Comerciales</option>';

	}
	;

	if (propertyTypeAd == 'V') {
		options += '<option value="V">Vacaciones</option>';
	}
	;

	if (propertyTypeAd == 'P') {
		options += '<option value="P">Proyectos</option>';
	}
	;

	return options;
}

function initForm() {
	
	$('#btnContinueAd').click(function(e) {
		var isValid = 		$('#formAd').valid();
		if (isValid) $('#formAdSubmit').trigger('click');
		else alert('Algunos campos no se completaron. Por favor complete los campos marcados con rojo antes de continuar');
	});

	setSendPhoto();
	
	
	$(".positive-integer").numeric({
		decimal : false,
		negative : false
	}, function() {
		alert("Ingrese solo numeros positivos, no ingrese puntos ni comas");
		this.value = "";
		this.focus();
	});



	$('#formAd').ajaxForm({
		success : showResponse
	});

	


	$("#propertySubTypeAd").change(function() {
		var propertySubTypeAd = $(this).val();
		checkPropertySubConditions(propertySubTypeAd);
	});

	$("#propertyTypeAd").change(function() {

		var propertyTypeAd = $(this).val();

		var options = getPropertySubTypeOptions(propertyTypeAd);
		
		$("#propertySubTypeAd").html(options);

		checkPropertySubConditions($("#propertySubTypeAd").val());

	});

	$('#getDivision2').click(
			function(e) {

				$.getJSON("http://www.crcasas.com/restlet/json/division2/"
						+ $('#newDivision2').val() + "/geo", function(data) {
					centerMap(data.lat, -1 * data.lng);
					$('#latAd').val(data.lat);
					$('#lngAd').val(-1 * data.lng);
				});

			});

	$('#sortPhotos').click(function(e) {

		$(".files").sortable({
			revert : true
		});

	});

	$('#getPosition').click(function(e) {

		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(getPositionSuccess);
		} else {
			alert('Su navegador no permite obtener la ubicacion');
		}

	});

	$('#deleteYouTube')
			.click(
					function(e) {
						var htm = '<iframe width="425" height="350" src= " " frameborder="0" ></iframe>';
						$('#youTube1').html(htm);
						$('#youTube1Url').val('');

					});

	$('#btnYouTube1')
			.click(
					function(e) {

						var URL = $('#youTube1Url').val();

						var VIDEO_ID = youtube_parser(URL);

						var htm = '<iframe width="425" height="350" src= "http://www.youtube.com/embed/'
								+ VIDEO_ID + '" frameborder="0" ></iframe>';

						$('#youTube1').html(htm);

					});

	$('#loadMapYes').click(function(e) {
		loadScript();
		$("#googleMap").show();

	});

	$("#googleMap").hide();

	$("#btnPublish").hide();

	$('#loadMapNo').click(function(e) {
		$("#googleMap").hide();

	});

	getDiv2AsOptions(1, 'select#newDivision2', true);

	$("select#newDivision1").change(function() {
		getDiv2AsOptions($(this).val(), "select#newDivision2", true);
	});

};

function showResponse(responseText, statusText, xhr, $form) {

	if (CR.editAd != responseText) {
		_gaq.push([ '_trackEvent', 'ad', 'create', '#inicio' ]);
		CR.editAd = parseInt(responseText);
	};
	
	
	//$('#saveSucessAlert').show();
	
	if (CR.adType == 'B') window.open("http://www.crcasas.com/w/pagar.html?adId="+ CR.editAd, "_self");
	else {
		alert('Los cambios fueron guardados en el servidor');
		window.open("http://www.crcasas.com/w/misanuncios.html", "_self");
	}
};

function centerMap(lat, lng) {
	var latLng = new google.maps.LatLng(lat, lng);
	CR.marker.position = latLng;
	CR.map.setCenter(latLng);

};

function getPositionSuccess(position) {
	centerMap(position.coords.latitude, position.coords.longitude);
	$('#latAd').val(position.coords.latitude);
	$('#lngAd').val(position.coords.longitude);

};

function newResponse(responseText, statusText, xhr, $form) {
	CR.adId = parseInt(responseText);
	$('#adIdAd').val(parseInt(responseText));
	$("#btnContinueAd").removeAttr("disabled");
	
	initForm();
	setupFormValidation();
	setUserInfo();
	$('#loadingWrapper').remove();
	$("#main").show();
	
	setFormTitle('Ingresando anuncio nuevo No.'+parseInt(responseText));

	
	getAdPhotos(parseInt(responseText));
	
};

function setUserInfo() {
	$('#firstNameAd').val(CR.userFirstName);
	$('#lastNameAd').val(CR.userLastName);
	$('#emailAd').val(CR.userEmail);
	$('#fbIdAd').val(CR.userId);
};

$(document).ready(function() {

		$('#formNew').ajaxForm({
			success : newResponse
		});
	
	var QueryString = getQueryString();
	

	$('#header').load('/w/tabs/headermini.html', function() {
		initHeaderForPublish();
		
	});

	$('#footer').load('/w/tabs/footermini.html');
	$('#loadingWrapper').load('/w/tabs/loading.html');
	$('#main').load('/w/tabs/formpro.html', function() {
		
		setSortable("#photosRow1");
				
		if (QueryString.adId !== undefined) {
			if (QueryString.adId != '0') {
				loadAd(QueryString.adId);
				setupFormValidation();
				initForm();
				//	
				
				CR.adId = QueryString.adId;
				getAdPhotos(QueryString.adId);
				
			}	
			else {
				
				if ( $.cookie("status") !== undefined || $.cookie("uid") !== undefined) {
					
					if ( $.cookie("status") == 'login') {
						
						setFormTitle('Ingresar un nuevo anuncio', 'Complete el formulario y presione continuar');

						CR.userEmail = $.cookie("userEmail");
						CR.userFirstName = $.cookie("userFirstName");
						CR.userLastName = $.cookie("userLastName");
						CR.userId = $.cookie("uid");
						
						CR.adType = 'B';
					
						$('#formNew').clearForm();
						
						$('#emailNewAd').val(CR.userEmail);
						$('#firstNameNewAd').val(CR.userFirstName);
						$('#lastNameNewAd').val(CR.userLastName);
						$('#fbIdNewAd').val($.cookie("uid"));				
						
						$('#formNewSubmit').trigger('click');

					}
				}
				
				
			}
		}
		;

	});

});
