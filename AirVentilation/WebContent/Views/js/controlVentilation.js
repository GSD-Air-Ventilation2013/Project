function getVentilationGain()
{
	$.ajax({  
	    type: "GET",  
	    url: "ajaxcontroller",  
	    data: "cmd=getCurrentVentilationGain&minutes=1",  
	    contentType: "application/json",
	    success: function(result){
	    	setSlider(result);
	    },
	    error:function(result){
	        alert("failure");
	    }                  
	  }); 
}

$(function() {
		var sliderValue = getVentilationGain();
	});

function setSliderValueInterval(value)
{
	if(value.length == 1)
		{
		return 1;
		}
	else
		{
		return value.substring(1) + 0;
		}
}

function setSlider(sliderValue)
{
	$( "#slider-vertical" ).slider({
		orientation: "vertical",
		range: "min",
		min: 0,
		max: 100,
		value: sliderValue*100,
		slide: function( event, ui ) {
			$( "#amount" ).val( ui.value );
		},
		stop: function ( event, ui ){
			var val = ui.value/100;
			setGain(val.toFixed(2));
			
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
	}

function setCountDown(seconds)
{
	$("#lblDisable").text("Disabled for " + seconds);
	if(seconds > 0)
		{
			setTimeout(function() {setCountDown(seconds - 1);}, 1000);
		}
	else
		{
			$("#lblDisable").hide(); 
			return;
		}
}

function setGain(value)
{
	$('body').css('cursor', 'wait');
	$.ajax({  
	    type: "GET",  
	    url: "ajaxcontroller",  
	    data: "cmd=setVentilationGain&gainValue="+value,  
	    contentType: "application/json",
	    success: function(result){
	    	$("#slider-vertical").slider('disable');
	    	$("#lblDisable").show();
	    	setCountDown(20);
	    	setTimeout(function(){$("#slider-vertical").slider('enable');}, 20000 );

	    	$('body').css('cursor', 'auto');
	    },
	    error:function(result){
	        alert("failure");
	    }                  
	  });  }