package agentMason.globalRepresentation;



import java.io.Serializable;
import java.util.Hashtable;


public interface VirtualSpaceReadOperations extends Serializable{
	
	
	public AgentPosition getAgentPosition(String agentID);
	public Hashtable<String, AgentPosition> getAgentsIntoRange(AgentPosition agentPosition, double range );
	public Hashtable<String, AgentPosition> getSurfacePicture();
	
	
}