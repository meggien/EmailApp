package labs.lab9;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class EmailSystem extends JFrame {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 1200;
	private JRadioButton high, medium, low;
	private int priority;
	private ActionListener listener;
	private String user;
	private static TreeMap<String, User> recipients;
	private JPanel inboxPanel, newMessagePanel, recipientPanel;
	private JTextArea messageText, text = new JTextArea();
	private JTextField subjectText;
	private JComboBox<String> userCombo;
	private JList receivedMail;
	
	
	public EmailSystem(String user) {
		JLabel label = new JLabel("Current User: " + user);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setPreferredSize(new Dimension(40, 40));
		add(label, BorderLayout.NORTH);
		text.setEditable(false);
		
		if (recipients == null) { // create static tree map to contain recipients
			recipients = new TreeMap<>();
			recipients.put("Robert Navarro", new User("Robert Navarro"));
		}
		
		this.user = user;
		if (!recipients.containsKey(user))
			recipients.put(user, new User(user)); // adds user to map if they are new
		
		JMenuBar menuBar = new JMenuBar();
		Menu menu = new Menu(menuBar, recipients, user);
		setJMenuBar(menuBar);
		
		listener = new PriorityListener();
		
		mainLayout();

		setSize(WIDTH, HEIGHT);
	}

	
	private void mainLayout() { // creates ui for inbox and message panel
		JPanel main = new JPanel();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		main.add(inbox());
		main.add(newMessage());
		add(main);
	}
	
	private JPanel inbox() { // creates ui for inbox panel
		inboxPanel = new JPanel();
		inboxPanel.setBorder(new TitledBorder(new EtchedBorder(),"Inbox"));
		JList received = mail(user);
		JPanel messages = new JPanel();
		messages.setLayout(new BorderLayout());
		messages.add(text);
		
		inboxPanel.setLayout(new GridLayout(1, 2, 3, 3));
		inboxPanel.add(received);
		inboxPanel.add(messages);
		JScrollPane scrollPane = new JScrollPane(received);
		JScrollPane scrollPane2 = new JScrollPane(messages);
		inboxPanel.add(scrollPane);
		inboxPanel.add(scrollPane2);
		return inboxPanel;
	}
	
	private JList mail(String user) { // creates ui for the JList and displays emails in order
		ArrayList<String> senders = new ArrayList<String>();
		ArrayList<String> entireMsg = new ArrayList<String>();
		User userCopy = new User(user);
		
		if (recipients.get(user) != null) {
			while (recipients.get(user).getMessages().size() > 0) {
				Message m = recipients.get(user).getMessages().remove();
				senders.add("From: " + m.getSender() + ", Subject: " + m.getSubject());
				entireMsg.add(m.toString());
				userCopy.addMessageToUser(m);
			}
			recipients.put(user, userCopy);
		}
		
		receivedMail = new JList(senders.toArray());
		receivedMail.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) { // gets index of the item selected and calls on the arraylist to display message
				if (!e.getValueIsAdjusting()) {
					int index = receivedMail.getSelectedIndex();
		        	rMessageText(entireMsg.get(index));
		          }
			}
		});
		return receivedMail;
	}
	
	private JTextArea rMessageText(String msg) { // shows email after JList is clicked
		text.setText(msg);
		return text;
	}
	
	private JPanel newMessage() { // ui for the new message area
		newMessagePanel = new JPanel();
		newMessagePanel.setBorder(new TitledBorder(new EtchedBorder(),"New Message"));
		JPanel recipientlist = recipientList();
		JPanel priority = priorityButtons();
		JPanel subjectText = subjectText();
		JPanel messageText = messageText();
		JPanel messageButtons = messageButtons();
		newMessagePanel.setLayout(new BoxLayout(newMessagePanel, BoxLayout.Y_AXIS));
		newMessagePanel.add(recipientlist);
		newMessagePanel.add(priority);
		newMessagePanel.add(subjectText);
		newMessagePanel.add(messageText);
		JScrollPane scrollPane = new JScrollPane(messageText);
		newMessagePanel.add(scrollPane);
		newMessagePanel.add(messageButtons);
		return newMessagePanel;
	}
	
	private JPanel recipientList() { // makes combobox of recipients
		userCombo = new JComboBox<String>();
		for (String r : recipients.keySet()) {
			if (!r.equals(user))
				userCombo.addItem(r); // adds potential recipients to combobox if it is not the current user
		}
		
		recipientPanel = new JPanel();
		recipientPanel.add(new JLabel("To: "));
		recipientPanel.add(userCombo);
		return recipientPanel;
	}
	
	private JPanel priorityButtons() { // deals with priority buttons ui
		high = new JRadioButton("High");
		high.setSelected(true);
		medium = new JRadioButton("Medium");
		low = new JRadioButton("Low");	

		ButtonGroup group = new ButtonGroup();
		group.add(high);
		group.add(medium);
		group.add(low);
		
		JPanel panel = new JPanel();
		panel.add(new JLabel("Priority: "));
		panel.add(high);
		panel.add(medium);
		panel.add(low);
		
		return panel;
	}
	
	private JPanel messageButtons() { // send and clear buttons for messages
		JButton clear = new JButton("Clear");	
		clear.addActionListener(new ActionListener() {

		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	messageText.setText("");
				subjectText.setText("");
				high.setSelected(true);
		    }
		});
		
		JButton send = new JButton("Send");
		send.addActionListener(listener);
		
		ButtonGroup group = new ButtonGroup();
		group.add(send);
		group.add(clear);
		
		JPanel panel = new JPanel();
		panel.add(send);
		panel.add(clear);
		
		return panel;
	}
	
	private JPanel subjectText() { // subject text ui
		JPanel panel = new JPanel();
		panel.add(new JLabel("Subject: "));
		final int FIELD_WIDTH = 25;
		subjectText = new JTextField(FIELD_WIDTH);
		panel.add(subjectText);
		return panel;
	}
	
	private JPanel messageText() { // message text ui (editable)
		JPanel panel = new JPanel();
		final int ROWS = 15;
		final int COLUMNS = 40;
		messageText = new JTextArea(ROWS, COLUMNS);
		messageText.setEditable(true);
		panel.setLayout(new BorderLayout());
		panel.add(messageText);
		return panel;
	}
	
	private void sendMail() { // retrieves info from other buttons and adds message to the user
		Message msg;
		String recip = (String)userCombo.getSelectedItem();
		if (high.isSelected())
			priority = 3;
		else if (medium.isSelected())
			priority = 2;
		else if (low.isSelected())
			priority = 1;
		msg = new Message(priority, user, recip, subjectText.getText(), messageText.getText());
		recipients.get(recip).addMessageToUser(msg);
		JOptionPane.showMessageDialog(null, "Message sent!");
		messageText.setText("");
		subjectText.setText("");
		high.setSelected(true);
	}
	
	class PriorityListener implements ActionListener { // listener for send button
		public void actionPerformed(ActionEvent event) {
			sendMail();
		}
	}
	

	public static void main(String[] args) {
		LoginSystem login = new LoginSystem();
		try {
			JFrame email = new EmailSystem(login.getUserName());
			email.setTitle("Email System - Meggie Nguyen - 48261700");
			email.setLocationRelativeTo(null);
			email.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			email.setVisible(true);
		} catch (NullPointerException e) { };
		
	}
}