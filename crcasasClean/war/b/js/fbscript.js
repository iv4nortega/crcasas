// Additional JS functions here
// var fbid = '174633095925807'; // production
// var fbid = '104922512993964';  // development 6.crcasas1.appspot.com

if (CR === undefined)
	var CR = {};





function fbParse(id) {
	FB.XFBML.parse(document.getElementById(id));
}


function getFBData(response) {

	CR.userId = response.authResponse.userID;
	
	FB.api('/me', function(response2) {
		CR.firstName = response2.first_name;
		CR.lastName  = response2.last_name;
		CR.userEmail = response2.email;
		CR.userName = CR.firstName+' '+CR.lastName;
		
		if (typeof(setUserName) != "undefined") { 
		    setUserName(CR.userName);
		}
		
	});
	

	CR.userPic = "http://graph.facebook.com/" + response.authResponse.userID + "/picture";
	
};

function clearFBData() {
	
	CR.userId = '';
	CR.firstName = '';
	CR.lastName = '';
	CR.userName = '';
	CR.userPic = '';
	CR.userEmail = '';
	
};

function checkLoginStatus(response) {

	if (response.status === 'connected') {
				
		getFBData(response);

		_gaq.push([ '_trackSocial', 'Facebook', 'connected', '#anunciar' ]);
		onConnected();



	} else if (response.status === 'not_authorized') {

		_gaq
				.push([ '_trackSocial', 'Facebook', 'not_authorized',
						'#anunciar' ]);

		
		clearFBData();

		onNonAuthorized();



	} else {
		clearFBData();
		onNotConnected();

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
		_gaq.push([ '_trackSocial', 'Facebook', 'post', adId ]);
	}

	FB.ui(obj, callback);
}


function fbLogin() {
	FB
			.login(
					function(response) {
						// DO I NEED TO HANDLE RESPONSE?
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
