$(document).ready(function() {
	getGraphData();
	//getCurrentHeaterGain();
	//getVentilationGain();
});

function getGraphData()
{
	getThermalComfort();
}

function getThermalComfort()
{
$('body').css('cursor', 'wait');
$.ajax({  
    type: "GET",  
    url: "ajaxcontroller",  
    data: "cmd=getThermalComfort&minutes=1",  
    contentType: "application/json",
    success: function(result){  
      //drawGraph(result);
      //setLastReading(result);
    	getACGain(result);
    },
    error:function(result){
        alert("failure");
    }                  
  });  
}

function getHeaterGain(thermalResult, acResult)
{
	$('body').css('cursor', 'wait');
	$.ajax({  
	    type: "GET",  
	    url: "ajaxcontroller",  
	    data: "cmd=getHeaterGain&minutes=1",  
	    contentType: "application/json",
	    success: function(result){  
	    	
	      drawGraph(thermalResult, acResult, result);
	      setSliderHeater(result[0].value);
	      //setLastReading(result);
	    },
	    error:function(result){
	        alert("failure");
	    }                  
	  });  
}

function getACGain(thermalResult)
{
	$('body').css('cursor', 'wait');
	$.ajax({  
	    type: "GET",  
	    url: "ajaxcontroller",  
	    data: "cmd=getVentilationGain&minutes=1",  
	    contentType: "application/json",
	    success: function(result){  
	    	getHeaterGain(thermalResult, result);
	      //drawGraph(thermalResult, result);
	      //setLastReading(result);
	    	setSlider(result[0].value);
	    },
	    error:function(result){
	        alert("failure");
	    }                  
	  });  
}

function setLastReading(data)
{
	var lastTemp = data[data.length-1].value;
	var lastHum = data[data.length-1].humanComfort;
	
	$("#tempLastReading").text(lastTemp.toFixed(2) + "°C");
	$("#humanComfortLastReading").text(lastHum.toFixed(3) + "%");
}

function getGainAndDate(data)
{
	var returnData = [];
	for (var i = 0; i < data.length; i++) {
		returnData.push([new Date(data[i].timestamp).getTime(), data[i].value]);
	}
	
	return returnData;
}

function getHeaterData(data)
{
	var returnData = [];
	for (var i = 0; i < data.length; i++) {
		returnData.push([new Date(data[i].timestamp).getTime(), data[i].value]);
	}
	
	return returnData;
}

function getThermalComfortData(data)
{
	var returnData = [];
	for (var i = 0; i < data.length; i++) {
		returnData.push([new Date(data[i].timestamp).getTime(), data[i].thermalComfort]);
	}
	
	return returnData;
}

function percentFormatter(v, axis) {
    return v.toFixed(axis.tickDecimals) +"%";
}

function smileyFormatter(v, axis)
{
	if(v == 0)
		{
		return "<img style='width:15px;height15px;' src='http://png.findicons.com/files/icons/360/emoticons/128/glad.png'/>";
		}
	if(v == 7.5)
		{
		return "<img style='width:20px;height20px;' src='http://png.findicons.com/files/icons/360/emoticons/128/smile_5.png'/>";
		}
	
	if(v == -7.5)
	{
	return "<img style='width:20px;height20px;' src='http://png.findicons.com/files/icons/360/emoticons/128/glad.png'/>";
	}
	return v;
}

function test(v, axis)
{
	return v+"%";
	}

function setInfotable(latestAcGain, latestHeaterGain, latestTc)
{
	var ac = latestAcGain * 100;
	var heat = latestHeaterGain * 100;
	
	$("#currentAC").text(ac.toFixed(0));
	$("#currentHeating").text(heat.toFixed(0));
	
	var action = getRecommendedAction(ac, heat, latestTc);
}

function getRecommendedAction(ac, heat, tc)
{
	$.ajax({  
	    type: "GET",  
	    url: "ajaxcontroller",  
	    data: "cmd=getRecommendedAction&ac=" + ac + "&heat="+heat + "&tc=" + tc,  
	    contentType: "application/json",
	    success: function(result){  
	    	$("#recommendedAction").text(result);
	    },
	    error:function(result){
	        alert("failure");
	    }                  
	  }); 
}

function drawGraph(thermalResult, acGainResult, heaterGain)
{
	var acGainData = getGainAndDate(acGainResult);
	var thermalComfortData = getThermalComfortData(thermalResult);
	var heaterGainData = getHeaterData(heaterGain);
	
	setInfotable(acGainResult[acGainResult.length - 1].value, heaterGain[heaterGain.length - 1].value, thermalResult[thermalResult.length - 1].thermalComfort);
	
	var plot = $.plot("#graphDiv", [
	                            	{ data: acGainData, label: "AC Gain"},
	                            	{ data: thermalComfortData, label: "Thermal Comfort", yaxis: 2},
	                            	{data: heaterGainData, label: "Heater Gain", yaxis: 2}
	                            ], {
	                            	series: {
	                            		lines: {
	                            			show: true
	                            		},
	                            		points: {
	                            			show: true
	                            		}
	                            	},
	                            	grid: {
	                            		hoverable: true,
	                            		clickable: true
	                            	},
	                            	xaxis: {
	                            	      mode: "time"
	                            	  },
	                            	  yaxes: [
	                            	          {
	                                      	min: 0,
	                                      	max: 1,
	                                      	tickformatter: test
	                                    },
	                                    {
	                                      	position: 1,
	                                    	axisLabelPadding: 1,
	                                      	min: -8,
	                                      	max: 8,
	                                      	tickFormatter: smileyFormatter
	                                    }
	                            	          ],
	                                          legend: { position: 'ne' },
	                            	  
	                            	width: 700,
	                            	height: 350
	                            });
	

	bindHover();

  	$('body').css('cursor', 'auto');
}

function showTooltip(x, y, contents) {
	$("<div id='tooltip'>" + contents + "</div>").css({
		position: "absolute",
		display: "none",
		top: y + 5,
		left: x + 5,
		border: "1px solid #fdd",
		padding: "2px",
		"background-color": "#fee",
		opacity: 0.80
	}).appendTo("body").fadeIn(200);
}
function bindHover(){
var previousPoint = null;
$("#graphDiv").bind("plothover", function (event, pos, item) {
	if (item) {
		if (previousPoint != item.dataIndex) {

			previousPoint = item.dataIndex;

			$("#tooltip").remove();
			var x = item.datapoint[0],
			y = item.datapoint[1].toFixed(2);

			showTooltip(item.pageX, item.pageY,
			    item.series.label + ": " + y + " </br>Timestamp: " + new Date(x));
		}
	} else {
		$("#tooltip").remove();
		previousPoint = null;            
	}
});
}

