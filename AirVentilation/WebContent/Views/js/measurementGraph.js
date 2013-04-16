$(document).ready(function() {
drawGraph();
bindHover();
});
function drawGraph()
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
			var x = item.datapoint[0].toFixed(2),
			y = item.datapoint[1].toFixed(2);

			showTooltip(item.pageX, item.pageY,
			    item.series.label + " of " + x + " = " + y);
		}
	} else {
		$("#tooltip").remove();
		previousPoint = null;            
	}
});
}

