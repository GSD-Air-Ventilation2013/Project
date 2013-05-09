package dk.itu.serverside;

import javax.servlet.http.HttpServletRequest;

public class CommandGetRecommendedAction extends Command {

	protected CommandGetRecommendedAction(String next) {
		super(next);
	}
	@Override
	public String execute(HttpServletRequest request) throws Exception {

		String ac = request.getParameter("ac");
		String heat = request.getParameter("heat");
		String action = "TEST";
		
		PolicyHandler policyHandler = new PolicyHandler();
		
		request.setAttribute("RecommendedAction", action);
		return next;
	}

}
