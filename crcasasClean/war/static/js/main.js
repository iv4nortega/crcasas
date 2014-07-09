        /**      
        Global var declarations

        to do

        showaddress,  country, source, sourcekey, sourceurl, dolpriceses
        **/

        var CR = {};

        CR.debugMode = false;

        CR.userFirstName = '';

        CR.userLastName = '';

        CR.userEmail = '';

        CR.editAd = '0';

        CR.pagesId = {
            SEARCH : 'search-id',
            AD : 'anuncio-btn',
            ADVERTISE : 'advertise-id',
            HOME : 'crcasas-logo',
            WALL : 'wall-id',
            ABOUT : 'about-id',
            LANDING : 'crcasas-logo'
        };

        CR.pagesTag = {
            SEARCH : "#inicio",
            AD : "#anuncio",
            ADVERTISE : "#anunciar",
            HOME : "#inicio",
            WALL : '#muro',
            ABOUT : '#acerca',
            LANDING : '#landing'

        };

        CR.current_ad = 0;
        CR.noinput_alert_text = "<button type='button' class='close' data-dismiss='alert'>&times;</button><strong>Warning!</strong> Digite un numero de anuncio y presione ir.";


        // Google analytics code
        var _gaq = _gaq || [];
        _gaq.push(['_setAccount', 'UA-23115758-1']);
        _gaq.push(['_trackPageview']);

        (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
        })();

        function getURLParameter(name) {
            return decodeURI(
                (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,null])[1]
            );
        };


        function setNumericFields() {

            $(".numeric").numeric();
            $(".integer").numeric(false, function () { alert("Integers only"); this.value = ""; this.focus(); });
            $(".positive").numeric({ negative: false }, function () { alert("No negative values"); this.value = ""; this.focus(); }); 
            $(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Ingrese solo numeros positivos, no ingrese puntos ni comas"); this.value = ""; this.focus(); });


            $("#remove").click(
		            function (e) {
		                e.preventDefault();
		                $(".numeric,.integer,.positive,.positive-integer").removeNumeric();
		            }
	        );            
        };


        $(document).ready(function () {

            setNumericFields(); 
            loadTabs();

            init_masonry();

            init_galleria();

            get_ads('');

            //createSlider();

            $("#login-btn").show();
            $("#logout-btn").hide();
            $("#user-picture").hide();



            $("#advertise-id").show();

            $('#crcasas-logo').effect("bounce", { times: 2, direction: 'down' }, 1000);

            $('body').tooltip(
                {
                    selector: '[rel=tooltip]'
                });


            $('#search-btn').click(function (e) {

                if ($("#division2 option:selected").val() == '000') {
                    get_ads('/' + $("#type option:selected").val() + '/' +
                                    $("#subType option:selected").val() + '/' +
                                    $("#division1 option:selected").val());

                } else {
                    get_ads('/' + $("#type option:selected").val() + '/' +
                                    $("#subType option:selected").val() + '/' +
                                    $("#division1 option:selected").val() + '/' +
                                    $("#division2 option:selected").val()
                                    );

                }


            });


            $('#newadlink').click(function (e) {

                $('#confirmEdit').confirmModal({
                    heading: 'Incluir nuevo anuncio',
                    body: 'Va a incluir un nuevo anuncio, cualquier cambio no guardado en el anuncio actual se perdera. Desea continuar?',
                    callback: function () {

                        $('#formAd').clearForm();
                        $('#mainTab a[href="#newad"]').tab('show');
                        $("#btnSaveAd").hide();
                    }
                });

            });

            $('#editadlink').click(function (e) {

                $('#confirmEdit').confirmModal({
                    heading: 'Editar anuncio',
                    body: 'Va a editar un anuncio, cualquier cambio no guardado en el anuncio actual se perdera. Desea continuar?',
                    callback: function () {

                        $('#formAd').clearForm();
                        $('#mainTab a[href="#newad"]').tab('show');

                    }
                });


            });

            $('#login-btn').click(function (e) {
                FB.login(function (response) {
                    if (response.authResponse) {
                        console.log('Welcome!  Fetching your information.... ');

                        $('#mainTab a[href="#myAdsTab"]').tab('show');


                        $('#newAdTab').load('/static/tabs/new.html', function () {


                            initNewAdTabs('#tabForm', '#btnNext', '#btnPrev');

                        });


                        FB.api('/me', function (response) {
                            console.log('Good to see you, ' + response.name + '.');

                        });
                    } else {
                        console.log('User cancelled login or did not fully authorize.');


                    }
                }, { scope: 'email' });

            });

            $('#logout-clk').click(function (e) {
                FB.logout(function (response) {

                });

            });

            $('#mainTab a, button').click(function (e) {
                var History = window.History;
                e.preventDefault();
                $(this).tab('show');


                try {

                    if (this.id == CR.pagesId.SEARCH) History.pushState(null, null, CR.pagesTag.SEARCH);
                    if (this.id == CR.pagesId.AD) History.pushState(null, null, CR.pagesTag.AD);
                    if (this.id == CR.pagesId.ADVERTISE) History.pushState(null, null, CR.pagesTag.ADVERTISE);
                    if (this.id == CR.pagesId.HOME) History.pushState(null, null, CR.pagesTag.HOME);
                    if (this.id == CR.pagesId.WALL) History.pushState(null, null, CR.pagesTag.WALL);
                    if (this.id == CR.pagesId.ABOUT) History.pushState(null, null, CR.pagesTag.ABOUT);
                    if (this.id == CR.pagesId.LANDING) History.pushState(null, null, CR.pagesTag.LANDING);

                } catch (ex) {

                    console.log(ex.message);
                };




                if (this.id == CR.pagesId.SEARCH) $('#content').masonry('reload');

                // advertise-id login-btn

                if (this.id == 'anuncio-btn') {

                    if (!$("#anuncio-id").val()) {
                        $("#noinput-ad-alert").html(CR.noinput_alert_text);
                    } else {
                        if ($("#anuncio-id").val() != CR.current_ad) {
                            get_ad($("#anuncio-id").val());
                            CR.current_ad = $("#anuncio-id").val();
                        }
                    }
                }

            })

            $(function () {
                $("[rel='tooltip']").tooltip();
            });


            var QueryString = function () {
                // This function is anonymous, is executed immediately and 
                // the return value is assigned to QueryString!
                var query_string = {};
                var query = window.location.search.substring(1);
                var vars = query.split("&");
                for (var i = 0; i < vars.length; i++) {
                    var pair = vars[i].split("=");
                    // If first entry with this name
                    if (typeof query_string[pair[0]] === "undefined") {
                        query_string[pair[0]] = pair[1];
                        // If second entry with this name
                    } else if (typeof query_string[pair[0]] === "string") {
                        var arr = [query_string[pair[0]], pair[1]];
                        query_string[pair[0]] = arr;
                        // If third or later entry with this name
                    } else {
                        query_string[pair[0]].push(pair[1]);
                    }
                }

                return query_string;
            } ();



            if ((QueryString.adId != undefined)) {


            }

            var hash = window.location.hash;

            hash = cleanHash(hash);

            if (findAdTag(hash)) {
                var hashId = findAdId(hash);

                if (hashId > 0) {
                    CR.adId = hashId;
                    $("#anuncio-id").val(CR.adId);
                    $('#anuncio-btn').trigger('click');

                };

            } else {
                var adIdParam = getURLParameter('anuncio');
                if (adIdParam != 'null' && $.isNumeric(adIdParam)) {
                    CR.adId = adIdParam;
                    $("#anuncio-id").val(CR.adId);
                    $('#anuncio-btn').trigger('click');

                } else {
                    $('#mainTab a[href="' + hash + '"]').tab('show');

                }

            };


            try {

                window.addEventListener("popstate", function (e) {
                    var hash = window.location.hash;
                    $('#mainTab a[href="' + hash + '"]').tab('show');
                });

            } catch (ex) {

                console.log('window.addEventListener popstate not supported ' + ex.message);
            };






            var userFormOptions = {
                success: userResponse
            };

            $('#formUser').ajaxForm(userFormOptions);



        }); 

            function findAdTag(hashString) {
                
                var patt1 = /(#anuncio)=\d+/i;

                var match = hashString.match(patt1);

                if (match != null) {
                  var result = match[1];
                  if (result) return true;  
                };

                
                return false;
            };

            function findAdId(hashString) {
                
                var patt1 = /#anuncio=(\d+)/i;

                var match = hashString.match(patt1);

                if (match != null) {
                  var result = match[1];
                  if (result) return parseInt(result);  
                };

                return 0;
            };


          function cleanHash(hashString) {

                var result = hashString;
                var patt1 = /#!/i;
                var match = hashString.match(patt1);
                if (match != null) {
                    var result = hashString.replace('#!','#');
                };
                return result;
            };


            // Buttons prev and next for new ad Tabs

  function centerMap(lat, lng) {
      var latLng = new google.maps.LatLng(lat, lng);
      CR.marker.position = latLng;
      CR.map.setCenter(latLng);


  };


        function getPositionSuccess(position) {
            centerMap(position.coords.latitude, position.coords.longitude);
            $('#latAd').val(position.coords.latitude); 
            $('#lngAd').val(position.coords.longitude); 
            
        };


        // post-submit callback 
        function showResponse(responseText, statusText, xhr, $form)  { 
            // for normal html responses, the first argument to the success callback 
            // is the XMLHttpRequest object's responseText property 
 
            // if the ajaxForm method was passed an Options Object with the dataType 
            // property set to 'xml' then the first argument to the success callback 
            // is the XMLHttpRequest object's responseXML property 
 
            // if the ajaxForm method was passed an Options Object with the dataType 
            // property set to 'json' then the first argument to the success callback 
            // is the json data object returned by the server 
 
           addNewUl(responseText);

           addEditAdClick();

           $('#publishResponse .modal-body').html('Su anuncio fue publicado. El # de id del anuncio es ' + responseText+'.  Le recomendamos presionar ahora el boton Compartir de su anuncio para compartirlo en Facebook.');


           if (CR.editAd != responseText) {
               _gaq.push(['_trackEvent', 'ad', 'create', '#inicio']);
               CR.editAd = responseText;
           };

           $('#publishResponse').modal('show');

           $("#myAdsTabNav").attr("href", "#myAdsTab")                            
           $('#mainTab a[href="#myAdsTab"]').tab('show');

           getMyAds();

        }

       function userResponse(responseText, statusText, xhr, $form)  { 
           if ( responseText=='new') {
                _gaq.push(['_trackSocial', 'facebook', 'authorized', '#inicio']);
           }
        }


        function goToEditAd(adNumber) {

                CR.adId = adNumber;
                $('#adIdAd').val(adNumber);

                $.getJSON('restlet/json/anuncio/' + adNumber + '/form', function (data) {

                    $.each(data, function (key, value) {

                        var type = $(':input[name="' + key + '"]').attr('type');

                        if (type == 'checkbox') {
                            if (value.toUpperCase() == 'TRUE') {
                                $(':input[name="' + key + '"]').prop('checked', true);
                            } else {
                                $(':input[name="' + key + '"]').prop('checked', false);
                            };

                        } else {

                            if (type == 'radio') {
                                if (value.toUpperCase() == 'TRUE') {
                                    $("input[name=" + key + " ][value=" + value.toLowerCase() + "]").prop('checked', true);
                                } else {
                                    $("input[name=" + key + " ][value=" + value.toLowerCase() + "]").prop('checked', true);
                                };

                            } else {
                                $(':input[name="' + key + '"]').val(value);
                            }


                        };

                    });

                    $('#newAdTab').tab('show');

                    $.getJSON("restlet/file/anuncio/" + adNumber + "/meta", function (data) {
                        document.getElementById("myFilesContainer").innerHTML = tmpl("template-ads", data);

                    });


                });

        };

        function addEditAdClick() {

            $('.editAdClick').click(function (e) {

                var adNumber = $(this).attr('id');

                goToEditAd(adNumber);

                $("#formHeader").html('Editar anuncio No. ' + adNumber);

            });

        };


       function addNewAdClick() {

           $('.newAdClick').click(function (e) {

               $('#formAd .specialFields').clearFields();


               $("#formHeader").html('Incluir Nuevo Anuncio');
              

           });

        };


        function initPricing() {
            $('#btnPremium').click(function (e) {
                $('#adTypeAd').val('P');
                $('#formAdSubmit').trigger('click');
                $('#publishModal').modal('hide');
            });

            $('#btnExclusive').click(function (e) {
                $('#adTypeAd').val('E');
                $('#formAdSubmit').trigger('click');
                $('#publishModal').modal('hide');
 
            });


          $('#btnBasic').click(function (e) {
                $('#adTypeAd').val('B');
                $('#formAdSubmit').trigger('click');
                $('#publishModal').modal('hide')

            });

        };

        function initUl() {
            $("#myAds ul").empty();
            $("#myAds ul").append('<li class="newAdClick"><a href="#newad" data-toggle="tab">Nuevo Anuncio</a></li>');
            $("#myAds ul").append('<li class="divider"></li>');

        }

        function addNewUl(adNumber) {

            $("#myAds ul").append('<li class="editAdClick" id="'+adNumber+'"><a tabindex="-1" href="#newad" data-toggle="tab">' + adNumber + '</a></li>');


 
        }

            function initNewAdTabs(tabSelector, nextSelector, prevSelector) {

           $(".positive-integer").numeric({ decimal: false, negative: false }, function() { alert("Ingrese solo numeros positivos, no ingrese puntos ni comas"); this.value = ""; this.focus(); });

                 var formOptions = { 
                         success:       showResponse  // post-submit callback 
 
                        // other available options: 
                        // target:        '#output1',   // target element(s) to be updated with server response 
                        // beforeSubmit:  showRequest,  // pre-submit callback 
                        // success:       showResponse  // post-submit callback 

                        //url:       url         // override for form's 'action' attribute 
                        //type:      type        // 'get' or 'post', override for form's 'method' attribute 
                        //dataType:  null        // 'xml', 'script', or 'json' (expected server response type) 
                        //clearForm: true        // clear all form fields after successful submit 
                        //resetForm: true        // reset the form after successful submit 
 
                        // $.ajax options can be used here too, for example: 
                        //timeout:   3000 
                };

                $('#formAd').ajaxForm(formOptions);


                //setupFormValidation();


                $('#firstNameAd').val(CR.userFirstName);  
                $('#lastNameAd').val(CR.userLastName);  
                $('#emailAd').val(CR.userEmail);  

                $('#btnSelectActivate').hide();
                          
                initFileUpload('#fileupload','#sortable');


                $( ".files" ).sortable({
                        revert: true
                });


                $("#propertyTypeAd").change(function () {

                    var propertyTypeAd = $(this).val();
                    var options = '';


                    if ( propertyTypeAd =='H') {
                        options += '<option value="H">Casa</option>';
                        options += '<option value="A">Apartamento</option>';
                        options += '<option value="C">Condominio</option>';
                        options += '<option value="B">Habitaciones</option>';
                        options += '<option value="V">Vacaciones</option>';
                    };

                    if ( propertyTypeAd =='L') {
                        options += '<option value="L">Lotes</option>';
                        options += '<option value="F">Fincas</option>';
                        options += '<option value="C">Lotes Comerciales</option>';
                    };

                    if ( propertyTypeAd =='C') {
                        options += '<option value="B">Edificios</option>';
                        options += '<option value="W">Bodegas</option>';
                        options += '<option value="V">Bovedas</option>';
                        options += '<option value="O">Oficinas</option>';
                    };

                    if ( propertyTypeAd =='V') {
                        options += '<option value="E">Vacaciones</option>';
                    };

                    if ( propertyTypeAd =='P') {
                        options += '<option value="P">Proyectos</option>';
                    };

                    $("#propertySubTypeAd").html(options);   

                });

                $('#btnNewAd').click(function (e) {

                    $('#formAd .specialFields').clearFields();
                    $('#mainTab a[href="#newAdTab"]').tab('show');
                    $("#myAdsTabNav").attr("href", "#newAdTab");
                    $("#formHeader").html('Nuevo Anuncio');
                    $("#btnSaveAd").hide();

                    CR.editAd = '0';

                    $("#myFilesContainer").empty();

                });


                $('#closeNewAd').click(function (e) {

                    $('#confirmEdit').confirmModal({
                        heading: 'Cancelar los cambios',
                        body: 'Cualquier cambio no guardado en el anuncio se perdera. Desea continuar?',
                        callback: function () {

                            //$('#formAd .specialFields').clearFields();
                            //$("#formHeader").html('Incluir Nuevo Anuncio');

                            $("#myAdsTabNav").attr("href", "#myAdsTab")                            
                            $('#mainTab a[href="#myAdsTab"]').tab('show');
                            $("#myFilesContainer").empty();



                        }
                    });

                });


                $('#btnSaveAd').click(function (e) {

                    $('#confirmEdit').confirmModal({
                        heading: 'Guardar los cambios',
                        body: 'Se guardaran los cambios en el servidor. Desea continuar?',
                        callback: function () {

                            generatePhotosInputs();

                            $('#formAdSubmit').trigger('click');

                            $("#myAdsTabNav").attr("href", "#myAdsTab")
                            $('#mainTab a[href="#myAdsTab"]').tab('show');




                        }
                    });

                });


                $('#btnSelectDelete').click(function (e) {

                    $('#confirmEdit').confirmModal({
                        heading: 'Eliminar el anuncio',
                        body: 'El anuncio sera eliminado completamente y no podra ser recuperado. Desea continuar?',
                        callback: function () {
                            $.ajax({
                                url: 'restlet/json/anuncio/' + CR.adId + '/form/delete/' + CR.userId,
                                type: 'DELETE',
                                dataType: 'json',
                                data: '',
                                success: function (response) { console.log('DELETE completed' + response); alert('deleted success') }
                            });


                            $("#formHeader").html('Incluir Nuevo Anuncio');

                            alert('El anuncio fue eliminado');

                        }
                    });



                });

                $('#btnSelectView').click(function (e) {
                    $("#anuncio-id").val(CR.adId);
                    $('#anuncio-btn').trigger('click');
                });

                $('#btnSelectPause').click(function (e) {

                    $.ajax({
                        url: 'restlet/json/anuncio/' + CR.adId + '/form/pause/' + CR.userId,
                        type: 'PUT',
                        dataType: 'json',
                        data: '',
                        success: function (response) {
                            alert('success');
                            $('#btnSelectPause').hide();
                            $('#btnSelectActivate').show();
                            console.log('Pause completed' + response);
                        }
                    });

                });

                $('#btnSelectActivate').click(function (e) {

                    $.ajax({
                        url: 'restlet/json/anuncio/' + CR.adId + '/form/activate/' + CR.userId,
                        type: 'PUT',
                        dataType: 'json',
                        data: '',
                        success: function (response) {
                            alert('success');
                            $('#btnSelectPause').show();
                            $('#btnSelectActivate').hide();
                            console.log('ACTIVATE completed' + response);
                        }
                    });

                });


                function generatePhotosInputs() {
                    var inputs = '';
                    var count = 0;

                    $('.fileKey').each(function () {
                        count++;
                        inputs += '<input type="hidden" name="photosAd' + count + '" value="' + $(this).html() + '" >';

                    });

                    inputs += '<input type="hidden" name="numPhotosAd" value="' + count + '" >';
                    $('#photosAd').html(inputs);                    
                };


                $('#btnPublish').click(function (e) {

                    generatePhotosInputs();

                });

                $('#getDivision2').click(function (e) {

                    $.getJSON("restlet/json/division2/" + $('#newDivision2').val() + "/geo", function (data) {
                        centerMap(data.lat, -1* data.lng);
                        $('#latAd').val(data.lat); 
                        $('#lngAd').val(-1* data.lng); 
                    });

                });


              $('#sortPhotos').click(function (e) {
  
                    $( ".files" ).sortable({
                        revert: true
                    });

                });                         

              $('#getPosition').click(function (e) {

                      if (navigator.geolocation) {
                      navigator.geolocation.getCurrentPosition(getPositionSuccess);
                    } else {
                      alert('Su navegador no permite obtener la ubicacion');
                    }

                });

        //      $('newAdTab').tooltip(    {
        //           selector: '[rel=tooltip]'
        //       });
                
              $(tabSelector + ' a').click(function (e) {
                    e.preventDefault();
                    $(this).tab('show');

                });



              $('#deleteYouTube').click(function (e) {
                  var htm = '<iframe width="425" height="350" src= " " frameborder="0" ></iframe>';
                  $('#youTube1').html(htm);
                  $('#youTube1Url').val('');

              });
                
              $('#btnYouTube1').click(function (e) {

                  var URL = $('#youTube1Url').val();

                  var VIDEO_ID = youtube_parser(URL); 

                  var htm = '<iframe width="425" height="350" src= "http://www.youtube.com/embed/' + VIDEO_ID + '" frameborder="0" ></iframe>';

                  $('#youTube1').html(htm);



              });


              $(nextSelector).click( function(e) { 
                  nextTab(tabSelector);  
              });
  
              $(prevSelector).click( function(e) {    
                  prevTab(tabSelector);  
              });
  
              $('a[data-toggle="tab"]').on('shown', function (e) {
                isFirstOrLastTab(tabSelector, nextSelector, prevSelector);
              });

              $('#loadMapYes').click(function (e) {
                  loadScript();
                  $("#googleMap").show();

              });

              $("#googleMap").hide();

              $("#btnPublish").hide();

              $('#loadMapNo').click(function (e) {
                  $("#googleMap").hide();

              });

              getDiv2AsOptions(1, 'select#newDivision2', true);


              $("select#newDivision1").change(function () {
                      getDiv2AsOptions($(this).val(), "select#newDivision2", true);
              });

              

            };

            function nextTab(tabSelector) {
              var e = $(tabSelector + ' li.active').next().find('a[data-toggle="tab"]');  
              if(e.length > 0) e.click();  

            }

            function prevTab(tabSelector) {
              var e = $(tabSelector+' li.active').prev().find('a[data-toggle="tab"]');  
              if(e.length > 0) e.click();  

            }

            function isFirstOrLastTab(tabSelector, nextSelector, prevSelector) {
              var e = $(tabSelector+' li:last').hasClass('active'); 

              if( e ) {

                $(nextSelector).hide();
                $("#btnPublish").show();     

              }
              else {     
                 $(nextSelector).show();
                 $("#btnPublish").hide();                      

              }   
              var f = $(tabSelector + ' li:first').hasClass('active');   
              if( f )   $(prevSelector).text('Inicio');
              else $(prevSelector).text('Atras'); 
 
            }



     function init_galleria() {
            Galleria.loadTheme('/galleria/themes/classic/galleria.classic.min.js');
            //Galleria.configure({
            //    imagePan: true,
            //    imageCrop: 'landscape'
            //});

            $('#inicio').on('shown', function (e) {
                
                Galleria.run('#galleria');
            });

       }


      function get_ad(adNumber) {

          $('#galleria').show();

          $.getJSON("restlet/json/imagen/" + adNumber, function (data) {

              if ($('#galleria').data('galleria')) {

                  $('#galleria').data('galleria').load(data)


              } else {


                  $('#galleria').galleria({
                      dataSource: data
                  });


              };

              if (data[0].image == 'images/crcasas.png') $('#galleria').hide();
              


          });  
         $.getJSON("restlet/json/anuncio/" + adNumber, function (data) {

 
              $("#adTitleShow").html(
                    $("#adTitleTemplate").render(data)
        
               );

              $("#adShow").html(
                    $("#adTemplate").render(data)
        
               );

 


              FB.XFBML.parse(document.getElementById("adComments"));

              FB.XFBML.parse(document.getElementById("facebookLike"));

           });       
      };

      function getFrontAds(adId, selector ) {
         $.getJSON("restlet/json/anuncio/" + adId, function (data) {
              $(selector).html(
                    $("#frontAdsTemplate").render(data)
        
               );

           });

        }; 


      function init_ad_click(item) {
           
          var ad = $(item).attr('id');

          $("#anuncio-id").val(ad);
          
          get_ad($("#anuncio-id").val());
          CR.current_ad = $("#anuncio-id").val();

          $('#mainTab button[data-target="#anuncio"]').tab('show'); // Select tab by name

        
      }



      function getMyAds(){

          $.getJSON('restlet/json/user/' + CR.userId, function (data) {
              $("#myAdsTabContainer").html(
                $("#myAdsTemplate").render(data)


            );

              //if (data.length === 0) alert('Para  Dele click a nuevo anuncio para incluir un anuncio nuevo.');

              $('.btnMyAdsActions').click(
                  function () {

                      var adId = $(this).data('adid');
                      var action = $(this).data('action');


                      if (action == 'Edit') {
                          goToEditAd(adId);
                          $('#mainTab a[href="#newAdTab"]').tab('show');
                          $("#myAdsTabNav").attr("href", "#newAdTab");
                          $("#formHeader").html('Anuncio No. ' + adId);
                          $("#btnSaveAd").show();
                      };

                      if (action == 'Post') {
                          var imageUrl = $(this).data('imageUrl');
                          var description = $(this).data('description');
                          var name = $(this).data('caption');
                          var caption = $(this).data('address');

                          postToFeed(adId, name, caption, description, 'www.crcasas.com#!anuncio='+adId, imageUrl);
                      };

                      if (action == 'View') {
                          $("#anuncio-id").val(adId);

                          get_ad(adId);
                          CR.current_ad = adId;

                          $('#mainTab button[data-target="#anuncio"]').tab('show'); // Select tab by name


                      };


                      if (action == 'Delete') {
                          $('#confirmEdit').confirmModal({
                              heading: 'Eliminar el anuncio',
                              body: 'El anuncio sera eliminado completamente y no podra ser recuperado. Desea continuar?',
                              callback: function () {
                                  $.ajax({
                                      url: 'restlet/json/anuncio/' + adId + '/form/delete/' + CR.userId,
                                      type: 'DELETE',
                                      dataType: 'json',
                                      data: '',
                                      success: function (response) { console.log('DELETE completed' + response); alert('deleted success') }
                                  });


                                  $("#formHeader").html('Nuevo Anuncio');

                                  alert('El anuncio fue eliminado');

                                  $('#' + adId).empty();

                              }
                          });



                      };






                  }
             );

          });


     
        };


      function get_ads(path_to_data){

         $.getJSON("restlet/json/anuncios" + path_to_data, function (data) {
            $("#adList").html(
                $("#listTemplate").render(data)

        
            );

            if (data.length === 0) alert('No se encontraron propiedades. Por favor intente de nuevo variando los criterios de busqueda.');


            run_masonry();

            $(".item").click(function () {

                    init_ad_click(this);

            });

         });
        }

       function run_masonry(){
            var $container = $('#content');

           $container.imagesLoaded(  function(){
                        $container.masonry( 'reload');
            });

        }


       function init_masonry(){

                    var $container = $('#content');
                    var gutter = 8;

                    $container.masonry(  {
                            itemSelector: '.item',
                            gutterWidth: gutter,
		                    isFitWidth: true
                        });

        };

        /**
        Functions declarations
        **/

        function loadTabs() {
           $('#acerca').load('/static/tabs/aboutus.html');
           $('#anunciar').load('/static/tabs/anunciar.html', function() {
                  $('#btnAdvertiseLogin').click(function (e) {
                    $('#login-btn').trigger('click');
                    });
                });
           $('#newAdTab').load('/static/tabs/new.html');
           $('#landing').load('/static/tabs/landing.html', function() {
                  $('#btnLandingSearch').click(function (e) {
                    $('#search-id').trigger('click');
                    });
              
                 $('#btnLandingAdvertise').click(function (e) {
                    $('#advertise-id').trigger('click');
                    });
                 });     
                 
                getFrontAds(1803063,'#landingLeft');  
                getFrontAds(2045068,'#landingCenter');
                getFrontAds(2346069,'#landingRight');



        }

        /**
        Create an HTML string containing all division2 (name and value) for an specific division1 value and put it on a select tag
        @method getDiv2AsOptions
        @param (Integer) the division1 value  
        @param (String) jquery select tag to populate options  

        **/
        function getDiv2AsOptions(div1, select, all) {

             $.getJSON("restlet/json/division2/" + div1, function (j) {

                j = j.sort(function (a, b) {
                    return (a['name'] > b['name']);
                });

                var options = '';

                if (!all) options += '<option value="000"> TODOS </option>';

                for (var i = 0; i < j.length; i++) {
                    options += '<option value="' + j[i].key + '">' + j[i].name + '</option>';
                };

 
                putOptions(select, options);
            })
        }

        /**
        Put an HTML string into a select tag
        @method putOptions
        @param (String) tag to put HTML on  
        @param (String) options HTML string  

        **/

        function putOptions(select, options) {
            $(select).html(options);
        }

        /**
        Change Javascripts events

        **/

        $(function () {
            $("select#division1").change(function () {

                getDiv2AsOptions($(this).val(), "select#division2", false);



            })
        });

        $(function () {
            getDiv2AsOptions(1, 'select#division2', false);
            
        });



         $(window).scroll(function() {
           if($(window).scrollTop() + $(window).height() > $(document).height() - 100) {
               /*
               DO SOMETHING: FOR EXAMPLE LOAD MORE PROPERTIES
               */
           }
        });



function initialize() {


    CR.marker = new google.maps.Marker({
            position: new google.maps.LatLng(9.93555545807, -84.0749969482),
            title: 'Ubicacion de la propiedad',
            map: CR.map,
            draggable: true
    });
    
    var mapOptions = {
        center: CR.marker.position,
        zoom: 15,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    if (CR.map == undefined) {
      CR.map = new google.maps.Map(document.getElementById("map_canvas"),
        mapOptions);

    }
 

     google.maps.event.addListenerOnce(CR.map, "bounds_changed", function() {
        google.maps.event.trigger(CR.map, "resize");
     });



  CR.marker.setMap (CR.map);
  CR.marker.setDraggable (true);





  google.maps.event.addListener(CR.marker, 'dragend', function (a) {
      var point = CR.marker.getPosition();
      CR.map.panTo(point);

      $('#latAd').val(a.latLng.lat()); 
     $('#lngAd').val(a.latLng.lng()); 


      // a.latLng contains the co-ordinates where the marker was dropped

  });




 

 






};

function loadScript() {

    if ( ! (typeof google === 'object' && typeof google.maps === 'object')) {
        
        var script = document.createElement("script");
        script.type = "text/javascript";
        script.src = "https://maps.googleapis.com/maps/api/js?key=AIzaSyCCQSUeeH5o-VTopo7LBGLlgIdYj4KRko0&sensor=true&callback=initialize";
        document.body.appendChild(script);
    } 

};




function youtube_parser(url){
    var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
    var match = url.match(regExp);
    if (match&&match[7].length==11){
        return match[7];
    }else{
        alert("Url incorrecta");
    }
};

function setupFormValidation() {
 
 $('#formAd').validate({
        rules: {
            price: {
                number: true,
                required: true
            },
            title: {
                required: true
            },
            yearBuilt: {
               minlength: 4,
               number: true
            },
            garages: {
               number: true
            },
            floors: {
               number: true
            },
            partialBaths: {
               number: true
            },
            bedRooms: {
               number: true
            },
            constructionSize: {
               number: true
            },
            frontSize: {
               number: true
            },
            lotSize: {
               number: true
            }

        },
        messages: {
            priceAd: "Por favor ingrese el precio de la propiedad",
            titleAd: "Por favor ingrese un titulo para su anuncio",
            yearBuiltAd: {
                number: "Por favor ingrese un numero de 4 cifras",
                minlength: "Por favor ingrese un numero de 4 cifras"
            },
            garagesAd: "Por favor ingrese un numero",
            floorsAd: "Por favor ingrese un numero",
            partialBathsAd: "Por favor ingrese un numero",
            bedRoomsAd: "Por favor ingrese un numero",
            constructionSizeAd: "Por favor ingrese un numero",
            frontSizeAd: "Por favor ingrese un numero",
            lotSizeAd: "Por favor ingrese un numero"
        },
        highlight: function (label) {
            $(label).closest('.control-group').removeClass('success').addClass('error');
        },
        success: function (label) {
            label.text('OK!').addClass('valid').closest('.control-group').removeClass('error').addClass('success');
        }
      });
   

};

