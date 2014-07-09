             // Additional JS functions here
            // var fbid = '174633095925807'; // production
            // var fbid = '104922512993964';  // development 6.crcasas1.appspot.com

            var fbStatus = "";

            var fbid = '174633095925807';

            //var fb_login_html = "<fb:login-button data-perms='email' autologoutlink='true' registration-url='http://www.6.crcasas1.appspot.com/static/registration.html' size = 'large'>Ingrese usando Facebook</fb:login-button>";

            var fb_login_html = "<button id= 'login-btn' class='btn btn-primary' type='button' rel='tooltip' data-placement='bottom' data-original-title='Para publicar anuncios, ingrese a crCasas usando Facebook Connect'><i class='icon-user icon-white'></i>&nbsp;&nbsp;Conectarse</button>";

            var newadbtn = "<button id ='new-ad-btn' class='btn btn-primary btn-large'>Incluir un nuevo anuncio &raquo;</button>";

            window.fbAsyncInit = function () {
                FB.init({
                    appId: fbid, // App ID
                    channelUrl: 'http://www.crcasas.com/channel.html', // Channel File
                    status: true, // check login status
                    cookie: true, // enable cookies to allow the server to access the session
                    xfbml: true  // parse XFBML
                });

                // Additional init code here

                FB.Event.subscribe('auth.authResponseChange', function (response) {

                    changeStatus(response);

                });

                FB.Event.subscribe('edge.create', function(targetUrl) {
                  _gaq.push(['_trackSocial', 'facebook', 'like', targetUrl]);
                });

                FB.Event.subscribe('edge.remove', function(targetUrl) {
                  _gaq.push(['_trackSocial', 'facebook', 'unlike', targetUrl]);
                });

                FB.Event.subscribe('message.send', function(targetUrl) {
                  _gaq.push(['_trackSocial', 'facebook', 'send', targetUrl]);
                });





            };

            // Load the SDK Asynchronously
            (function (d) {
                var js, id = 'facebook-jssdk', ref = d.getElementsByTagName('script')[0];
                if (d.getElementById(id)) { return; }
                js = d.createElement('script'); js.id = id; js.async = true;
                js.src = "//connect.facebook.net/es_LA/all.js";
                ref.parentNode.insertBefore(js, ref);
            } (document));

            function changeStatusToConnected(name, imageUrl) {

                $("#login-btn").hide();
                $("#logout-btn").show();
                $("#user-picture").show();
                $("#myAdsTabNav").show();


                $("#advertise-id").hide();

                $("#user-name").empty().append(name + "&nbsp;<b class='caret'></b>");

                $("#user-picture").attr("src", imageUrl);

                initUl();


                $.getJSON("http://www.crcasas.com/restlet/json/user/" + CR.userId +'?callback=?', function (data) {

                    $.each(data, function (i, item) {
                        addNewUl(item.id);
                    });

                    addEditAdClick();
                    addNewAdClick();

                });

                getMyAds();


            };

            function changeStatusToNotConnected(l) {
                $("#login-btn").show();
                $("#logout-btn").hide();
                $("#user-picture").hide();


                $("#advertise-id").show();
                $("#myAdsTabNav").hide();

                CR.userFirstName = '';
                CR.userLastName = '';
                CR.userEmail = '';
                CR.userId = '';

                $('#anunciar').load('/tabs/anunciar.html', function() {
                      $('#btnAdvertiseLogin').click(function (e) {
                        $('#login-btn').trigger('click');
                        });
                });

                initUl();
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

                    _gaq.push(['_trackSocial', 'facebook', 'login', '#inicio']);

                    FB.api('/me', function (response) {
                        var fbname = response.name;

                        CR.userFirstName = response.first_name;

                        CR.userLastName = response.last_name;

                        CR.userEmail = response.email;

                        CR.userId = uid;

                        $('#formUser').clearForm();
                        $('#idF01').val(CR.userId);
                        $('#emailF01').val(CR.userEmail);
                        $('#firstNameF01').val(CR.userFirstName);
                        $('#lastNameF01').val(CR.userLastName);

                        $('#formUserSubmit').trigger('click');

                        var img_link = "http://graph.facebook.com/" + response.id + "/picture";

                        changeStatusToConnected(fbname, img_link);
                        
                        loadNewAdTab();

                    });

                } else if (response.status === 'not_authorized') {
                    // the user is logged in to Facebook, 
                    // but has not authenticated your app

                     _gaq.push(['_trackSocial', 'facebook', 'not_authorized', '#inicio']);

                    changeStatusToNotConnected();


                } else {
                    // the user isn't logged in to Facebook.

                    changeStatusToNotConnected();
                }
            }


            function postToFeed(adId, name, caption, description, url, picture) {

                // calling the API ...
                var obj = {
                  method: 'feed',
                  link: url,
                  picture: picture,
                  name: name,
                  caption: caption,
                  description: description
                };

                function callback(response) {
                  //document.getElementById('msg').innerHTML = "Post ID: " + response['post_id'];
                   _gaq.push(['_trackSocial', 'facebook', 'post', adId]);
                }

                FB.ui(obj, callback);
              }