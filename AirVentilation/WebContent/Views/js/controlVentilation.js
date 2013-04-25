$(function() {
		$( "#slider-vertical" ).slider({
			orientation: "vertical",
			range: "min",
			min: 1,
			max: 100,
			value: 60,
			slide: function( event, ui ) {
				$( "#amount" ).val( ui.value );
			},
			stop: function ( event, ui ){
				var val = ui.value/100;
				setGain(val.toFixed(1));
			}
		});
		$( "#amount" ).val( $( "#slider-vertical" ).slider( "value" ) );
	});

function setGain(value)
{
	$.ajax({  
	    type: "GET",  
	    url: "ajaxcontroller",  
	    data: "cmd=setVentilationGain&gainValue="+value,  
	    contentType: "application/json",
	    success: function(result){
	    	alert("Works!");
	    },
	    error:function(result){
	        alert("failure");
	    }                  
	  });  }