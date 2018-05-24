package agentMason.simulationClock.remoteAccess;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class RMITardinessCollectorClient implements TardinessCollector {

	private static final String HOST = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_IP;
	private static final int PORT = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_PORT;
	private static Registry registry;
	RMITardinessCollector remoteRMITardinessCollector;

	public RMITardinessCollectorClient() {
		 try {
			remoteRMITardinessCollector = (RMITardinessCollector) LocateRegistry.getRegistry(HOST, PORT).lookup(RMITardinessCollector.class.getSimpleName());
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean sendTardiness(double tardiness) {
		try {
			return remoteRMITardinessCollector.sendTardiness(tardiness);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws Exception {
		registry = LocateRegistry.getRegistry(HOST, PORT);
		RMITardinessCollector remoteRMITardinessCollector = (RMITardinessCollector) registry
				.lookup(RMISimulationTime.class.getSimpleName());
		for (int i = 1; i <= 100; i++) {
			System.out.println("counter = "
					+ remoteRMITardinessCollector.sendTardiness(0));
			Thread.sleep(100);
		}
	}

}
