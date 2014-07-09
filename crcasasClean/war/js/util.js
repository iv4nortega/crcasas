function getQueryString() {
	  var query_string = {};
	  var query = window.location.search.substring(1);
	  var vars = query.split("&");
	  for (var i=0;i<vars.length;i++) {
	    var pair = vars[i].split("=");
	    	// If first entry with this name
	    if (typeof query_string[pair[0]] === "undefined") {
	      query_string[pair[0]] = pair[1];
	    	// If second entry with this name
	    } else if (typeof query_string[pair[0]] === "string") {
	      var arr = [ query_string[pair[0]], pair[1] ];
	      query_string[pair[0]] = arr;
	    	// If third or later entry with this name
	    } else {
	      query_string[pair[0]].push(pair[1]);
	    }
	  } 
	    return query_string;
	
};


function startSpin(selector) {
	$(selector).addClass("icon-spinner");
	$(selector).addClass("icon-spin");			
}

function stopSpin(selector) {
	$(selector).removeClass("icon-spinner");
	$(selector).removeClass("icon-spin");	
}

function showSpinMessage(selector, message) {
	$(selector).html('<i class="icon-large loadingMessage"> </i> ' 
					  + message );
	startSpin('.loadingMessage');
};

function hideSpinMessage(selector) {
	$(selector).remove();
};


function getURLParameter(name) {
	return decodeURI((RegExp(name + '=' + '(.+?)(&|$)').exec(location.search) || [
			, null ])[1]);
};

/**
 * Create an HTML string containing all division2 (name and value) for an
 * specific division1 value and put it on a select tag
 * 
 * @method getDiv2AsOptions
 * @param (Integer)
 *            the division1 value
 * @param (String)
 *            jquery select tag to populate options
 * 
 */
function getDiv2AsOptions(div1, select, all) {

	$.getJSON("/restlet/json/division2/" + div1
			, function(j) {

		j = j.sort(function(a, b) {
			return (a['name'] > b['name']);
		});

		var options = '';

		if (!all)
			options += '<option value="000"> TODOS </option>';

		for ( var i = 0; i < j.length; i++) {
			options += '<option value="' + j[i].key + '">' + j[i].name
					+ '</option>';
		};
		
		putOptions(select, options);
		
	})
}


function getDiv2andSet(div1, select, all, stateId, countyId) {

	$.getJSON("/restlet/json/division2/" + div1
			, function(j) {

		j = j.sort(function(a, b) {
			return (a['name'] > b['name']);
		});

		var options = '';

		if (!all)
			options += '<option value="000"> TODOS </option>';

		for ( var i = 0; i < j.length; i++) {
			options += '<option value="' + j[i].key + '">' + j[i].name
					+ '</option>';
		};
		

		putOptions(select, options);
		
		$("#newDivision1").val(stateId);
		$("#newDivision2").val(countyId);

	})
}


/**
 * Put an HTML string into a select tag
 * 
 * @method putOptions
 * @param (String)
 *            tag to put HTML on
 * @param (String)
 *            options HTML string
 * 
 */

function putOptions(select, options) {
	$(select).html(options);
}

function setFormTitle(title, subTitle) {
	$('#formAdTitle').html(title);
	$('#formAdSubTitle').html(subTitle);
};



function bookmarkThis(selector) {
    $(selector).click(function(e) {
        e.preventDefault(); // this will prevent the anchor tag from going the user off to the link
        var bookmarkUrl = window.location.href;
        var bookmarkTitle = this.title;

        if (window.sidebar) { // For Mozilla Firefox Bookmark
            window.sidebar.addPanel(bookmarkTitle, bookmarkUrl,"");
        } else if( window.external || document.all) { // For IE Favorite
            window.external.AddFavorite( bookmarkUrl, bookmarkTitle);
        } else if(window.opera) { // For Opera Browsers
            $(selector).attr("href",bookmarkUrl);
            $(selector).attr("title",bookmarkTitle);
            $(selector).attr("rel","sidebar");
        } else { // for other browsers which does not support
             alert('Su navegador no permite esta funcionalidad');
             return false;
        }
    });
};


/**
 * jQuery.browser.mobile (http://detectmobilebrowser.com/)
 *
 * jQuery.browser.mobile will be true if the browser is a mobile device
 *
 **/
(function(a){(jQuery.browser=jQuery.browser||{}).mobile=/(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|iris|kindle|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(a)||/1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(a.substr(0,4))})(navigator.userAgent||navigator.vendor||window.opera);


function googleInitialize(lat, lng) {
	
	var mapOptions = {
		    center:  new google.maps.LatLng(lat, lng),
		    zoom: 15,
		    mapTypeId: google.maps.MapTypeId.ROADMAP
		};
	
    var latLng = new google.maps.LatLng(lat, lng);
	
	if (CR.map == undefined) {
		
		
			if ($('#map-canvas').length) { 
				  CR.map = new google.maps.Map(document.getElementById("map-canvas"),
						    mapOptions);				
			} else {
				console.log ( 'map-canvas doesnt exits' );
			}


		} else {

		    CR.map.setCenter(latLng);
			
		}
	
	var marker = new google.maps.Marker({
	      position: latLng,
	      map: CR.map,
	      title: ''
	  });
		  
	}

function youtube_parser(url) {
	var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
	var match = url.match(regExp);
	if (match && match[7].length == 11) {
		return match[7];
	} else {
		alert("Url incorrecta");
	}
};


function isiOS() {
	
	var iOS = ( navigator.userAgent.match(/(iPad|iPhone|iPod)/g) ? true : false );
	return iOS
}

function isChrome() {
	var is_chrome = navigator.userAgent.toLowerCase().indexOf('chrome') > -1;
	return is_chrome;
}

