/**
 * 
 */
package agentMason.simulationClock.remoteAccess;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import agentMason.simulationClock.Statistics;
import agentMason.util.RMIMainNodeRegistry;

/**
 * @author taranti
 *
 */
public class RMISimulationTimeServer extends UnicastRemoteObject implements
		RMISimulationTime {
   


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private SimulationTime simulationTimeConnection;

	protected RMISimulationTimeServer() throws RemoteException {
		super();

		simulationTimeConnection = SimulationClockConnectorFactoryProducer
				.getSimulationClockConnector()
				.getSimulationTimeConnection();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agentMason.simulationClock.remoteAccess.SimulationTime#getSimulationTime()
	 */
	@Override
	public long getSimulationTime() throws RemoteException {

		return simulationTimeConnection.getSimulationTime();
	}
	
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see agentMason.simulationClock.remoteAccess.SimulationTime#isKillSimulation()
	 */
	@Override
	public boolean isKillSimulation() throws RemoteException  {
		// TODO Auto-generated method stub
		System.out.println("\nXXXXXXXXXXXXXXXXXXX\nrecebida chamada externa isKillSimulation() no server RMI\nXXXXXXXXXXXXXXXXXXXXXXX");
		return simulationTimeConnection.isKillSimulation();
	}
	

	    public static void startRMISimulationTimeServer()   {
	        try {
				RMIMainNodeRegistry.getInstance().registerObject(RMISimulationTime.class.getSimpleName(), new RMISimulationTimeServer());
			} catch (RemoteException | AlreadyBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   
	    }

		@Override
		public SimulationTimeDTO getSimulationTimeDTO(
				Statistics otherNodeStatistics) throws RemoteException {
			return simulationTimeConnection.getSimulationTimeDTO(otherNodeStatistics);
		}

		

		
  

}
