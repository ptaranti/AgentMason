package agentMason.core;

import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.remoteAccess.SimulationClockConnectorAbstractFactory;
import agentMason.simulationClock.remoteAccess.SimulationClockConnectorFactoryProducer;
import agentMason.simulationClock.remoteAccess.SimulationTime;
import agentMason.simulationClock.remoteAccess.SimulationTimeDTO;
import agentMason.simulationClock.remoteAccess.TardinessCollector;
import agentMason.util.logSupport.BehaviorsLogger;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import shipSimulation.UnitState;
import sim.engine.SimState;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;

public abstract  class AgentMason extends Agent {

	private static final long serialVersionUID = 1L;
	/**
	 * must indicate a Mason Model
	 */
	public SimState state; // MyModel
																		// is
																		// our
																		// SimState
	
	// subclass

	public AgentMason() {
		super();
		
	}

	protected void setup() {
		super.setup();
		System.out.println(this.getLocalName());
		
		
		modelStateLoader();
		
		
		TardinessLevelInformBehaviour tardinessLevelInformBehaviour;
		tardinessLevelInformBehaviour = new TardinessLevelInformBehaviour(this);
		MasonSimulationModelDriverBehaviour masonSimulationModelDriverBehaviour = new MasonSimulationModelDriverBehaviour(
				this);
		//masonSimulationModelDriverBehaviour.reset(2000);

		if (tardinessLevelInformBehaviour != null)
			addBehaviour(tardinessLevelInformBehaviour);
		addBehaviour(masonSimulationModelDriverBehaviour);

	}

	protected  abstract  void modelStateLoader();  //load the model
	
	
	


}

class TardinessLevelInformBehaviour extends SimpleBehaviour {

	private SimulationTime simulationTimeConnection;
	private TardinessCollector tardinessCollectorConnection;

	private double lastSimualtionTime = 0;

	private static final long serialVersionUID = 1L;

	private double wakeupSimulationTime;
	protected double simulatedTimePeriod;
	private boolean finished;
	protected int tickCount = 0;
	double speed;
	AgentMason a;

	protected int logStep = 1;

	public TardinessLevelInformBehaviour(AgentMason a) {
		super(a);
		setSimulatedTimePeriod();
		
		SimulationClockConnectorAbstractFactory simulationClockConnectorAbstractFactory = SimulationClockConnectorFactoryProducer.getSimulationClockConnector(); 
		simulationTimeConnection = simulationClockConnectorAbstractFactory.getSimulationTimeConnection();
		tardinessCollectorConnection = simulationClockConnectorAbstractFactory.getTardinesCollectorConnection();
		SimulationClock simulationClock = SimulationClock.getInstance();

	}

	public void onStart() {

		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("DEBUG " + myAgent.getLocalName() + " "
					+ this.getBehaviourName() + " -> behaviour started ");

	}

	public final void action() {
		// Someone else may have stopped us in the meanwhile
		
	
		
		if (!finished) {

			if (simulationTimeConnection.isKillSimulation()) a.doDelete();

			double now = this.lastSimualtionTime;

			now = simulationTimeConnection.getSimulationTime();
			// System.out.println(">>>>>>> " + now + " " +
			// wakeupSimulationTime);
			double blockTime = wakeupSimulationTime - now;
			if (blockTime <= 0) {
				// Timeout is expired --> execute the user defined action and
				// re-initialize wakeupTime

				// if(PropertiesLoaderImpl.TESTING_DEVS)a.stopSimulationTimeTardinessInforming(a);

				if (PropertiesLoaderImpl.DEBUG)
					System.out.println("DEBUG " + myAgent.getLocalName() + " "
							+ this.getBehaviourName() + " Cycle "
							+ this.tickCount);

				double tardiness = (((simulatedTimePeriod - blockTime) / simulatedTimePeriod) - 1);

				if (PropertiesLoaderImpl.DEBUG)
					System.out.println(this + " " + tardiness + " blockTime "
							+ blockTime + " simulatedTimePeriod "
							+ simulatedTimePeriod);

				if (PropertiesLoaderImpl.DEBUG)
					System.out.println("DEBUG " + myAgent.getLocalName() + " ");
				
				//System.out.println(this.getBehaviourName() + " error "
				//		+ tardiness);

				//System.out.println(myAgent.getLocalName());

				try {
					if (PropertiesLoaderImpl.FILE_LOG)
						BehaviorsLogger.getInstance().executeLogBehaviour(
								myAgent.getLocalName(), this, "startLoad",
								System.currentTimeMillis(), now, tardiness,
								blockTime);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				tickCount++;

				wakeupSimulationTime = now + simulatedTimePeriod;

				informError(tardiness);

				this.lastSimualtionTime = now;

			}
		

		}
	}

	public final boolean done() {
		return finished;
	}

	/**
	 * This method must be called to reset the behaviour and starts again
	 * 
	 * @param period
	 *            the new tick time
	 */
	public void reset(long simulatedTimePeriod) {
		this.reset();
		if (simulatedTimePeriod <= 0) {
			throw new IllegalArgumentException("Period must be greater than 0");
		}
		this.simulatedTimePeriod = simulatedTimePeriod;
	}

	/**
	 * This method must be called to reset the behaviour and starts again
	 * 
	 * @param timeout
	 *            indicates in how many milliseconds from now the behaviour must
	 *            be waken up again.
	 */
	public void reset() {
		super.reset();
		finished = false;
		tickCount = 0;
	}

	/**
	 * Make this <code>TickerBehaviour</code> terminate. Calling stop() has the
	 * same effect as removing this TickerBehaviour, but is Thread safe
	 */
	public void stop() {
		finished = true;
	}

	/**
	 * Retrieve how many ticks were done (i.e. how many times this behaviour was
	 * executed) since the last reset.
	 * 
	 * @return The number of ticks since the last reset
	 */
	public final int getTickCount() {
		return tickCount;
	}

	// #MIDP_EXCLUDE_BEGIN

	// For persistence service
	private void setTickCount(int tc) {
		tickCount = tc;
	}

	// For persistence service

	private void setSimulatedTimePeriod(long newPeriod) {

		this.simulatedTimePeriod = newPeriod;

	}

	// For persistence service
	private void setWakeupSimulationTime(long wt) {
		simulatedTimePeriod = wt;
	}

	// For persistence service
	private double getWakeupSimulationTime() {
		return simulatedTimePeriod;
	}

	public void informError(double error) {

		tardinessCollectorConnection.sendTardiness(error);

	}

	// For persistence service
	private void setSimulatedTimePeriod() {
		this.simulatedTimePeriod = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME / 10; // fazer
																								// 10
																								// verificações
																								// a
																								// cada
																								// DEFAULT_SLICE_TIME
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("DEBUG " + myAgent.getLocalName() + " "
					+ this.getBehaviourName() + "  rate de execução "
					+ this.simulatedTimePeriod);
	}

}

class GlobalRepresentationDataExchangeBehaviour extends SimpleBehaviour {

	@Override
	public void action() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
}
