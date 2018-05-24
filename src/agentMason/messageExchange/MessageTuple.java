package agentMason.messageExchange;

import java.io.Serializable;

public class MessageTuple implements Serializable{
	


	public String getSender() {
		return sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public String getMessageClassName() {
		return messageClassName;
	}

	public Object getMessage() {
		return message;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	String sender;
	
	String receiver;
	
	String messageClassName;
	
	Object message;
	
	long simuationTime;
	
	
		public MessageTuple(String sender, String receiver, String messageClassName,
			Object message,long simuationTime) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.messageClassName = messageClassName;
		this.message = message;
		this.simuationTime = simuationTime;
	}
		
		public MessageTuple(){}

	public boolean matchMessageTuple(String sender, String receiver, String messageClassName, long simulationTime){
		if (!this.receiver.equals(receiver)) return false;
		if ((sender != null) && (!this.sender.equals(sender)))return false;
		if ( messageClassName!= null && (!this.messageClassName.equals(messageClassName))) return false;
		if (this.simuationTime > simulationTime) return false;
		return true;
	}
	

	
}
