package agentMason.core;

import java.rmi.RemoteException;

import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.remoteAccess.SimulationClockConnectorAbstractFactory;
import agentMason.simulationClock.remoteAccess.SimulationClockConnectorFactoryProducer;
import agentMason.simulationClock.remoteAccess.SimulationTime;
import agentMason.simulationClock.remoteAccess.SimulationTimeDTO;
import agentMason.simulationClock.remoteAccess.TardinessCollector;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

/**
 * " Original Class: JADE TickerBehaviour This abstract class implements a
 * <code>Behaviour</code> that periodically executes a user-defined piece of
 * code. The user is expected to extend this class re-defining the method
 * <code>onTick()</code> and including the piece of code that must be
 * periodically executed into it. author Giovanni Caire - TILAB"
 * 
 * 
 * 
 * 
 * 
 * This class was modified for allow use of simulation time in MASP
 * 
 */

public abstract class SimulationBehaviour extends SimpleBehaviour {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	private double wakeupSimulationTime;
	protected double simulatedTimePeriod;
	private boolean finished;
	protected int tickCount = 0;
	Agent a;
	protected int logStep = 1;

	
	SimulationClock simulationClock =  SimulationClock.getInstance();

	public SimulationBehaviour(Agent a) {
		super(a);
		this.a = a;
		this.simulatedTimePeriod = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
	}

	public void onStart() {

		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("DEBUG " + myAgent.getLocalName() + " "
					+ this.getBehaviourName() + " -> behaviour started ");

	}

	public final void action() {
		// Someone else may have stopped us in the meanwhile
		if (!finished) {

			if (simulationClock.isKillSimulation()) a.doDelete();

			double now = simulationClock.getSimulationTime();

			double blockTime = this.wakeupSimulationTime - now;
			
			if (blockTime <= 0) {
				// Timeout is expired --> execute the user defined action and
				// re-initialize wakeupTime

				// if(PropertiesLoaderImpl.TESTING_DEVS)a.stopSimulationTimeTardinessInforming(a);

				// if (PropertiesLoaderImpl.DEBUG)
				//System.out.println("DEBUG " + myAgent.getLocalName() + " "
				//		+ this.getBehaviourName() + " Cycle " + this.tickCount);

				//double tardiness = (((simulatedTimePeriod - blockTime) / simulatedTimePeriod) - 1);

				//if (PropertiesLoaderImpl.DEBUG)
				//	System.out.println("DEBUG " + myAgent.getLocalName() + " "
				//			+ this.getBehaviourName() + " error " + tardiness);

				tickCount++;

				this.onTick();

				wakeupSimulationTime = now + simulatedTimePeriod;

			}
			//else this.block((long) (blockTime / (simulationClock.getSimualtionClockRate() * 10)));

		}
	}


	/**
	 * This method is invoked periodically with the period defined in the
	 * constructor. Subclasses are expected to define this method specifying the
	 * action that must be performed at every tick.
	 */
	protected abstract void onTick();





}
