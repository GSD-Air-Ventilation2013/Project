package dk.itu.serverside;

import javax.servlet.http.*;

import dk.itu.restconnection.Measurement;
import dk.itu.restconnection.RestClient;

public class CommandRestcall extends Command {

	public CommandRestcall(String next) {
		super(next);
	}

	public String execute(HttpServletRequest request) {
		RestClient client = new RestClient();
		String value = request.getParameter("minutes");
		Measurement[] temperatures = client.getMeasurements("floor-0-room-1.temp", value);
		request.setAttribute("Measurement", temperatures);
		return next;
	}
}
