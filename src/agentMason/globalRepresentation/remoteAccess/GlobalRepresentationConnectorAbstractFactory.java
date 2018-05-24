package agentMason.globalRepresentation.remoteAccess;

import agentMason.globalRepresentation.VirtualSpaceReadOperations;
import agentMason.globalRepresentation.VirtualSpaceWriteOperations;



public abstract class GlobalRepresentationConnectorAbstractFactory {

	abstract public VirtualSpaceReadOperations getVirtualSpaceReadOperationsConnection();
	   
	abstract public VirtualSpaceWriteOperations getVirtualSpaceWriteOperationsConnection();
	
}


