
// obtiene las fotos de un anuncio y las muestra en el formulario
function getAdPhotos(sourceKey) {
	
	
	$.getJSON(
			"/restlet/json/imagen/" + sourceKey
					, function(data) {

				$("#photosRow").empty();
				$.each(data, function(i, obj) {					
					  displayPhoto2(obj);
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

function setPhotoServletValues(sourceKey, id, key, position, fbId, action) {
	$('#sourceKeyPhoto').val(sourceKey);
	$('#idPhoto').val(id);
	$('#keyPhoto').val(key);
	$('#positionPhoto').val(position);
	$('#fbIdPhoto').val(fbId);
	$('#actionPhoto').val(action);
	return true;
};

// guarda una foto en el servidor
function savePhoto(sourceKey, id, key, position, fbId, action) {
	setPhotoServletValues(sourceKey, id, key, position, fbId, action);
	 
	var queryString = $('#photoServlet').formSerialize();
	$.post('/forms/photo', queryString);	
};


function setPhotoServlet() {
	
	$('#photoServlet').ajaxForm({
		success : photoServletResponse
	});
	
	

	
};

// habilita la funcionalidad para mover las fotos
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
				 $.post('/forms/photo', queryString);
				 
				 alert("#tr-"+ key);
				 $("#tr-"+ key).remove();
				 


			});


		
		
	}
	
	
	 
};

function displayPhoto2(obj) {
	
	if (typeof(obj.key) != "undefined") {
		
		 $("#photosRow").append($("#photoViewTemplate2").render(obj));

		 setSortable("#photosRow");
		
		 	
			 $('[name="'+obj.key+'"]').click(function(e) {

				 var key = $(this).attr('name');
				
				 var sourceKey = $( this ).attr('sourceKey');
				 				 
				 setPhotoServletValues('', '', key, '', '', 'D');
				 
				 var queryString = $('#photoServlet').formSerialize();
				 $.post('/forms/photo', queryString);
				 
				 $(this).parent().parent().remove();

				 saveOrder();
				 
			});
		
	}
	 
};



function photoResponse(responseText, statusText, xhr, $form) {

	//hideLoading();
	
	$("#btnSendPhoto").removeAttr("disabled");
	
	// COMPATIBILITY ISSUES WITH INTERNET EXPLORER
	// obj = JSON && JSON.parse(responseText) || $.parseJSON(responseText);

	stopSpin("#send-icon");

	
	
	obj = $.parseJSON(responseText);
	 
	
	 
	 if (obj != null) {
		 
		 if (typeof(obj.key) != "undefined")
		 {
			 var n = $(".fileKey").length;
			 n++;
			 
			 savePhoto(CR.sourceKey, '', obj.key, n, '', 'S');

			 displayPhoto2(obj);
			 
		 } else
		 {
			 alert('No fue posible subir el archivo. Revise el formato del archivo y vuelva a intentar. Solo se pueden subir fotos en los formatos siguientes: JPEG, PNG, WEBP, GIF, BMP, TIFF e ICO. Tambien podria ser que su browser este desactualizado. Puede continuar y guardar su anuncio e intentar subir las fotos en otro momento con un browser mas actualizado');
			 
		 }	 
		 


	 } else {
		 alert('No fue posible subir su foto. Es posible que su browser este desactualizado. Puede guardar su anuncio e intentar subir las fotos con un browser mas actualizado');
	 }

	 

	
};


function showLoading() {

	 $("#photosRow").append("<tr><td id='photoLoader' class='col-sm-3 adPhoto thumbnail' style='line-height: 20px;'><img src='img/ajax_loader_blue_128.gif' /></td><tr>");
		
};

function hideLoading() {
	
	$('#photoLoader').remove();
};

function sendPhotoClick() {
	$.getJSON('/restlet/file/url2', function(result) {
		
		startSpin("#send-icon");

		$('#frmPhoto').ajaxForm({
			success : photoResponse,
			url : result.url

		});

		$('#frmPhotoSubmit').trigger('click');
		
		$("#btnSendPhoto").attr("disabled", "disabled");
		
		resetFileInput();
		
		//showLoading();
		
	});	
}


function resetFileInput() {
	
	$("#fileItemCanvas").empty();
	$("#fileItemCanvas").html( 	
			'<label for="fileItem">Seleccionar</label>' + 
			'<input id="fileItem" type="file" name="fileItem"/>' 
	);
}

function setSendPhoto() {
	
	$("#btnSendPhoto").unbind();
	
	$('#btnSendPhoto').click(sendPhotoClick);
};



function saveOrder() {
	var count = 0;

	$('.fileKey').each(
			function() {
				count++;
				
				var sourceKey = parseInt($('#sourceKeyAd').val());
				var key = $(this).attr('id');
				
				savePhoto(sourceKey, '', key, count, '', 'S');

			});
	
	resetPhotos();
	
};

function resetPhotos() {
	
	
	getAdPhotos(CR.sourceKey);
}