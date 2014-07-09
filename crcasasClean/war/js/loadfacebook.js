var hostname = document.location.hostname;

switch (hostname)
{
   case 'localhost':
          
          window.fbAsyncInit = function() {
		    FB.init({
			    appId      : '399492506851789', // local host
			    channelUrl : '//WWW.CRCASAS.COM/channel.html', // Channel File
		      status     : true, // check login status
		      cookie     : true, // enable cookies to allow the server to access the session
		      xfbml      : true  // parse XFBML
		    });
			//FB.getLoginStatus(function(response) { checkLoginStatus(response); });
			FB.Event.subscribe('auth.authResponseChange', function(response) {checkLoginStatus(response);});		 
		};
   		break
   case "crcasastest1.appspot.com":
          
          window.fbAsyncInit = function() {
		    FB.init({
			    appId      : '104922512993964', // crcasastest1
			    channelUrl : '//WWW.CRCASASTEST1.COM/channel.html', // Channel File
		      status     : true, // check login status
		      cookie     : true, // enable cookies to allow the server to access the session
		      xfbml      : true  // parse XFBML
		    });
			//FB.getLoginStatus(function(response) { checkLoginStatus(response); });
			FB.Event.subscribe('auth.authResponseChange', function(response) {checkLoginStatus(response);});		 
		};          
   		break
   case "crcasas.com": 
          window.fbAsyncInit = function() {
		    FB.init({
			    appId      : '174633095925807', // crcasas.com
			    channelUrl : '//WWW.CRCASAS.COM/channel.html', // Channel File
		      status     : true, // check login status
		      cookie     : true, // enable cookies to allow the server to access the session
		      xfbml      : true  // parse XFBML
		    });
			//FB.getLoginStatus(function(response) { checkLoginStatus(response); });
			FB.Event.subscribe('auth.authResponseChange', function(response) {checkLoginStatus(response);});		 
		};    
       break;
   default: 
       alert('Where I am?');
       break;
}

  // Load the SDK asynchronously
  
(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/all.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));
