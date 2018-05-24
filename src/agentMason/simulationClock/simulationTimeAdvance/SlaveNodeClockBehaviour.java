package agentMason.simulationClock.simulationTimeAdvance;

import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.SimulationClockControl;
import agentMason.simulationClock.StatisticCollector;
import agentMason.simulationClock.Statistics;
import agentMason.simulationClock.remoteAccess.RMISimulationTimeClient;
import agentMason.simulationClock.remoteAccess.SimulationTime;
import agentMason.simulationClock.remoteAccess.SimulationTimeDTO;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class SlaveNodeClockBehaviour extends TickerBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected SimulationClock simulationClock = SimulationClock.getInstance();
	protected SimulationClockControl simulationClockControl = SimulationClockControl
			.getInstance();

	private static StatisticCollector statisticCollector = StatisticCollector
			.getInstance();

	private static SimulationTime rMISimulationTimeClient = new RMISimulationTimeClient();

	private static SimulationTimeDTO simulationTimeDTO;

	private static Statistics oldStatistics = new Statistics();

	@Override
	protected void onTick() {

		oldStatistics = statisticCollector.recoverData();

		this.simulationTimeDTO = rMISimulationTimeClient
				.getSimulationTimeDTO(oldStatistics);

		if (simulationTimeDTO.isKillSimulation)
			simulationClock.setKillSimulation(true);
		simulationClock.setSimulationTime(simulationTimeDTO.simulationTime);
		simulationClock
				.setSimualtionClockRate(simulationTimeDTO.simulationRate);
		
		//System.out.println("SlaveClock received: >> "+ simulationTimeDTO);

	}

	protected void setup() {

	}

	public SlaveNodeClockBehaviour(Agent a, long period) {
		super(a, period);
	}

}
