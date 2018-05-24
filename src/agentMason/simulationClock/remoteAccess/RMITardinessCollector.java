package agentMason.simulationClock.remoteAccess;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMITardinessCollector extends Remote{
	
	//returns the simulation time in milliseconds
	public boolean sendTardiness(double tardiness) throws RemoteException;

}
