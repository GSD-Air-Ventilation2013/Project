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
			commands.put("restcall", new CommandRestcall("Views/restcallresult.jsp"));
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