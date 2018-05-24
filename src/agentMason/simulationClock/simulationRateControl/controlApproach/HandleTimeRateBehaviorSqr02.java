package agentMason.simulationClock.simulationRateControl.controlApproach;





import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.core.Agent;


public class HandleTimeRateBehaviorSqr02 extends HandleTimeRateBehaviour {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int unstabilityLevel;
	static double A;
	static double B;

	public HandleTimeRateBehaviorSqr02(Agent a, long period) {
		super(a, period);
		this.lastRate = period;
		// TODO Auto-generated constructor stub
		this.A = (1 / ((PropertiesLoaderImpl.MAX_ERROR_TRIGGER) * ((2 * (PropertiesLoaderImpl.ERROR_TRIGGER) - PropertiesLoaderImpl.MAX_ERROR_TRIGGER))));
		this.B = A * (2 * (PropertiesLoaderImpl.ERROR_TRIGGER));
		this.unstabilityLevel = 0;
	}

	@Override
	void errorControl() {
		double currentError = currentStatistics.getMaxErrorInformed();
			if (currentError > PropertiesLoaderImpl.ERROR_TRIGGER) {
				double newClockRate = 0.001;
				double tempClockRate = 0.001;
				unstabilityLevel = 0;

				double Y1 = Math.pow((1 / 2), (currentStatistics.getMaxErrorInformed() / 2));
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
				unstabilityLevel--;
				double newClockRate = 0.001;
				double tempClockRate = 0.001;
				if (PropertiesLoaderImpl.AUTOMATIC_CONTROL) {
					if (currentError == 0) {
						// if ((unstabilityLevel * (-1)) >
						// simulationClockControl
						// .getStablesCyclesToOptimize()) {

						double stableCycles = unstabilityLevel
								+ PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE;

						double tmp = stableCycles / (-2);
						tmp = Math.pow(2, tmp);
						newClockRate = (-1) / tmp;

						if (PropertiesLoaderImpl.DEBUG)
							System.out.println("01 CONTROLE AUMENTAR RATE "
									+ newClockRate + "\nStablesCicles "
									+ stableCycles);

						this.simulationClockControl
								.setSimulationClockRate(newClockRate);

						// to avoid fast increments increment in rate that could
						// cause instability to system, it is necessary
						// increment the verificatiom time.
						// the above code intend to do this:
						// some variables shoul be parameterized in future
						// release

						// criar estrutura que reveba o numero de slice time na
						// carga e defina o maxima rate a partir daí.

						int NUMBER_STABLES_INTERATION_FOR_INCREMENT_STABILITY = 2;
						double periodIncremet = 100;
						//long maximumNewBehavourTicker = PropertiesLoaderImpl.TIME_RATE_CONTROL * 10;

						if (PropertiesLoaderImpl.DEBUG)
							System.out.println("unstabilityLevel -  "
									+ unstabilityLevel);
						if (PropertiesLoaderImpl.DEBUG)
							System.out
									.println("(-1)*NUMBER_STABLES_INTERATION_FOR_INCREMENT_STABILITY - "
											+ (-1)
											* NUMBER_STABLES_INTERATION_FOR_INCREMENT_STABILITY);
						if (PropertiesLoaderImpl.DEBUG)
							System.out
									.println("(unstabilityLevel > (-1)*NUMBER_STABLES_INTERATION_FOR_INCREMENT_STABILITY)  -  "
											+ (unstabilityLevel > (-1)
													* NUMBER_STABLES_INTERATION_FOR_INCREMENT_STABILITY));

						if (unstabilityLevel < (-1)
								* NUMBER_STABLES_INTERATION_FOR_INCREMENT_STABILITY) {
							unstabilityLevel = 0;

							lastRate = (long) (lastRate + periodIncremet);

							if (PropertiesLoaderImpl.DEBUG)
								System.out.println(">>>newBehavourTicker >"
										+ lastRate);
							this.reset(lastRate);
						}

					}
				}
			
		}

	}




}
