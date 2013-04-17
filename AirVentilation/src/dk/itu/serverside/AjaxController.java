package dk.itu.serverside;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import dk.itu.restconnection.Measurement;

@WebServlet("/ajaxcontroller")
public class AjaxController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public AjaxController(){}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException
	{
		try {
			String commandname = request.getParameter("cmd");

			Command command = ApplicationController.getInstance().getCommand(commandname);
			command.execute(request);
			Measurement[] results = (Measurement[]) request.getAttribute("Measurement");
			
			Gson gson = new Gson();
			response.setContentType("application/json");
			PrintWriter out = response.getWriter();
			out.print(gson.toJson(results));
		} catch (Exception ex) {
		}
	}
	
}
