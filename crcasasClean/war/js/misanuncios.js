if (CR === undefined)
	var CR = {};

function onConnected() {
	$('#main').load('/tabs/myads.html', function() {

		getMyAds();
	});
	showConnectedUser();

};


function onNonAuthorized() {
	$('#main').load('/tabs/notconnect.html');
	clearConnectedUser();

};

function onNotConnected() {
	$('#main').load('/tabs/notconnect.html');
	clearConnectedUser();
};

// Facebook login callback 
function loginCallback() {
	
    FB.getLoginStatus(function(response) {
        if (response.status === 'connected') {
        	

        } 
        else  alert('Para ingresar anuncios primero debe ingresar con su Login de Facebook');

       });
	
}

function setDeleteCnfBtn(selector) {

	$(selector)
	.click(
			function(e) {

				$.ajax({
							url : '/restlet/json/anuncio/'
									+ CR.adToDelete
									+ '/form/delete/'
									+ CR.userId,
							type : 'DELETE',
							dataType : 'json',
							data : '',
							success : function(
									response) {
								console.log('DELETE completed'+ response);															
								$('#'+CR.adToDelete).remove();

							},
						    error: function(XMLHttpRequest, textStatus, errorThrown) { 
								$('#'+CR.adToDelete).remove();
								console.log('Text status: '+ textStatus+' errorThrown: '+ errorThrown);					        
						    }
						});				
				
				
				
			});
	
}


function setDeleteBtn() {

	$('.btnDeleteAd')
			.click(
					function(e) {
	                    CR.adToDelete = $(this).data('sourcekey');
					});

}


function setPostBtn() {

	$('.btnPostAd')
			.click(
					function(e) {
	                    var sourceKey = $(this).data('sourcekey');
	                    var sourceUrl = $(this).data('sourceurl');

	                    var imageUrl = $(this).data('imageurl');
	                    var description = $(this).data('description');
	                    var name = $(this).data('caption');
	                    var caption = $(this).data('address');

	                    postToFeed(sourceKey, name, caption, description, sourceUrl, imageUrl);

					});

}


function setPauseBtn() {
	$('.btnPauseAd')
	.click(
			function(e) {
				
				
                var adId = $(this).data('adid');
                var adStatus = $(this).data('status');
                var status = "pause"    
                var targetStatus = "activate"    
               	
                if (adStatus =='S') {
                	status = 'activate';
                	targetStatus = 'pause';
                }
                
                
				$.ajax({
					url : '/restlet/json/anuncio/'
							+ adId
							+ '/form/'
							+ targetStatus
							+ '/'
							+ CR.userId,
					type : 'PUT',
					dataType : 'json',
					data : '',
					success : function(
							response) {
						if (status == 'activate') $(this).html('<i class="icon-pause"></i>');
						else $(this).html('<i class="icon-play"></i>');
					},
				    error: function(XMLHttpRequest, textStatus, errorThrown) { 

						if (status == 'activate') $(this).html('<i class="icon-pause"></i>');
						else $(this).html('<i class="icon-play"></i>');
				    }
				});	               

			});

}

function getMyAds(){


	showSpinMessage('#spinMessage','Cargando los anuncios en el servidor, por favor espere...');
	
    $.getJSON('/restlet/json/user/' + CR.userId, function (data) {
    	$('#myAdsTabContainer').nextAll().remove();
        $("#myAdsTabContainer").after(
          CR.adTemplate.render(data));

        //if (data.length === 0) alert('Para  Dele click a nuevo anuncio para incluir un anuncio nuevo.');

        setDeleteBtn();
        setPostBtn();
        setPauseBtn();
        
        fbParse('myAdsTable');

    	hideSpinMessage('#spinMessage');

    });



  };



$(document).ready(function() {

	// 104922512993964  crcasastest1
	// 399492506851789 localhost

	window.onerror = function(message, file, line) {
		   _gaq.push(['_trackEvent', 'JS Error', file + ':' + line + '\n\n' + message]);
		};
		
		
	
	  $.getScript('//connect.facebook.net/en_UK/all.js', function(){
		    FB.init({
		      appId: '174633095925807',
		    });     
		    $('#login-btn').removeAttr('disabled');
		    				
			FB.getLoginStatus(function(response) {
				checkLoginStatus(response);
				}, true);
			
			FB.Event.subscribe('auth.statusChange', function(response) {checkLoginStatus(response);});	
			
			FB.XFBML.parse();
		    
		  });
	  
	var QueryString = getQueryString();
	
	CR.adTemplate = $.templates( "#myAdsTemplate2");
	
	$('#header').load('/tabs/headermini.html', function() {
		initHeaderForPublish();
		
	});

	$('#footer').load('/tabs/footermini.html');
	$('#main').load('/tabs/loading.html');

	setDeleteCnfBtn('#deleteAdConfirmed');

});
