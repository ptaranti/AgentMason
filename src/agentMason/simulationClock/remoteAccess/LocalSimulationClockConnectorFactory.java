package agentMason.simulationClock.remoteAccess;

import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.StatisticCollector;

public class LocalSimulationClockConnectorFactory extends SimulationClockConnectorAbstractFactory {

	@Override
	public SimulationTime getSimulationTimeConnection() {
		return SimulationClock.getInstance();
	}

	@Override
	public TardinessCollector getTardinesCollectorConnection() {
		// TODO Auto-generated method stub
		return StatisticCollector.getInstance();
	}

}
