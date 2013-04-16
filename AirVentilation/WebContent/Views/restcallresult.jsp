<jsp:include page="header.jsp" flush="true" />
<%@ page import="java.util.*" import="dk.itu.restconnection.Measurement"%>

<section id="content" class="body">
	<h2>RestCall information</h2>
	<%
		Measurement[] measurements = (Measurement[]) request.getAttribute("Measurement");
		out.print(measurements[0].getUuid());
	%>
</section>
<jsp:include page="footer.jsp" flush="true" />