package agentMason.globalRepresentation.remoteAccess;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

import agentMason.globalRepresentation.AgentPosition;
import agentMason.globalRepresentation.VirtualSpaceReadOperations;
import agentMason.util.RMIMainNodeRegistry;

public class RMIVirtualSpaceReadOperationsServer extends UnicastRemoteObject
		implements RMIVirtualSpaceReadOperations {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VirtualSpaceReadOperations virtualSpaceReadOperationsConnection;

	protected RMIVirtualSpaceReadOperationsServer() throws RemoteException {
		super();

		virtualSpaceReadOperationsConnection = GlobalRepresentationConnectorFactoryProducer
				.getGlobalRepresentationConnector()
				.getVirtualSpaceReadOperationsConnection();
	}

	public static void startRMIVirtualSpaceReadOperationsServer() {
		try {
			RMIMainNodeRegistry.getInstance().registerObject(
					RMIVirtualSpaceReadOperations.class.getSimpleName(),
					new RMIVirtualSpaceReadOperationsServer());
		} catch (RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public AgentPosition getAgentPosition(String agentID)
			throws RemoteException {
		return virtualSpaceReadOperationsConnection.getAgentPosition(agentID);

	}

	@Override
	public Hashtable<String, AgentPosition> getAgentsIntoRange(
			AgentPosition agentPosition, Double range) throws RemoteException {
		return virtualSpaceReadOperationsConnection.getAgentsIntoRange(
				agentPosition, range);
	}

	@Override
	public Hashtable<String, AgentPosition> getSurfacePicture()
			throws RemoteException {
		return virtualSpaceReadOperationsConnection.getSurfacePicture();
	}

}
