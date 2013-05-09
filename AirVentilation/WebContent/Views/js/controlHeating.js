function getCurrentHeaterGain()
{
	$.ajax({  
	    type: "GET",  
	    url: "ajaxcontroller",  
	    data: "cmd=getCurrentHeaterGain&minutes=1",  
	    contentType: "application/json",
	    success: function(result){
	    	setSliderHeater(result);
	    },
	    error:function(result){
	        alert("failure");
	    }                  
	  }); 
}

function setSliderValueIntervalHeater(value)
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

function setSliderHeater(sliderValue)
{
	$( "#slider-vertical-heater" ).slider({
		orientation: "vertical",
		range: "min",
		min: 0,
		max: 100,
		value: sliderValue*100,
		slide: function( event, ui ) {
			$( "#amountHeater" ).text( ui.value );
		},
		stop: function ( event, ui ){
			var val = ui.value/100;
			setGainHeater(val.toFixed(2));
		}
	});
	$( "#amountHeater" ).text( $( "#slider-vertical-heater" ).slider( "value" ) );
	}

function setCountDownHeater(seconds)
{
	$("#lblDisableHeater").text("Disabled for " + seconds);
	if(seconds > 0)
		{
			setTimeout(function() {setCountDownHeater(seconds - 1);}, 1000);
		}
	else
		{
			$("#lblDisableHeater").hide(); 
			return;
		}
}

function setGainHeater(value)
{
	$('body').css('cursor', 'wait');
	$.ajax({  
	    type: "GET",  
	    url: "ajaxcontroller",  
	    data: "cmd=setHeaterGain&gainValue="+value,  
	    contentType: "application/json",
	    success: function(result){
	    	$("#slider-vertical-heater").slider('disable');
	    	$("#lblDisableHeater").show();
	    	setCountDownHeater(20);
	    	setTimeout(function(){$("#slider-vertical-heater").slider('enable');}, 20000 );

	    	$('body').css('cursor', 'auto');
	    },
	    error:function(result){
	        alert("failure");
	    }                  
	  });  }