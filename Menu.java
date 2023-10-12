package labs.lab9;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Queue;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Menu extends JFrame {
	 TreeMap<String, User> recipients;
	 String currentUser;

	public Menu(JMenuBar menuBar, TreeMap<String, User> recipients, String currentUser) { // builds menu bar
		menuBar.add(createFileMenu());
		menuBar.add(createUsersMenu());
		this.recipients = recipients;
		this.currentUser = currentUser;
	}
	
	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}
	
	public JMenu createFileMenu() { // makes file menu and adds exit option
		JMenu menu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		ActionListener listener = new ExitListener();
		exitItem.addActionListener(listener);
		menu.add(exitItem);
		return menu;
	}

	
	public JMenu createUsersMenu() {
		JMenu menu = new JMenu("Users");
		menu.add(createSwitchUser());
		return menu;
	}
	
	public JMenuItem createSwitchUser() { // deals with switching users
		class SwitchUserListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				try {
					LoginSystem login = new LoginSystem();
					String user = login.getUserName();
					
					dispose();
					EmailSystem email = new EmailSystem(user);
					email.setTitle("Email System - Meggie Nguyen - 48261700");
					email.setLocationRelativeTo(null);
					email.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					email.setVisible(true);
					} catch (NullPointerException e) { }
			}
		}
		JMenuItem item = new JMenuItem("Switch User");
		ActionListener listener = new SwitchUserListener();
		item.addActionListener(listener);
		return item;
	}
	

}