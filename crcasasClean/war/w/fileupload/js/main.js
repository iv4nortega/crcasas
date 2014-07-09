/*
 * jQuery File Upload Plugin JS Example 7.0
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/*jslint nomen: true, unparam: true, regexp: true */
/*global $, window, document */



function initFileUpload(uploadSelector, sortSelector) {

	
	$(uploadSelector).fileupload({submit: function (e, data) {


		var $this = $(this);
    	debugger;
    	
    	
    	
        $.getJSON('http://www.crcasas.com/restlet/file/url?callback=?', function (result) {
        	
        	data.url = result.url;
        	
            $this.fileupload('send', data);
            
        });
        
        
        return false;
    }
	});
	
    
}



	  /* #fileupload  #sortable*/

