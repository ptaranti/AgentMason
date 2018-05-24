package agentMason.simulationClock.remoteAccess;

import agentMason.simulationClock.Statistics;

public interface SimulationTime {

	
	//returns the simulation time in milliseconds
	public long getSimulationTime();
	public boolean isKillSimulation();
	public SimulationTimeDTO getSimulationTimeDTO(Statistics otherNodeStatistics);	
}
