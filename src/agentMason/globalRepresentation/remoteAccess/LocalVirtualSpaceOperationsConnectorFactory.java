package agentMason.globalRepresentation.remoteAccess;

import agentMason.globalRepresentation.VirtualSpaceReadOperations;
import agentMason.globalRepresentation.VirtualSpaceReadOperationsImp;
import agentMason.globalRepresentation.VirtualSpaceWriteOperations;
import agentMason.globalRepresentation.VirtualSpaceWriteOperationsImp;


public class LocalVirtualSpaceOperationsConnectorFactory extends
GlobalRepresentationConnectorAbstractFactory {

	@Override
	public VirtualSpaceReadOperations getVirtualSpaceReadOperationsConnection() {
		return VirtualSpaceReadOperationsImp.getInstance();
	}

	@Override
	public VirtualSpaceWriteOperations getVirtualSpaceWriteOperationsConnection() {
		return VirtualSpaceWriteOperationsImp.getInstance();
	}

}


