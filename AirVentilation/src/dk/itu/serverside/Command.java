package dk.itu.serverside;

import javax.servlet.http.HttpServletRequest;

public abstract class Command {

	protected String next;

	protected Command(String next) {
		this.next = next;
	}

	public abstract String execute(HttpServletRequest request) throws Exception;
}
