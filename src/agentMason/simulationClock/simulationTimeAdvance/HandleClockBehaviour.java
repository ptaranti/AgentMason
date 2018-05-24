package agentMason.simulationClock.simulationTimeAdvance;

import java.rmi.RemoteException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import agentMason.StartAgentMason;
import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.SimulationClockControl;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class HandleClockBehaviour extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected SimulationClock simulationClock = SimulationClock.getInstance();
	protected SimulationClockControl simulationClockControl = SimulationClockControl
			.getInstance();

	private long lastSimulationTime;
	public boolean simulationStarted;
	private boolean isHeatingCycle;
	private boolean killSimulation;

	private long startRealTime;
	private long lastRealTime;

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static Lock readLock = rwl.readLock();
	private Lock writeLock = rwl.writeLock();

	@Override
	protected void onTick() {

		writeLock.lock();
		try {
			
			// System.out.println("iniciando update tempo ");
			double simulationClockRate = simulationClockControl
					.getSimulationClockRate();
			long nowRealTime = System.currentTimeMillis() - startRealTime;
			double incrementSimulationTime = ((nowRealTime - this.lastRealTime) * simulationClockRate);
			this.lastRealTime = nowRealTime;
			this.lastSimulationTime = this.lastSimulationTime
					+ (long) incrementSimulationTime;

			// code to stop the experiment when achieve the parameter
			// EXPERIMENT_DURATION_MIN
			if (!isHeatingCycle
					&& nowRealTime >= (PropertiesLoaderImpl.EXPERIMENT_DURATION_MIN * 60 * 1000)) {
				System.out
						.println("******************************************");
				System.out
						.println("*** The experiment has finished       ****");
				System.out
						.println("******************************************");

				simulationClock.setSimulationStarted(false);
				System.exit(0);
			}

			if (isHeatingCycle
					&& (nowRealTime >= (60 * 1000 * PropertiesLoaderImpl.TIME_FOR_HEATING))) {
				PropertiesLoaderImpl
						.setINITIAL_SIMULATION_TIME_RATE(simulationClockControl
								.getSimulationClockRate());
				System.out
						.println("******************************************");
				System.out
						.println("*** The heating cycle has finished    ****");
				System.out
						.println("******************************************");
				this.killSimulation = true;
				PropertiesLoaderImpl.setUseHeatingCycle(false);

				simulationClock.setSimulationStarted(false);

				this.isHeatingCycle = false;
				this.lastSimulationTime = 0;

				StartAgentMason.stopHeating2();
			}

			simulationClock.setSimulationTime(lastSimulationTime);
			simulationClock.setSimualtionClockRate(simulationClockRate);

		} finally {
			writeLock.unlock();
		}

	}

	protected void setup() {

	}

	public HandleClockBehaviour(Agent a, long period) {
		super(a, period);
		this.startRealTime = System.currentTimeMillis();
		this.lastRealTime = 0;
		simulationClockControl = SimulationClockControl.getInstance();
		// this.lastSimulationTime = PASSO_INCREMENTO_TEMPO_SIMULACAO + 1;
		this.isHeatingCycle = PropertiesLoaderImpl.USE_HEATING_CYCLE;
		this.killSimulation = false;
	}

}
