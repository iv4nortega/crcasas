// Additional JS functions here
// var fbid = '174633095925807'; // production
// var fbid = '104922512993964';  // development 6.crcasas1.appspot.com

if (CR === undefined)
	var CR = {};

var fbStatus = "";

var fbid = '174633095925807';

window.fbAsyncInit = function() {
	FB.init({
		appId : fbid, // App ID
		channelUrl : 'http://www.crcasas.com/channel.html', // Channel File
		status : true, // check login status
		cookie : true, // enable cookies to allow the server to access the
		// session
		xfbml : true
	// parse XFBML
	});

	// Additional init code here

	FB.Event.subscribe('auth.authResponseChange', function(response) {

		changeStatus(response);

	});

	FB.Event.subscribe('edge.create', function(targetUrl) {
		_gaq.push([ '_trackSocial', 'facebook', 'like', targetUrl ]);
	});

	FB.Event.subscribe('edge.remove', function(targetUrl) {
		_gaq.push([ '_trackSocial', 'facebook', 'unlike', targetUrl ]);
	});

	FB.Event.subscribe('message.send', function(targetUrl) {
		_gaq.push([ '_trackSocial', 'facebook', 'send', targetUrl ]);
	});

};

// Load the SDK Asynchronously
(function(d) {
	var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
	if (d.getElementById(id)) {
		return;
	}
	js = d.createElement('script');
	js.id = id;
	js.async = true;
	js.src = "//connect.facebook.net/es_LA/all.js";
	ref.parentNode.insertBefore(js, ref);
}(document));

function changeStatusToConnected(name, imageUrl) {

	$("#login-btn").hide();
	
	$("#btnAdvertiseLogin").hide();

	$("#logout-btn").show();
	$("#user-picture-canvas").show();
	$("#btnGoToMyAds").show();;
	$("#txtPublishGuide").hide();

	$("#user-name").empty().append(name + "&nbsp;<b class='caret'></b>");

	$("#user-picture").attr("src", imageUrl);
	
	$.getJSON("http://www.crcasas.com/restlet/json/hasads/" + CR.userId,
			function(data) {
			    if (!data) $("#btnGoToMyAds").attr("href", "/publicar.html?adId=0")
			    else $("#btnGoToMyAds").attr("href", "/misanuncios.html");
			});
	
};

function changeStatusToNotConnected() {
	$("#login-btn").show();
	$("#btnAdvertiseLogin").show();

	$("#logout-btn").hide();
	$("#user-picture-canvas").hide();
	$("#btnGoToMyAds").hide();
	
	$("txtPublishGuide").show();
	
	CR.userFirstName = '';
	CR.userLastName = '';
	CR.userEmail = '';
	CR.userId = '';

	$.cookie("uid", '');
	$.cookie("status", 'logout');

}

function changeStatus(response) {

	fbStatus = response.status;
	if (response.status === 'connected') {
		// the user is logged in and has authenticated your
		// app, and response.authResponse supplies
		// the user's ID, a valid access token, a signed
		// request, and the time the access token
		// and signed request each expire
		// y se despliega el button de nuevo anuncio

		var uid = response.authResponse.userID;
		var accessToken = response.authResponse.accessToken;

		_gaq.push([ '_trackSocial', 'facebook', 'login', '#inicio' ]);

		FB.api('/me', function(response) {
			var fbname = response.name;

			CR.userFirstName = response.first_name;

			CR.userLastName  = response.last_name;

			CR.userEmail = response.email;

			CR.userId = uid;

			$.cookie("uid", uid);
			$.cookie("status", 'login');
			$.cookie("userFirstName", CR.userFirstName );
			$.cookie("userLastName", CR.userLastName );
			$.cookie("userEmail", CR.userEmail);
			
			$('#formUser').clearForm();
			$('#idF01').val(CR.userId);
			$('#emailF01').val(CR.userEmail);
			$('#firstNameF01').val(CR.userFirstName);
			$('#lastNameF01').val(CR.userLastName);

			$('#formUserSubmit').trigger('click');

			var img_link = "http://graph.facebook.com/" + response.id
					+ "/picture";

			changeStatusToConnected(fbname, img_link);

		});

	} else if (response.status === 'not_authorized') {
		// the user is logged in to Facebook,
		// but has not authenticated your app

		_gaq.push([ '_trackSocial', 'facebook', 'not_authorized', '#inicio' ]);

		changeStatusToNotConnected();

	} else {
		// the user isn't logged in to Facebook.

		changeStatusToNotConnected();
	}
}

function postToFeed(adId, name, caption, description, url, picture) {

	// calling the API ...
	var obj = {
		method : 'feed',
		link : url,
		picture : picture,
		name : name,
		caption : caption,
		description : description
	};

	function callback(response) {
		// document.getElementById('msg').innerHTML = "Post ID: " +
		// response['post_id'];
		_gaq.push([ '_trackSocial', 'facebook', 'post', adId ]);
	}

	FB.ui(obj, callback);
}


function fbLogin() {
	FB
			.login(
					function(response) {
						if (response.authResponse) {
							console
									.log('Welcome!  Fetching your information.... ');

							FB
									.api(
											'/me',
											function(
													response) {
												console
														.log('Good to see you, '
																+ response.name
																+ '.');

											});
						} else {
							console
									.log('User cancelled login or did not fully authorize.');

						}
					}, {
						scope : 'email'
					});

}


function likeThis(url) {

	FB.api(
			  'me/og.likes',
			  'post',
			  {
			    object: url
			  },
			  function(response) {
			    // handle the response
			  }
			);
	
}