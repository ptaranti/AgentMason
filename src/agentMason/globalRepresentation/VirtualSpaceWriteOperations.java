package agentMason.globalRepresentation;

import java.io.Serializable;
import java.util.Hashtable;


public interface VirtualSpaceWriteOperations extends Serializable{
	
	public void updateAgentPosition(AgentPosition agentPosition);
	public void createAgentPosition(AgentPosition agentPosition);
	public void deleteAgentPosition(AgentPosition agentPosition);
	public Hashtable<String, AgentPosition> getSurfacePicture(long currentSimulationTime) ;
	
	
}
