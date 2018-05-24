package agentMason.messageExchange.remoteAccess;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import agentMason.messageExchange.MessageTuple;
import agentMason.messageExchange.TupleSpaceHandler;
import agentMason.messageExchange.TupleSpaceHandlerImp;
import agentMason.util.RMIMainNodeRegistry;

public class RMITupleSpaceHandlerServer extends UnicastRemoteObject implements RMITupleSpaceHandler
 {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private TupleSpaceHandler tupleSpaceHandlerConnection;
	
	
	protected RMITupleSpaceHandlerServer() throws RemoteException {
		super();
	
		this.tupleSpaceHandlerConnection = TupleSpaceHandlerImp.getInstance();
	}
	
	
	public static void startRMITupleSpaceHandlerServer() {
		try {
			RMIMainNodeRegistry.getInstance().registerObject(
					RMITupleSpaceHandler.class.getSimpleName(),
					new RMITupleSpaceHandlerServer());
		} catch (RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public MessageTuple getMessage(String sender, String receiver,
			String messageClassName, long simulationTime) throws RemoteException {
		
		return tupleSpaceHandlerConnection.getMessage(sender, receiver, messageClassName, simulationTime);
	}

	@Override
	public List<MessageTuple> getAllMessages(String sender, String receiver,
			String messageClassName, long simulationTime) throws RemoteException {
		return tupleSpaceHandlerConnection.getAllMessages(sender, receiver, messageClassName, simulationTime);
	}

	@Override
	public void sendMessage(MessageTuple messageTuple) throws RemoteException {
		tupleSpaceHandlerConnection.sendMessage(messageTuple);
		
	}

	@Override
	public void sendMessage(List<String> receiverAgentIDList,
			MessageTuple messageTuple) throws RemoteException {
		tupleSpaceHandlerConnection.sendMessage(receiverAgentIDList, messageTuple);
		
	}

}



