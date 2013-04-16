package controllers;
import java.io.IOException;

import dk.itu.restconnection.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dk.itu.restconnection.RestClient;

public class TestController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	 public void doPost( HttpServletRequest request, 
             HttpServletResponse response) 
             throws IOException, ServletException {
		 RestClient client = new RestClient();
		 Measurement[] temperatures = client.getMeasurements("floor-0-room-1.temp", 2);
		 
		 request.setAttribute("Measurement", temperatures);
		 RequestDispatcher view = request.getRequestDispatcher("Views/TestView.jsp");
		 view.forward(request, response); 
}
}
