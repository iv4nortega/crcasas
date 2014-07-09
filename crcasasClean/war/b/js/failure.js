$(document).ready(function() {
	var QueryString = getQueryString();
	//alert(QueryString.cm);
	$('#header').load('/tabs/header.html');
	$('#footer').load('/tabs/footer.html');	
	$('#main').load('/tabs/failure.html', function(){
		
		if(QueryString.cm !== undefined ) {
			$("#failureShow").attr("href", "/index.html");
			$("#failureEdit").attr("href", "/index.html");			
		};
		

		
	});	
  	

	
	
});
