package agentMason.simulationClock.simulationRateControl.controlApproach;


import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.core.Agent;

public class HandleTimeRateBehaviorSqr01 extends HandleTimeRateBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int unstabilityLevel;
	double A;
	double B;



	public HandleTimeRateBehaviorSqr01(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
		this.A = (1 / ((PropertiesLoaderImpl.MAX_ERROR_TRIGGER) * ((2 * (PropertiesLoaderImpl.ERROR_TRIGGER) - PropertiesLoaderImpl.MAX_ERROR_TRIGGER))));
		this.B = A * (2 * (PropertiesLoaderImpl.ERROR_TRIGGER));
		this.unstabilityLevel = 0;
	}


	void errorControl() {
		
		double currentError = currentStatistics.getMaxErrorInformed();
		// TODO Auto-generated method stub
		if (currentError >  PropertiesLoaderImpl.ERROR_TRIGGER) {
			
			double newClockRate = 0.1;
			double tempClockRate = 0.1;
			
			if (unstabilityLevel < 0)
				unstabilityLevel = -1;
			unstabilityLevel++;

			double Y1 = Math.pow((1 / 2), (unstabilityLevel / 2));
			tempClockRate = Y1;

			if (currentError < PropertiesLoaderImpl.MAX_ERROR_TRIGGER) {
				double Y2 = (A * Math.sqrt(currentError))
						- (B * currentError) + 1;
				tempClockRate = (Y1 + Y2) / 2;
			}

			if (newClockRate < tempClockRate)
				newClockRate = tempClockRate;
			if (PropertiesLoaderImpl.DEBUG)
				System.out.println("02 CONTROLE DIMINUI RATE "
						+ newClockRate);
			simulationClockControl.setSimulationClockRate(newClockRate);
		} else {
			double newClockRate = 0.1;
			//double tempClockRate = 0.1;
			unstabilityLevel--;

			if (PropertiesLoaderImpl.AUTOMATIC_CONTROL) {
				if ((unstabilityLevel * (-1)) > PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE) {

					double stableCycles = unstabilityLevel
							+ PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE;

					double tmp = stableCycles / (-2);
					tmp = Math.pow(2, tmp);
					if (PropertiesLoaderImpl.DEBUG)
						System.out.println("tmp " + tmp);
					newClockRate = (-1) / tmp;

					if (PropertiesLoaderImpl.DEBUG)
						System.out.println("01 CONTROLE AUMENTAR RATE "
								+ newClockRate + "\nStablesCicles "
								+ stableCycles);

					simulationClockControl
							.setSimulationClockRate(newClockRate);
				}
			}
		}
		
	}



}
