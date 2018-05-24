package agentMason.globalRepresentation.remoteAccess;

import agentMason.globalRepresentation.VirtualSpaceReadOperations;
import agentMason.globalRepresentation.VirtualSpaceWriteOperations;
import agentMason.simulationClock.remoteAccess.RMISimulationTimeClient;
import agentMason.simulationClock.remoteAccess.RMITardinessCollectorClient;
import agentMason.simulationClock.remoteAccess.SimulationClockConnectorAbstractFactory;
import agentMason.simulationClock.remoteAccess.SimulationTime;
import agentMason.simulationClock.remoteAccess.TardinessCollector;

public class RMIVirtualSpaceOperationsConnectorFactory extends
		GlobalRepresentationConnectorAbstractFactory {

	@Override
	public VirtualSpaceReadOperations getVirtualSpaceReadOperationsConnection() {
		return (VirtualSpaceReadOperations) new RMIVirtualSpaceReadOperationsClient();
	}

	@Override
	public VirtualSpaceWriteOperations getVirtualSpaceWriteOperationsConnection() {
		return (VirtualSpaceWriteOperations) new RMIVirtualSpaceWriteOperationsClient();
	}

}


	

