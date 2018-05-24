package agentMason.simulationClock.simulationRateControl.controlApproach;

import java.rmi.RemoteException;

import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.SimulationClockControl;
import agentMason.simulationClock.StatisticCollector;
import agentMason.simulationClock.Statistics;
import agentMason.util.logSupport.ClockControlRegisterLogger;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public abstract class HandleTimeRateBehaviour extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected SimulationClock simulationClock;
	protected SimulationClockControl simulationClockControl;

	protected Statistics oldStatistics;
	protected Statistics currentStatistics;

	protected long lastRate;
	protected long lastGoodRate;

	// protected static long stabilityCounter;

	public HandleTimeRateBehaviour(Agent a, long period) {
		super(a, period);
		simulationClock = SimulationClock.getInstance();
		simulationClockControl = SimulationClockControl.getInstance();
		
			oldStatistics = StatisticCollector.getInstance().recoverData();
	}

	@Override
	protected void onTick() {

	
			this.currentStatistics = StatisticCollector.getInstance()
					.recoverData();
		

		// Shout-down the system if a critical error is achieved
		if ((currentStatistics.getNumberOfErrorsGreatherThenTriggerUpperLimit() > 0)
				&& PropertiesLoaderImpl.STOP_WHEN_OVERLOAD) {
			System.out
					.println("\n\n************************************************\n"
							+ "The Simulation achieved an inacetable error level\n"
							+ "The simulation is being stooped \n"
							+ "A new time configuration could be necessary before run it again "
							+ "\n ************************************* ");
			System.exit(1);
		}

		if (PropertiesLoaderImpl.AVOID_OVERLOAD
				|| PropertiesLoaderImpl.AUTOMATIC_CONTROL) {

		
				if (simulationClock.isSimulationStarted())
					errorControl();
			

			if (PropertiesLoaderImpl.DEBUG)
				System.out.println("DEBUG " + myAgent.getLocalName() + " "
						+ this.getBehaviourName() + "currentError: "
						+ currentStatistics.getMaxErrorInformed());
			if (PropertiesLoaderImpl.FILE_LOG)
				ClockControlRegisterLogger.getInstance()
						.executeLogClockControl(
								currentStatistics.getMaxErrorInformed());

			oldStatistics = currentStatistics;
		}

	}

	abstract void errorControl();
}