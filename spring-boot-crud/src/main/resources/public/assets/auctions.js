$(document).ready(function(){
	$("table tbody tr").click(function(){
		$.get( "/getRandomLinks", function( data ) {
			  $( "#details" ).html( data );
			});
	})
})