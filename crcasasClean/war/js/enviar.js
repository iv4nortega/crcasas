if (CR === undefined)
	var CR = {};


CR.options = { 
        success:       showResponse,  // post-submit callback 
        clearForm: true,        // clear all form fields after successful submit 
    }; 

function validate(formData, jqForm, options) { 
 
    return true; 
}

function showResponse(responseText, statusText, xhr, $form)  {  
    alert('El correo fue enviado a sus amigos. Gracias por usar crCasas!'); 
}

$(document).ready(function() {

	var QueryString = getQueryString();	

	$('#oneMore')
	.click(
			function(e) {
				$( ".cc" ).first().show();
				$( ".cc" ).first().removeClass('cc');
				if ( $( ".cc" ).first().length == 0 ) $( "#oneMore" ).hide();
			});
	
    $('#forwardForm').ajaxForm(CR.options);
    
    var first = Math.round(Math.random()*10);
    var second = Math.round(Math.random()*10);    
    
    $('#securityQuestion').html(first+ ' + ' + second + ' = ?');
    
    $( "#security" ).change(function() {
    	  
    	  var result =  $( "#security" ).val();
    	  
    	  if (result==first+second)  $('#submit').removeAttr('disabled');   	 
    	  else alert( "Buen intento. Por favor vuelvalo a intentar!" );
    	  
    	});
    
    adId = QueryString.id;
    $( "#adId" ).val(adId);
    $('#adIdTitle').html(adId);
    
    
});

	

