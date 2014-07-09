$(document).ready(function() {
	var QueryString = getQueryString();
	//alert(QueryString.cm);
	$('#header').load('/w/tabs/header.html');
	$('#footer').load('/w/tabs/footer.html');	
	$('#main').load('/w/tabs/failure.html', function(){
		
		if(QueryString.cm !== undefined ) {
			$("#failureShow").attr("href", "/w/index.html");
			$("#failureEdit").attr("href", "/w/index.html");			
		};
		

		
	});	
  	

	
	
});
