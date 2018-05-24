package agentMason.globalRepresentation.remoteAccess;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Hashtable;

import agentMason.globalRepresentation.AgentPosition;

public interface RMIVirtualSpaceReadOperations extends Remote {
	
	public AgentPosition getAgentPosition(String agentID)throws RemoteException;

	public Hashtable<String, AgentPosition> getAgentsIntoRange(AgentPosition agentPosition, Double range )throws RemoteException;

	public Hashtable<String, AgentPosition> getSurfacePicture()throws RemoteException;

	

}
