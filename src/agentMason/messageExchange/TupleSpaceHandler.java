package agentMason.messageExchange;

import java.io.Serializable;
import java.util.List;

public interface TupleSpaceHandler extends Serializable{


	/**
	 * @param sender
	 * @param receiver
	 * @param messageType
	 * @param simulationTime
	 * @return the oldest message that matches the template, and whose simulation time is lesser then the informed in parameters
	 */
	public MessageTuple getMessage(String sender, String receiver, String messageClassName, long simulationTime);
	
	/**
	 * @param sender
	 * @param receiver
	 * @param messageType
	 * @param simulationTime
	 * @return all messages that match the template, and whose simulation time is lesser then the informed in parameters
	 */
	public List<MessageTuple> getAllMessages(String sender, String receiver, String messageClassName, long simulationTime);
	
	
	/**
	 * @param messageTuple
	 */
	public void sendMessage(MessageTuple messageTuple);
	
	/**
	 * @param receiverAgentIDList
	 * @param messageTuple
	 */
	public void sendMessage(List<String> receiverAgentIDList, MessageTuple messageTuple );
	
	
}
