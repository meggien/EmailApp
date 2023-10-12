package labs.lab9;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Comparable {
	private int priority;
	private String sender, recipient, subject, message;
	private String time;
	private LocalDateTime now;
	
	public Message(int priority, String sender, String recipient, String subject, String message) { // deals with messages and its format
		this.priority = priority;
		this.sender = sender;
		this.recipient = recipient;
		this.subject = subject;
		this.message = message;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");  
		now = LocalDateTime.now();  
		time = dtf.format(now);
	}
	
	public int getPriority() {
		return priority;
	}
	
	public String getSender() {
		return sender;
	}
	
	public String getRecipient() {
		return recipient;
	}
	
	public String getMessage() {
		return message;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getTime() {
		return time;
	}
	
	public LocalDateTime getTimeAsNum() {
		return now;
	}
	
	@Override
    public String toString() {
		String msg, p;
		msg = "From: " + sender + "\nTo: " + recipient + "\nPriority: ";
		if (priority == 3)
			p = "High";
		else if (priority == 2)
			p = "Medium";
		else
			p = "Low";
		msg += p + "\nSubject: " + subject + "\n" + time + "\n\n" + message;
        return msg;
    }
	
	@Override
	public int compareTo(Object otherObject) {
		Message o = (Message)otherObject;
		if (priority == o.getPriority()) {
			return -now.toString().compareTo(o.getTimeAsNum().toString()); 
		}

		return -Integer.compare(priority, o.getPriority());
		
	}
	 
}