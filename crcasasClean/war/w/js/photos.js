function getAdPhotos(adId) {
	
	$.getJSON(
			"http://www.crcasas.com/restlet/json/imagen/" + adId
					, function(data) {

				$.each(data, function(i, obj) {
					
					  displayPhoto(obj);
					});

			}).success(function() {
	}).error(function(jqXHR, textStatus, errorThrown) {
		console.log("error " + textStatus);
		console.log("incoming Text " + jqXHR.responseText);
		if (CR.debugMode)
			alert('Error ')
	}).complete(function() {
	});

	
};

function photoServletResponse(responseText, statusText, xhr, $form) {
	
};

function setPhotoServletValues(adId, id, key, position, fbId, action) {
	$('#adIdPhoto').val(adId);
	$('#idPhoto').val(id);
	$('#keyPhoto').val(key);
	$('#positionPhoto').val(position);
	$('#fbIdPhoto').val(fbId);
	$('#actionPhoto').val(action);
	return true;
};

function savePhoto(adId, id, key, position, fbId, action) {
	setPhotoServletValues(adId, id, key, position, fbId, action);
	 
	var queryString = $('#photoServlet').formSerialize();
	$.post('http://www.crcasas.com/forms/photo', queryString);	
};

function setPhotoServlet() {
	
	$('#photoServlet').ajaxForm({
		success : photoServletResponse
	});
	
	

	
};

function setSortable(selector) {
	
	 $( selector ).sortable({
	      connectWith: ".connectedSortable",
        containment: "#photosFormContainer",
        stop: function(event, ui) {
            saveOrder();
        }
	    }).disableSelection();
	
	
};

function displayPhoto(obj) {
	
	if (typeof(obj.key) != "undefined") {
		
		 if (CR.photosCounter<=3) {
			 
			 $("#photosRow"+CR.photosRow).append($("#photoViewTemplate").render(obj));
			 CR.photosCounter++;
		 } else {
			 CR.photosCounter = 0;
			 CR.photosRow++;
			 $("#photosFormContainer").append("<div class='row-fluid'><ul id='photosRow"+CR.photosRow+"' class='thumbnails connectedSortable'></ul></div>");
			 $("#photosRow"+CR.photosRow).append($("#photoViewTemplate").render(obj));
			 
			 setSortable("#photosRow"+CR.photosRow);
			 CR.photosCounter++;
			 
		 };
		 	
			 $('[name="'+obj.key+'"]').click(function(e) {

				 var key = $(this).attr('name');
				
				 setPhotoServletValues('', '', key, '', '', 'D');
				 
				 var queryString = $('#photoServlet').formSerialize();
				 $.post('http://www.crcasas.com/forms/photo', queryString);
				 
				 $(this).parent().remove();

			});


		
		
	}
	
	
	 
};

function photoResponse(responseText, statusText, xhr, $form) {

	hideLoading();
	
	$("#btnSendPhoto").removeAttr("disabled");
	
	// COMPATIBILITY ISSUES WITH INTERNET EXPLORER
	// obj = JSON && JSON.parse(responseText) || $.parseJSON(responseText);


	
	
	obj = $.parseJSON(responseText);
	 
	
	 
	 if (obj != null) {
		 
		 if (typeof(obj.key) != "undefined")
		 {
			 var n = $(".fileKey").length;
			 n++;
			 
			 savePhoto(CR.adId, '', obj.key, n, '', 'S');

			 displayPhoto(obj);
			 
		 } else
		 {
			 alert('No fue posible subir su foto. Es posible que su browser este desactualizado. Puede guardar su anuncio e intentar subir las fotos con un browser mas actualizado');
			 
		 }	 
		 


	 } else {
		 alert('No fue posible subir su foto. Es posible que su browser este desactualizado. Puede guardar su anuncio e intentar subir las fotos con un browser mas actualizado');
	 }

	 

	
};


function showLoading() {
	
	 if (CR.photosCounter<=3) {
		 
		 $("#photosRow"+CR.photosRow).append("<li id='photoLoader' class='span3 adPhoto thumbnail' style='line-height: 20px;'><img src='img/ajax_loader_blue_128.gif' /></li>");
		 
		 
	 } else {

		 $("#photosFormContainer").append("<div id='photoLoader' class='row-fluid'><ul id='photoLoader2' class='thumbnails sortable'></ul></div>");
		 $("#photoLoader2").append("<li id='photoLoader' class='span3 adPhoto thumbnail' style='line-height: 20px;'><img src='img/ajax_loader_blue_128.gif' /></li>");
		 
	 }
	
};

function hideLoading() {
	
	$('#photoLoader').remove();
};


function setSendPhoto() {
	$('#btnSendPhoto').click(function(e) {
		
		$.getJSON('http://www.crcasas.com/restlet/file/url2', function(result) {

			$('#frmPhoto').ajaxForm({
				success : photoResponse,
				url : result.url

			});

			$('#frmPhotoSubmit').trigger('click');

			$('#fileRemove').trigger('click');
			
			$("#btnSendPhoto").attr("disabled", "disabled");
			
			showLoading();
			
		});

	});
};



function saveOrder() {
	var count = 0;

	$('.fileKey').each(
			function() {
				count++;
				
				var adId = parseInt($('#adIdAd').val());
				var key = $(this).attr('id');
				
				savePhoto(adId, '', key, count, '', 'S');

			});
	
	resetPhotos();
	
};

function resetPhotos() {
	
	$('#photosFormContainer').empty();
	$("#photosFormContainer").append("<div class='row-fluid'><ul id='photosRow1"+"' class='thumbnails connectedSortable'></ul></div>");
	
	getAdPhotos(CR.adId);
}