package dk.itu.serverside;

import javax.servlet.http.HttpServletRequest;

import dk.itu.restconnection.Measurement;
import dk.itu.restconnection.RestClient;

public class CommandGetHeaterGain extends Command {

	protected CommandGetHeaterGain(String next) {
		super(next);
	}

	@Override
	public String execute(HttpServletRequest request) throws Exception {
		RestClient client = new RestClient();
		String value = request.getParameter("minutes");
		Measurement[] gain = client.getHeaterGain("room-0-heater-0-gain", value);
		request.setAttribute("HeaterGain", gain);
		return next;
	}

}
