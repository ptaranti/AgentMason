package agentMason.globalRepresentation.remoteAccess;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;

import agentMason.globalRepresentation.AgentPosition;
import agentMason.globalRepresentation.VirtualSpaceReadOperations;
import agentMason.simulationClock.remoteAccess.RMITardinessCollector;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class RMIVirtualSpaceReadOperationsClient implements
		VirtualSpaceReadOperations {
	
	private static final String HOST = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_IP;
	private static final int PORT = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_PORT;
	RMIVirtualSpaceReadOperations remoteRMIVirtualSpaceReadOperations;

	
	public RMIVirtualSpaceReadOperationsClient() {
			try {
				remoteRMIVirtualSpaceReadOperations = (RMIVirtualSpaceReadOperations) LocateRegistry.getRegistry(HOST, PORT).lookup(RMIVirtualSpaceReadOperations.class.getSimpleName());
			} catch (RemoteException | NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	
	@Override
	public AgentPosition getAgentPosition(String agentID) {
		try {
			return remoteRMIVirtualSpaceReadOperations.getAgentPosition(agentID);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Hashtable<String, AgentPosition> getAgentsIntoRange(
			AgentPosition agentPosition, double range) {
		try {
			Hashtable<String, AgentPosition> agentsIntoRange= new Hashtable<String, AgentPosition>();
			agentsIntoRange.putAll(remoteRMIVirtualSpaceReadOperations.getAgentsIntoRange(
					agentPosition, range));
			
			//System.out.println("agentsIntoRange " +agentsIntoRange);
			
			return agentsIntoRange;
					
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Hashtable<String, AgentPosition> getSurfacePicture() {
		try {
			return remoteRMIVirtualSpaceReadOperations.getSurfacePicture();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}


