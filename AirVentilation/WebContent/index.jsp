<jsp:include page="Views/header.jsp" flush="true" />
<%@ page import="java.lang.*"%>

<section id="content" class="body">
	<h2>Visual representation</h2>
	<p>Here goes simon widget</p>
</section>

<section id="content" class="body">
	<h2>Controls</h2>
	<form action="mainservlet" method="POST">
		<input type="hidden" name="cmd" value="restcall" /> <input
			value="GetRestData" name="apply" type="submit" /> <input
			value="TurnOn" name="apply" type="submit" /> <input value="TurnOff"
			name="apply" type="submit" />
	</form>
</section>

<jsp:include page="Views/footer.jsp" flush="true" />