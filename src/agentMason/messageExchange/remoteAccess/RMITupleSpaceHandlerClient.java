package agentMason.messageExchange.remoteAccess;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;


import agentMason.messageExchange.MessageTuple;
import agentMason.messageExchange.TupleSpaceHandler;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class RMITupleSpaceHandlerClient implements TupleSpaceHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String HOST = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_IP;
	private static final int PORT = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_PORT;


	
	
	RMITupleSpaceHandler  remoteRMITupleSpaceHandler;
	
	
	
	public RMITupleSpaceHandlerClient() {

	
			try {
				this.remoteRMITupleSpaceHandler = (RMITupleSpaceHandler) LocateRegistry.getRegistry(HOST, PORT).lookup(RMITupleSpaceHandler.class.getSimpleName());
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
	
	
	@Override
	public MessageTuple getMessage(String sender, String receiver,
			String messageClassName, long simulationTime) {
		try {
			return remoteRMITupleSpaceHandler.getMessage( sender,  receiver,
					messageClassName,  simulationTime);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MessageTuple> getAllMessages(String sender, String receiver,
			String messageClassName, long simulationTime) {
		try {
			return remoteRMITupleSpaceHandler.getAllMessages( sender,  receiver,
					messageClassName,  simulationTime);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void sendMessage(MessageTuple messageTuple) {
		try {
			 remoteRMITupleSpaceHandler.sendMessage(messageTuple);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void sendMessage(List<String> receiverAgentIDList,
			MessageTuple messageTuple) {
		try {
			 remoteRMITupleSpaceHandler.sendMessage(receiverAgentIDList, messageTuple);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
