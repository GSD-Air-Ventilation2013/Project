package dk.itu.serverside;

import javax.servlet.http.HttpServletRequest;

import dk.itu.restconnection.Measurement;
import dk.itu.restconnection.RestClient;

public class CommandGetVentilationGain extends Command {

	protected CommandGetVentilationGain(String next) {
		super(next);
	}

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		RestClient client = new RestClient();
		Measurement[] gain = client.getVentilationGain("room-0-ac-0-gain");
		request.setAttribute("VentilationGain", gain);
		return next;
	}

}
