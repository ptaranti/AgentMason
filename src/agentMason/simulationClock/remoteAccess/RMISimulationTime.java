package agentMason.simulationClock.remoteAccess;

import java.rmi.Remote;
import java.rmi.RemoteException;

import agentMason.simulationClock.Statistics;

public interface RMISimulationTime extends Remote{

	public long getSimulationTime()throws RemoteException;
	public boolean isKillSimulation() throws RemoteException;
	public SimulationTimeDTO getSimulationTimeDTO(Statistics otherNodeStatistics) throws RemoteException;
}
