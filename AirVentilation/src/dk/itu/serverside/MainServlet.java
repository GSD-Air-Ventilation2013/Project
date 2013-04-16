package dk.itu.serverside;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mainservlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MainServlet() {
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String commandname = request.getParameter("cmd");
			Command command = ApplicationController.getInstance().getCommand(commandname);
			RequestDispatcher dispatcher = request.getRequestDispatcher(command.execute(request));
			dispatcher.forward(request, response);
		} catch (Exception ex) {
			// some logic
		}
	}
}
