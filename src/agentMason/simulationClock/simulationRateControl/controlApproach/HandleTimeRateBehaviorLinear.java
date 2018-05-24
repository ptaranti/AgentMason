package agentMason.simulationClock.simulationRateControl.controlApproach;



import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.core.Agent;

public class HandleTimeRateBehaviorLinear extends HandleTimeRateBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long  stabilityCounter = 0;

	public HandleTimeRateBehaviorLinear(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub

	}


	@Override
	void errorControl() {
		double newRate = simulationClockControl.getSimulationClockRate();
		
		//double stability = currentStatistics.getNumberOfInformations() - currentStatistics.getNumberOfErrorsGreatherThenTriggerPoint();
		
		
		if ( currentStatistics.getNumberOfErrorsGreatherThenTriggerPoint() > 0) {
			if ( stabilityCounter > 0 ) stabilityCounter = (int) ((-1)* Math.pow(currentStatistics.getNumberOfErrorsGreatherThenTriggerPoint(), 2));
			else	stabilityCounter = (int) (stabilityCounter - currentStatistics.getNumberOfErrorsGreatherThenTriggerPoint());
			newRate = optimizeSimulationTimeRate(stabilityCounter);
		} else {
			stabilityCounter++;
				if (PropertiesLoaderImpl.AUTOMATIC_CONTROL) {
				if (stabilityCounter > PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE) {
					newRate = optimizeSimulationTimeRate(stabilityCounter);
				}
			}
		}

		simulationClockControl.setSimulationClockRate(newRate);
	}
	
	public double optimizeSimulationTimeRate(double value) {
		// negative counter counter has as consequence a more fast simulation
		double simulationClockRate = simulationClockControl.getSimulationClockRate()* (1 + (value * PropertiesLoaderImpl.OPTIMIZATION_RATE));
		if (simulationClockRate < 0.01) simulationClockRate = 0.01;
		if (PropertiesLoaderImpl.DEBUG)
			System.out
					.println("DEBUG "
							+ this.getClass()
							+ " new simulation rate = "
							+ simulationClockRate);
		return simulationClockRate;

	}

}
