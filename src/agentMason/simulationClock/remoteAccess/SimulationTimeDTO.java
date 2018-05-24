package agentMason.simulationClock.remoteAccess;

import java.io.Serializable;

public class SimulationTimeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final long simulationTime;
	public final double simulationRate;
	public final boolean isKillSimulation;

	public SimulationTimeDTO(long simulationTime, double simulationRate,
			boolean isKillSimulation) {
		super();
		this.simulationTime = simulationTime;
		this.simulationRate = simulationRate;
		this.isKillSimulation = isKillSimulation;

	}

	public String toString() {

		return "SimulationTimeDTO: simulationTime>>" + this.simulationTime
				+ "; simulationRate>>" + this.simulationRate
				+ "; isKillSimulation>>" + this.isKillSimulation;
	}
}
