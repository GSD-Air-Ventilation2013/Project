$(document).ready(function() {
	getGraphData();
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
  	$('body').css('cursor', 'auto');
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
	      drawGraph(thermalResult, result);
	      //setLastReading(result);
	  	$('body').css('cursor', 'auto');
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

var angryUp = 0;
var angryDown = 0;
var middle = 0;

function smileyFormatter(v, axis)
{
	if(v <= 1 && v >= -1 && middle == 0)
		{
		middle++;
		return "<img style='width:15px;height15px;' src='http://png.findicons.com/files/icons/360/emoticons/128/glad.png'/>";
		}
	if(v > 8 && angryUp == 0)
		{
		angryUp++;
		return "<img style='width:15px;height15px;' src='http://png.findicons.com/files/icons/360/emoticons/128/smile_5.png'/>";
		}
	
	if(v <= -6 && angryDown == 0)
	{
	angryDown++;
	return "<img style='width:15px;height15px;' src='http://png.findicons.com/files/icons/360/emoticons/128/smile_5.png'/>";
	}
	return "";
}

function drawGraph(thermalResult, acGainResult)
{
	var acGainData = getGainAndDate(acGainResult);
	var thermalComfortData = getThermalComfortData(thermalResult);
	
	var plot = $.plot("#graphDiv", [
	                            	{ data: acGainData, label: "AC Gain"},
	                            	{ data: thermalComfortData, label: "Thermal Comfort", yaxis: 2}
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
	                                      	min: -12,
	                                      	max: 10,
	                                      	tickFormatter: smileyFormatter
	                                    }
	                            	          ],
	                                          legend: { position: 'ne' },
	                            	  
	                            	width: 700,
	                            	height: 350
	                            });
	

	bindHover();
}

function drawGraphOLD()
{
	var sin = [],
	cos = [];

for (var i = 0; i < 14; i += 0.5) {
	sin.push([i, Math.sin(i)]);
	cos.push([i, Math.cos(i)]);
}

var plot = $.plot("#graphDiv", [
	{ data: sin, label: "sin(x)"},
	{ data: cos, label: "cos(x)"}
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
	yaxis: {
		min: -1.2,
		max: 1.2
	},
	width: 700,
	height: 350
});
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

