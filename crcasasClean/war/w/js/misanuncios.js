if (CR === undefined)
	var CR = {};


function setDeleteBtn() {

	$('.btnDeleteAd')
			.click(
					function(e) {
						
	                    var adId = $(this).data('adid');

						bootbox
								.confirm(
										"El anuncio se borrara permanentenmente. Desea continuar?",
										function(result) {

											if (result) {
												
												$('#'+adId).remove();
												
												$
														.ajax({
															url : '/restlet/json/anuncio/'
																	+ adId
																	+ '/form/delete/'
																	+ CR.userId,
															type : 'DELETE',
															dataType : 'json',
															data : '',
															success : function(
																	response) {
																console
																		.log('DELETE completed'
																				+ response);																
															}
														});

											}

										});

					});

}


function setPostBtn() {

	$('.btnPostAd')
			.click(
					function(e) {
						
	                    var adId = $(this).data('adid');
	                    var imageUrl = $(this).data('imageurl');
	                    var description = $(this).data('description');
	                    var name = $(this).data('caption');
	                    var caption = $(this).data('address');

	                    postToFeed(adId, name, caption, description, 'http://www.crcasas.com/w/home.html#!anuncio='+adId, imageUrl);



					});

}


function getMyAds(){

    $.getJSON('/restlet/json/user/' + CR.userId, function (data) {
        $("#myAdsTabContainer").html(
          $("#myAdsTemplate").render(data)


      );

        //if (data.length === 0) alert('Para  Dele click a nuevo anuncio para incluir un anuncio nuevo.');

        setDeleteBtn();
        setPostBtn();
        

    });



  };



$(document).ready(function() {

	
	var QueryString = getQueryString();
	

	$('#header').load('/w/tabs/headermini.html', function() {
		initHeaderForPublish();
		
	});

	$('#footer').load('/w/tabs/footermini.html');
	$('#loadingWrapper').load('/w/tabs/loading.html');

	
	if ( $.cookie("status") !== undefined || $.cookie("uid") !== undefined) {
		
		if ( $.cookie("status") == 'login') {
			
			CR.userId = $.cookie("uid");
			$('#loadingWrapper').remove();
			$("#main").show();
			$('#main').load('/w/tabs/myads.html', function() {

				getMyAds();
			});
			
			
		}
	}	

	


});
