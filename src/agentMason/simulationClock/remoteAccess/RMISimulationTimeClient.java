package agentMason.simulationClock.remoteAccess;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

import agentMason.simulationClock.Statistics;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class RMISimulationTimeClient implements SimulationTime {

	long oldSimulationTime = 0;
	private static final String HOST = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_IP;
	private static final int PORT = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_PORT;
	static RMISimulationTime remoteRMISimulationTime;

	public RMISimulationTimeClient() {
		try {
			remoteRMISimulationTime = (RMISimulationTime) LocateRegistry
					.getRegistry(HOST, PORT).lookup(
							RMISimulationTime.class.getSimpleName());
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public long getSimulationTime() {
		// TODO Auto-generated method stub
		try {
			this.oldSimulationTime = remoteRMISimulationTime
					.getSimulationTime();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return oldSimulationTime;

	}

	@Override
	public boolean isKillSimulation() {
		boolean flag = false;
		try {
			flag = remoteRMISimulationTime.isKillSimulation();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public SimulationTimeDTO getSimulationTimeDTO(Statistics otherNodeStatistics) {
		try {
			return remoteRMISimulationTime.getSimulationTimeDTO(otherNodeStatistics);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {

		RMISimulationTimeClient rmiConnector = new RMISimulationTimeClient();
		System.out.println(rmiConnector.getSimulationTime());
		System.out.println(rmiConnector.isKillSimulation());
		System.out.println(rmiConnector.getSimulationTimeDTO(new Statistics()));
	}
}
