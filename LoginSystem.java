package labs.lab9;

import javax.swing.JOptionPane;

public class LoginSystem extends JOptionPane {
	private User user;

	public LoginSystem() { // Prompts for username
		try {
			boolean valid = false;
			String input;
			while (!valid) { // keep prompting for input until it is not whitespace
				input = showInputDialog("Enter Username: ");
				if (!input.isBlank()) {
					valid = true;
					setUser(input);
				}
			}
		} catch (NullPointerException e) { } // removes error message from console
	}
	
	private void setUser(String user) {
		this.user = new User(user.trim());
	}
	
	public User getUser() {
		return user;
	}
	
	public String getUserName() {
		return user.getName();
	}
}