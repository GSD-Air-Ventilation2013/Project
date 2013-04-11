<%@ page import="java.util.*" 
	import="dk.itu.restconnection.Measurement"
%>

<html>
<body>
<h1 align="center">Test.jsp</h1>
<p>

<%
	Measurement measurement = (Measurement) request.getAttribute("Measurement");
	out.print(measurement.getUuid());
  //List styles = (List) request.getAttribute("styles");
  //Iterator it = styles.iterator();
  //while(it.hasNext()) {
  //  out.print("<br>try: " + it.next());
  //}
  
%>

</body>
</html>