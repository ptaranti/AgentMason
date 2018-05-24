package agentMason.simulationClock.remoteAccess;

public class RMISimulationClockConnectorFactory extends
		SimulationClockConnectorAbstractFactory {

	@Override
	public SimulationTime getSimulationTimeConnection() {
		return (SimulationTime) new RMISimulationTimeClient();
	}

	@Override
	public TardinessCollector getTardinesCollectorConnection() {
		return (TardinessCollector) new RMITardinessCollectorClient();
	}

}
