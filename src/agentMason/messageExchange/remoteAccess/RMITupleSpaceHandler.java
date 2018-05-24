package agentMason.messageExchange.remoteAccess;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import agentMason.messageExchange.MessageTuple;

public interface RMITupleSpaceHandler extends Remote {


	/**
	 * @param sender
	 * @param receiver
	 * @param messageType
	 * @param simulationTime
	 * @return the oldest message that matches the template, and whose simulation time is lesser then the informed in parameters
	 */
	public MessageTuple getMessage(String sender, String receiver, String messageClassName, long simulationTime)throws RemoteException;
	
	/**
	 * @param sender
	 * @param receiver
	 * @param messageType
	 * @param simulationTime
	 * @return all messages that match the template, and whose simulation time is lesser then the informed in parameters
	 */
	public List<MessageTuple> getAllMessages(String sender, String receiver, String messageClassName, long simulationTime)throws RemoteException;
	
	
	/**
	 * @param messageTuple
	 */
	public void sendMessage(MessageTuple messageTuple)throws RemoteException;
	
	/**
	 * @param receiverAgentIDList
	 * @param messageTuple
	 */
	public void sendMessage(List<String> receiverAgentIDList, MessageTuple messageTuple )throws RemoteException;
	
	
}

