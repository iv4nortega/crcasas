// Additional JS functions here
// var fbid = '174633095925807'; // production
// var fbid = '104922512993964';  // development 6.crcasas1.appspot.com

if (CR === undefined)
	var CR = {};

function fbParse(id) {
	FB.XFBML.parse(document.getElementById(id));
}


function getFBData(response) {

	userId = response.authResponse.userID;
	
	FB.api('/me', function(response2) {
		firstName = response2.first_name;
		lastName  = response2.last_name;
		userEmail = response2.email;
		userName = firstName+' '+lastName;
		userPic = "http://graph.facebook.com/" + response.authResponse.userID + "/picture";
		setFBData(userId, firstName, lastName, userName, userPic, userEmail);		
		if (typeof(setUserName) != "undefined") { 
		    setUserName(userName);
		}
		if (typeof(onConnected) != "undefined") { 
			onConnected();
		}
	});
};

function setFBData(userId, firstName, lastName, userName, userPic, userEmail) {
	
	CR.userId = userId;
	CR.firstName = firstName;
	CR.lastName = lastName;
	CR.userName = userName;
	CR.userPic = userPic;
	CR.userEmail = userEmail;
	
	updateUser();
	
};

function clearFBData() {
	
	CR.userId = '';
	CR.firstName = '';
	CR.lastName = '';
	CR.userName = '';
	CR.userPic = '';
	CR.userEmail = '';
	
};

function onFBInit(){	
	$(".showAfterFBInit").show();
	$(".hideAfterFBInit").hide();
	$(".showOnFBunknown").show();	
	
	$(".enableAfterFBInit").prop('disabled', false);
	
};

function updateUser() {
	$('#idF01').val(CR.userId);
	$('#emailF01').val(CR.userEmail);
	$('#firstNameF01').val(CR.firstName);
	$('#lastNameF01').val(CR.lastName);
	$('#formUserSubmit').trigger('click');

}


function checkLoginStatus(response) {
	
	$('#login-btn').removeAttr('disabled');
	
	if (response.status === 'connected') {
				
		getFBData(response);
		
		_gaq.push([ '_trackSocial', 'Facebook', 'connected', '#anunciar' ]);
		



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



function getFBLoginStatus(response) {
		
	if (response.status === 'connected') {
		getFBData(response);
		$(".showOnFBconnected").show();
		$(".hideOnFBconnected").hide();
		
		// NO llama a onConnected, por que getFBData lo llama. Esto para tener los datos de FB antes de hacer el FB Connect;
		
		_gaq.push([ '_trackSocial', 'Facebook', 'connected', '#anunciar' ]);
	} else if (response.status === 'not_authorized') {
		_gaq
				.push([ '_trackSocial', 'Facebook', 'not_authorized',
						'#anunciar' ]);

		$(".showOnFBnot_authorized").show();
		$(".hideOnFBnot_authorized").hide();
		
		clearFBData();
		onNonAuthorized();
		
	} else {
		clearFBData();
		onNotConnected();
		
		$(".showOnFBunknown").show();
		$(".hideOnFBunknown").hide();

	}
}


function onFBLoginStatusChange(response) {
	getFBLoginStatus(response);
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
	
	FB.getLoginStatus(function(response) {
		if (response.status === 'unknown' || response.status === 'not_authorized') {
			FB.login(
					function(response) {
						if (response.authResponse) console.log('Welcome! ');
						else console.log('User cancelled login or did not fully authorize.');
						}, {scope : 'email'
					});				

		} else {
			onConnected();			
		}
		});

}


function likeThis(url) {
	
	if (typeof(FB) != 'undefined'
	     && FB != null ) {

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

		
		
	} else {
	    // alert the user
	}


	
}


function getPostCode(postId) {
	var s1 =  "<div class='fb-post' data-href='https://www.facebook.com/crcasas/posts/";
	var e1 =  "' data-width='666'>";
    var s2 = "<div class='fb-xfbml-parse-ignore'><a href='https://www.facebook.com/crcasas/posts/";
	var e2 = "'>Post</a> by <a href='https://www.facebook.com/crcasas'>CrCasas.com</a>.</div></div>";
	return decodeURI(s1.concat(postId,e1,s2,postId,e2));
	
}



