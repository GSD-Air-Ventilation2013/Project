package dk.itu.serverside;

import java.util.HashMap;
import java.util.Map;

// Facade pattern
public class ApplicationController {

	// one instance of ApplicationController
	private static ApplicationController instance = null;

	// list of commands
	private Map<String, Command> commands = new HashMap<String, Command>();

	// private constructor
	private ApplicationController() {
		try {
			commands.put("getThermalComfort", new CommandRestcall("Views/restcallresult.jsp"));
			commands.put("setVentilationGain", new CommandSetVentilationGain("Views/restcallresult.jsp"));
			commands.put("getCurrentVentilationGain", new CommandGetVentilationGain("Views/restcallresult.jsp"));
			commands.put("getVentilationGain", new CommandGetVentilationGain("Views/restcallresult.jsp"));
			commands.put("getHeaterGain", new CommandGetHeaterGain("Views/restcallresult.jsp"));
			commands.put("setHeaterGain", new CommandSetHeaterGain("Views/restcallresult.jsp"));
			commands.put("getCurrentHeaterGain", new CommandGetHeaterGain("Views/restcallresult.jsp"));
			commands.put("getRecommendedAction", new CommandGetRecommendedAction("Views/restcallresult.jsp"));
		} catch (Exception e) {
			System.out.println("Exception in Application Controller- Panic Situation");
		}
	}

	public Command getCommand(String commandName) {
		return commands.get(commandName);
	}

	// singleton pattern - only one instance of Application controller allowed
	public static ApplicationController getInstance() {
		if (instance == null)
			instance = new ApplicationController();
		return instance;
	}
}