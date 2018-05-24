package agentMason.globalRepresentation.remoteAccess;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

import agentMason.globalRepresentation.AgentPosition;

public interface RMIVirtualSpaceWriteOperations extends Remote {
	
	public void updateAgentPosition(AgentPosition agentPosition)throws RemoteException;
	public void createAgentPosition(AgentPosition agentPosition)throws RemoteException;
	public void deleteAgentPosition(AgentPosition agentPosition)throws RemoteException;
	public Hashtable<String, AgentPosition> getSurfacePicture(long currentSimulationTime) throws RemoteException;

}
