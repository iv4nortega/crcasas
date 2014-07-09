var CR = {};

function payServletResponse(responseText, statusText, xhr, $form) {
	
};

function setPayServletValues(adId, fbId, type) {
	$('#adIdPay').val(adId);
	$('#fbIdPay').val(fbId);
	$('#typePay').val(type);
	return true;
};

function savePay(adId,  fbId, type) {
	setPayServletValues(adId, fbId, type);
	 
	var queryString = $('#payServlet').formSerialize();
	$.post('http://www.crcasas.com/forms/pagar', queryString);	
};

function setPayServlet() {
	
	$('#payServlet').ajaxForm({
		success : payServletResponse
	});
	
	

	
};


function setSendPayment() {
	$('#btnPublishBasicAd').click(function(e) {
		savePay(CR.adId, CR.userId, 'B');
		alert('su anuncio fue publicado');
		
		window.open("http://www.crcasas.com/w/misanuncios.html", "_self");
		
	});
};

function hidePayButtons() {
	$('#checkPremiumPlusContainer').hide();
	$('#payPremiumContainer').hide();
	$('#payPremiumPlusContainer').hide();

	$('#checkExclusivePlusContainer').hide();
	$('#payExclusiveContainer').hide();
	$('#payExclusivePlusContainer').hide();

	$('#payBasicContainer').hide();

	$('#formBtns2Container').hide();

};

function premiumChecked() {
	exclusiveUnChecked();
	basicUnChecked();
	$('#checkPremiumPlusContainer').show();
	$('#payPremiumContainer').show();
	$('#payPremiumPlusContainer').hide();
};

function exclusiveChecked() {
	premiumUnChecked();
	basicUnChecked();
	$('#checkExclusivePlusContainer').show();
	$('#payExclusiveContainer').show();
	$('#payExclusivePlusContainer').hide();
};

function basicChecked() {
	premiumUnChecked();
	exclusiveUnChecked();
	$('#payBasicContainer').show();

};

function setFormAdSubTitle(subTitle) {
	$('#formAdSubtitle').html(subTitle);

};

function premiumUnChecked() {
	$('input[name=checkPremium]').attr('checked', false);
	$('input[name=checkPremiumPlus]').attr('checked', false);

	$('#checkPremiumPlusContainer').hide();
	$('#payPremiumContainer').hide();
	$('#payPremiumPlusContainer').hide();
};

function exclusiveUnChecked() {
	$('input[name=checkExclusive]').attr('checked', false);
	$('input[name=checkExclusivePlus]').attr('checked', false);

	$('#checkExclusivePlusContainer').hide();
	$('#payExclusiveContainer').hide();
	$('#payExclusivePlusContainer').hide();
};

function basicUnChecked() {
	$('input[name=checkBasic]').attr('checked', false);
	$('#payBasicContainer').hide();

};

function isChecked(selector) {
	if ($(selector).is(':checked'))
		return true;
	else
		return false;

};

function premiumPlusChecked() {
	$('#payPremiumContainer').hide();
	$('#payPremiumPlusContainer').show();
};

function exclusivePlusChecked() {
	$('#payExclusiveContainer').hide();
	$('#payExclusivePlusContainer').show();
};

function premiumPlusUnChecked() {

	$('#payPremiumPlusContainer').hide();
	$('#payPremiumContainer').show();

};

function exclusivePlusUnChecked() {
	$('#payExclusivePlusContainer').hide();
	$('#payExclusiveContainer').show();
};

$(document).ready(function() {

	var QueryString = getQueryString();

	$('#header').load('/w/tabs/headermini.html', function() {
		initHeaderForPublish();
		
	});

	
	$('#footer').load('/w/tabs/footermini.html');
	$('#loadingWrapper').load('/w/tabs/loading.html');
	
	$('#main').load('/w/tabs/paypal.html', function() {
		
		
		$('#checkPremium').click(function(e) {

			if (isChecked('#checkPremium'))
				premiumChecked();
			else
				premiumUnChecked();
		});

		$('#checkPremiumPlus').click(function(e) {

			if (isChecked('#checkPremiumPlus'))
				premiumPlusChecked();
			else
				premiumPlusUnChecked();

		});

		$('#checkExclusive').click(function(e) {

			if (isChecked('#checkExclusive'))
				exclusiveChecked();
			else
				exclusiveUnChecked();
		});

		$('#checkExclusivePlus').click(function(e) {
			if (isChecked('#checkExclusivePlus'))
				exclusivePlusChecked();
			else
				exclusivePlusUnChecked();
		});

		$('#checkBasic').click(function(e) {

			if (isChecked('#checkBasic'))
				basicChecked();
			else
				basicUnChecked();
		});
		
		
		if ( (QueryString.adId !== undefined) 
				&& ($.cookie("status") !== undefined) 
				&& ($.cookie("uid") !== undefined)
				&& ($.cookie("status") == 'login')) {
			
			CR.adId = QueryString.adId;
			CR.userId = $.cookie("uid");
						
			$("#returnBtn").attr("href", "http://www.crcasas.com/w/publicar.html?adId="+QueryString.adId)
			
			setFormTitle('Anuncio No. ' + QueryString.adId,'Seleccione el tipo de anuncio');

			setPayServlet();
			setSendPayment();
			$('#loadingWrapper').remove();
			$("#main").show();
			$('.adIdPay').val(CR.adId);
			
			

		}

	});
});
