/**
 * 
 */
package agentMason.simulationClock.remoteAccess;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import agentMason.util.RMIMainNodeRegistry;

/**
 * @author taranti
 *
 */
public class RMITardinessCollectorServer extends UnicastRemoteObject implements
		RMITardinessCollector {

	
	private static Registry registry;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TardinessCollector tardinessCollectorConnection;

	protected RMITardinessCollectorServer() throws RemoteException {
		super();

		tardinessCollectorConnection = SimulationClockConnectorFactoryProducer
				.getSimulationClockConnector()
				.getTardinesCollectorConnection();
	}

	public boolean sendTardiness(double tardiness) throws RemoteException {
		return tardinessCollectorConnection.sendTardiness(tardiness);

	}
	


	    public static void startRMITardinessCollectorServer()  {
	        try {
				RMIMainNodeRegistry.getInstance().registerObject(RMITardinessCollector.class.getSimpleName(), new RMITardinessCollectorServer());
			} catch (RemoteException | AlreadyBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }



}
