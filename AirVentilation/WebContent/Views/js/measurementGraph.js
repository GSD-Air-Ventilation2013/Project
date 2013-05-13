$(document).ready(function() {
	getGraphData();
	//getCurrentHeaterGain();
	//getVentilationGain();
});

function getGraphData()
{
	getThermalComfort();
}

var temperatureData;

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
    	setTemperatureData(result);
    },
    error:function(result){
        alert("failure");
    }                  
  });  
}

function setTemperatureData(data)
{
	var result = [];
	for (var i = 0; i < data.length; i++) {
		result.push(data[i].value);
	}
	
	temperatureData = result;
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
	      setSliderHeater(result[result.length - 1].value);
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
	    	setSlider(result[result.length - 1].value);
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

function setInfotable(latestAcGain, latestHeaterGain, latestTc, ppd)
{
	var ac = latestAcGain * 100;
	var heat = latestHeaterGain * 100;
	
	$("#currentAC").text(ac.toFixed(0));
	$("#currentHeating").text(heat.toFixed(0));
	
	var action = getRecommendedAction(ac, heat, latestTc, ppd);
}

function getRecommendedAction(ac, heat, tc, ppd)
{
	$.ajax({  
	    type: "GET",  
	    url: "ajaxcontroller",  
	    data: "cmd=getRecommendedAction&ac=" + ac + "&heat="+heat + "&tc=" + tc + "&ppd=" + ppd,  
	    contentType: "application/json",
	    success: function(result){  
	    	$("#recommendedAction").text(result);
	    },
	    error:function(result){
	        alert("failure");
	    }                  
	  }); 
}

var acGainData;
var heaterGainData;
var thermalComfortData;

function drawGraph(thermalResult, acGainResult, heaterGain)
{
	acGainData = getGainAndDate(acGainResult);
	thermalComfortData = getThermalComfortData(thermalResult);
	heaterGainData = getHeaterData(heaterGain);
	
	setInfotable(acGainResult[acGainResult.length - 1].value, heaterGain[heaterGain.length - 1].value, thermalResult[thermalResult.length - 1].thermalComfort, thermalResult[thermalResult.length - 1].predictedPercentageDissatisfied);
	
	var plot = $.plot("#graphDiv", [
	                            	{data: thermalComfortData, label: "Thermal Comfort", yaxis: 2}
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
	                                      	max: 1
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

function showTooltip(x, y, index, contents) {
	
	var acGain = acGainData[index][1] * 100;
	var heaterGain = heaterGainData[index][1] * 100;
	var tcLevel = thermalComfortData[index][1];
	var temperature = temperatureData[index].toFixed(1);
	
	var newContent = "<table><tr><td>Ac: " + acGain + "%</td></tr>" + "<tr><td>Heater: " + heaterGain+"%</td></tr>" +
			"<tr><td>Thermal Comfort: " + tcLevel + "</td></tr><tr><td>" + temperature + "°C</td></tr></table>";
	
	$("<div id='tooltip'>" + newContent + "</div>").css({
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

			showTooltip(item.pageX, item.pageY, previousPoint,
			    item.series.label + ": " + y + " </br>Timestamp: " + new Date(x));
		}
	} else {
		$("#tooltip").remove();
		previousPoint = null;            
	}
});
}

