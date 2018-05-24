package agentMason.globalRepresentation.remoteAccess;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Hashtable;

import agentMason.globalRepresentation.AgentPosition;
import agentMason.globalRepresentation.VirtualSpaceWriteOperations;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

@SuppressWarnings("serial")
public class RMIVirtualSpaceWriteOperationsClient implements
		VirtualSpaceWriteOperations {

	
	
	
	private static final String HOST = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_IP;
	private static final int PORT = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_PORT;
	private  RMIVirtualSpaceWriteOperations remoteRMIVirtualSpaceWriteOperations;

	
	public RMIVirtualSpaceWriteOperationsClient() {

			try {
				remoteRMIVirtualSpaceWriteOperations = (RMIVirtualSpaceWriteOperations) LocateRegistry.getRegistry(HOST, PORT)
						.lookup(RMIVirtualSpaceWriteOperations.class.getSimpleName());
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
	}
	
	
	@Override
	public void updateAgentPosition(AgentPosition agentPosition) {
		try {
			 remoteRMIVirtualSpaceWriteOperations.updateAgentPosition(agentPosition);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void createAgentPosition(AgentPosition agentPosition) {
		try {
			 remoteRMIVirtualSpaceWriteOperations.createAgentPosition(agentPosition);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void deleteAgentPosition(AgentPosition agentPosition) {
		try {
			 remoteRMIVirtualSpaceWriteOperations.deleteAgentPosition(agentPosition);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Hashtable<String, AgentPosition> getSurfacePicture(long currentSimulationTime) {
		try {
			 return remoteRMIVirtualSpaceWriteOperations.getSurfacePicture(currentSimulationTime);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
