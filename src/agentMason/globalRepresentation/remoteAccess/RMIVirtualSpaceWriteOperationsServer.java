package agentMason.globalRepresentation.remoteAccess;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Hashtable;

import agentMason.globalRepresentation.AgentPosition;
import agentMason.globalRepresentation.VirtualSpaceWriteOperations;
import agentMason.globalRepresentation.VirtualSpaceWriteOperationsImp;
import agentMason.util.RMIMainNodeRegistry;

public class RMIVirtualSpaceWriteOperationsServer extends UnicastRemoteObject
		implements RMIVirtualSpaceWriteOperations {

	private VirtualSpaceWriteOperations virtualSpaceWriteOperationsConnection = VirtualSpaceWriteOperationsImp.getInstance();

	protected RMIVirtualSpaceWriteOperationsServer() throws RemoteException {
		super();

	}

	public static void startRMIVirtualSpaceWriteOperationsServer() {
		try {
			RMIMainNodeRegistry.getInstance().registerObject(
					RMIVirtualSpaceWriteOperations.class.getSimpleName(),
					new RMIVirtualSpaceWriteOperationsServer());
		} catch (RemoteException | AlreadyBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void updateAgentPosition(AgentPosition agentPosition)
			throws RemoteException {
		virtualSpaceWriteOperationsConnection
				.updateAgentPosition(agentPosition);
		//System.out.println("CHAMARAM updateAgentPosition  - POSICAO REC: " +agentPosition);

	}

	@Override
	public void createAgentPosition(AgentPosition agentPosition)
			throws RemoteException {
		virtualSpaceWriteOperationsConnection
				.createAgentPosition(agentPosition);
		//System.out.println("CHAMARAM CREATEAgentPosition - POSICAO REC: " +agentPosition);
	}

	@Override
	public void deleteAgentPosition(AgentPosition agentPosition)
			throws RemoteException {
		virtualSpaceWriteOperationsConnection
				.deleteAgentPosition(agentPosition);

	}

	@Override
	public Hashtable<String, AgentPosition> getSurfacePicture(
			long currentSimulationTime) throws RemoteException {
		return virtualSpaceWriteOperationsConnection
				.getSurfacePicture(currentSimulationTime);

	}

}
