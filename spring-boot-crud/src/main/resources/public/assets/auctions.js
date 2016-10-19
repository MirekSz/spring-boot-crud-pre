$(document).ready(function(){
	$("table tbody tr").click(function(){
		$.get( "/getRandomLinks", function( data ) {
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