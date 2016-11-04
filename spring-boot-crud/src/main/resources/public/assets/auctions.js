var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});

$(document).ready(function(){
	$("table tbody tr").click(function(){
		$.post( "/getRandomLinks", function( data ) {
			$( "#details" ).hide()
			  $( "#details" ).html( data );
			  $( "#details" ).animate({
				    height: "toggle"
				  }, 500, function() {
				    // Animation complete.
				  });
			});
	})
})
//thymelaft javascript