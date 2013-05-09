package dk.itu.serverside;

import javax.servlet.http.HttpServletRequest;

import dk.itu.restconnection.Measurement;
import dk.itu.restconnection.RestClient;

public class CommandSetHeaterGain extends Command {

	protected CommandSetHeaterGain(String next) {
		super(next);
	}

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		RestClient client = new RestClient();
		String value = request.getParameter("gainValue");
		String setGainStatus = client.setGain("room-0-heater-0-gain", value);
		request.setAttribute("SetGainStatus", setGainStatus);
		return next;
	}

}
