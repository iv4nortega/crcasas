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

	$.getJSON("http://www.crcasas.com/restlet/json/division2/" + div1
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
		}
		;

		putOptions(select, options);
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
