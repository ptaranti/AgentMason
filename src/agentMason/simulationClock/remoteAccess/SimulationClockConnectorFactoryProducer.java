package agentMason.simulationClock.remoteAccess;

import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class SimulationClockConnectorFactoryProducer {

	public static SimulationClockConnectorAbstractFactory getSimulationClockConnector() {
		
		if(PropertiesLoaderImpl.DEBUG) System.out.println(SimulationClockConnectorFactoryProducer.class + " PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE received with value = "+PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE);
		
		if (PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE) {
			return new LocalSimulationClockConnectorFactory();
		}
		if (!PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE) {
			return new LocalSimulationClockConnectorFactory(); //test only local clocks. 25sep
		//	return new RMISimulationClockConnectorFactory();
		}
		return null;
	}
}