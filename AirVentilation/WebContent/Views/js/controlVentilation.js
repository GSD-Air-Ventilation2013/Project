$(function() {
		$( "#slider-vertical" ).slider({
			orientation: "vertical",
			range: "min",
			min: 0,
			max: 100,
			value: 60,
			slide: function( event, ui ) {
				$( "#amount" ).val( ui.value );
			},
			stop: function ( event, ui ){
				var val = ui.value/100;
				setGain(val.toFixed(1));
				
				if(val == 0)
					{
						$("#radioOn").prop('checked', false);
						$("#radioOff").prop('checked', true);
					}
				else
					{
						$("#radioOn").prop('checked', true);
						$("#radioOff").prop('checked', false);
					}
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
	    },
	    error:function(result){
	        alert("failure");
	    }                  
	  });  }