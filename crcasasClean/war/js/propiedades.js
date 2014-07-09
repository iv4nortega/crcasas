 

$(document).ready( function() {
			
	window.onerror = function(message, file, line) {
		   _gaq.push(['_trackEvent', 'JS Error', file + ':' + line + '\n\n' + message]);
		};
	
	$("[rel='tooltip']").tooltip();
	
	$(".carousel").carousel({
		interval:6000
	});
	
	initContactForm();

	initSellerForm();
	
	get_ad('873049');
	get_ad('142349');

	get_ad('169298');
	
	get_ad('621688');
	
	get_ad('136559');	
	
	get_ad('940780');	

	get_ad('100617');
	get_ad('521340');
	get_ad('835815');
	
	get_ad('3309063');


	get_ad('377426');


});


function get_ad(adNumber) {
	
	$.getJSON("/restlet/json/anuncio/" + adNumber,
			function(data) {
						
				var template = $.templates("#property");
				
				var property = template.render(data);
				$( "#loading" ).remove();
				$("#propiedades").append(property);
				

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
	
	
    $('#contactForm').ajaxForm(function() { 
        alert("Gracias por su consulta!"); 
    });
	
	
};


function sellerFormResponse(responseText, statusText, xhr, $form) {
	$('#sellerForm').clearForm();
	alert('Su mensaje ha sido enviado.');
};

function sellerFormBefore(formData, jqForm, options) {
	return $('#sellerForm').valid();
};


function initSellerForm() {
	
	
    $('#sellerForm').ajaxForm(function() { 
        alert("Gracias por su consulta!"); 
    });
	
	
	
};


