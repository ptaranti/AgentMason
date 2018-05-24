package agentMason.simulationClock.remoteAccess.test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import agentMason.simulationClock.Statistics;
import agentMason.simulationClock.remoteAccess.RMISimulationTime;
import agentMason.simulationClock.remoteAccess.RMITardinessCollector;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class RemoteClientAccessClockConnectorTest {

	long oldSimulationTime = 0;
	private static final String HOST = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_IP;
	private static final int PORT = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_PORT;
	static RMISimulationTime remoteRMISimulationTime;
	static RMITardinessCollector remoteRMITardinessCollector;

	public static void main(String[] args) throws Exception {

		remoteRMISimulationTime = (RMISimulationTime) LocateRegistry
				.getRegistry(HOST, PORT).lookup(
						RMISimulationTime.class.getSimpleName());

		remoteRMITardinessCollector = (RMITardinessCollector) LocateRegistry
				.getRegistry(HOST, PORT).lookup(
						RMITardinessCollector.class.getSimpleName());

		long time = System.currentTimeMillis();
		long finalTime = time + 100000;
		long i = 1;
		do {
			System.out
					.println("remoteRMISimulationTime.getSimulationTime()  = "
							+ remoteRMISimulationTime.getSimulationTime());
			i++;
			System.out
					.println("remoteRMITardinessCollector.sendTardiness(i) success  = "
							+ remoteRMITardinessCollector.sendTardiness(i));

			System.out
					.println("remoteRMISimulationTime.isKillSimulation() state"
							+ remoteRMISimulationTime.isKillSimulation());
			System.out
					.println("remoteRMISimulationTime.getSimulationTimeDTO(new Statistics())"
							+ remoteRMISimulationTime
									.getSimulationTimeDTO(new Statistics()));

		} while (System.currentTimeMillis() < finalTime);

		System.out.println(i / 10 + "informations per second");
	}

}
