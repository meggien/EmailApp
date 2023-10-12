package labs.lab9;

import java.util.PriorityQueue;

public class User {
	private String username;
	private PriorityQueue<Message> messages;
	
	public User(String username) { // Creates a queue of received messages for a user
		messages = new PriorityQueue<Message>();
		this.username = username;
		
	}
	
	public String getName() {
		return username;
	}
	
	public void addMessageToUser(Message m) {
		messages.add(m);
	}
	
	public PriorityQueue<Message> getMessages() {
		return messages;
	}
	
}