$(document).ready(function() {
getGraphData();
});

function getGraphData()
{
$('body').css('cursor', 'wait');
$.ajax({  
    type: "GET",  
    url: "ajaxcontroller",  
    data: "cmd=restcall",  
    contentType: "application/json",
    success: function(result){  
      drawGraph(result);
      setLastReading(result);
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
	var lastHum = data[data.length-1].relativeHumidity;
	
	$("#tempLastReading").text(lastTemp.toFixed(2) + "°C");
	$("#humidLastReading").text(lastHum.toFixed(2) + "%");
}

function getTempAndDate(data)
{
	var returnData = [];
	for (var i = 0; i < data.length; i++) {
		returnData.push([new Date(data[i].timestamp).getTime(), data[i].value]);
	}
	
	return returnData;
}

function getHumidityData(data)
{
	var returnData = [];
	for (var i = 0; i < data.length; i++) {
		returnData.push([new Date(data[i].timestamp).getTime(), data[i].relativeHumidity]);
	}
	
	return returnData;
}

function percentFormatter(v, axis) {
    return v.toFixed(axis.tickDecimals) +"%";
}

function drawGraph(data)
{
	var temperatureData = getTempAndDate(data);
	var humidityData = getHumidityData(data);
	
	var plot = $.plot("#graphDiv", [
	                            	{ data: temperatureData, label: "Temperature"},
	                            	{ data: humidityData, label: "Relative Humidity (%)", yaxis: 2}
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
	                            	  yaxes: [{ min: 0 },
	                            	          {
	                            		  alignTicksWithAxis: 1,
	                                      	position: 1,
	                                      	tickFormatter: percentFormatter
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

