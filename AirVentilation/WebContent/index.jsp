<jsp:include page="Views/header.jsp" flush="true" />
<%@ page import="java.lang.*"%>
<script type="text/javascript" src="<%= request.getContextPath() %>/Views/js/measurementGraph.js" ></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/Views/js/controlVentilation.js" ></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/Views/js/controlHeating.js" ></script>
<section id="content" class="body">
	<h2>Building 3, Room 0</h2>
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
				<td>Current AC gain</td><td><div id="currentAC"></div></td>
			</tr>
			<tr>
				<td>Current Heater Gain</td><td><div id="currentHeating"></div></td>
			</tr>
			<tr>
				<td>Recommended action:</td><td><div id="recommendedAction"></div></td>
			</tr>
		</tbody>
		</table>
	</div>
	<p>
	<table>
	<tr>
	<td>
	<label style="margin-bottom: 5px;">AC:</label>
	</td>
	</tr>
	<tr>
	<td>
	<label id="lblDisable" style="font-size: xx-small; height: 25px;"></label>
	</td>
	</tr>
	<tr>
	<td>
	<label id="amount" style="border: 0; font-weight: bold;"></label>
	<div id="slider-vertical"></div>
	</td>
	</tr>
	</table>
	
	<table style="float: left;">
	<tr>
	<td>
	<label>Heater:</label>
	</td>
	</tr>
	<tr>
	<td>
	<label id="lblDisableHeater" style="font-size: xx-small; height: 25px;"></label>
	</td>
	</tr>
	<tr>
	<td>
	<label id="amountHeater" style="border: 0; font-weight: bold;"></label>
	<div id="slider-vertical-heater"></div>
	</td>
	</tr>
	</table>
	
</div>
</section>

<jsp:include page="Views/footer.jsp" flush="true" />