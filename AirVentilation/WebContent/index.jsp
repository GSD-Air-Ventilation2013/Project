<jsp:include page="Views/header.jsp" flush="true" />
<%@ page import="java.lang.*"%>
<script type="text/javascript" src="<%= request.getContextPath() %>/Views/js/measurementGraph.js" ></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/Views/js/controlVentilation.js" ></script>
<section id="content" class="body">
	<h2>Visual representation</h2>
	<div id="graphDiv" ></div>
</section>
<section id="content" class="body">
<div id="divContent">
<div>
		<table class="readingTable">
		<thead>
		<tr>
		<th></th><th>Latest Reading</th>
		</tr>
		</thead>
		<tbody>
			<tr>
				<td>Temperature</td><td><div id="tempLastReading"></div></td>
			</tr>
			<tr>
				<td>Relative Humidity</td><td><div id="humidLastReading"></div></td>
			</tr>
		</tbody>
		</table>
	</div>
	<p>
	<table>
	<tr>
	<td>
	<label>Gain:</label>
	</td>
	</tr>
	<tr>
	<td>
	<label id="lblDisable" style="font-size: xx-small;"></label>
	</td>
	</tr>
	</table>
	<input type="text" id="amount" style="border: 0; font-weight: bold;">
	</p>
	<div id="slider-vertical"></div>
	
<div>
<form action="">
<input disabled="disabled" id="radioOn" type="radio" name="onOff" value="On" checked="checked">On
<input disabled="disabled" id="radioOff" type="radio" name="onOff" value="Off">Off
</form>
</div>
</div>
</section>

<jsp:include page="Views/footer.jsp" flush="true" />