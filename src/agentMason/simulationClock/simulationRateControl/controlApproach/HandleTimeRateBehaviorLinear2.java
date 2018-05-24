package agentMason.simulationClock.simulationRateControl.controlApproach;


import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.core.Agent;

/**
 * @author taranti incluido tratamento brusco de erros alem do limite
 */
public class HandleTimeRateBehaviorLinear2 extends HandleTimeRateBehaviour {

	/**
	 * 
	 */
	
	private final double MAX_VAR_RATE = 0.5;
	private final double RISE_REDICTION = 0.5;
	private static final long serialVersionUID = 1L;
	// private static boolean usedStrongCorrection = false;
	private static int counterStrongCorrection = 0;
	private static double lastError = 0;
	private static double periodOld = 0;
	private static double periodOldOld = 0;
	private static double averagePeriod = 0;
	private long stabilityCounter = 0;
	private long contador = 1;

	public HandleTimeRateBehaviorLinear2(Agent a, long period) {
		super(a, period);

	}

	@Override
	void errorControl() {
		contador++;
		// vies
		System.out.println("\n\nInicio de nova VRF>>>>>>>");
		System.out.println("contador = " + this.contador);
		System.out.println("super.currentStatistics.getMaxErrorInformed() -> "
				+ super.currentStatistics.getMaxErrorInformed());
		System.out
				.println("super.currentStatistics.getNumberOfErrorsGreatherThenTriggerUpperLimit()  -> "
						+ super.currentStatistics
								.getNumberOfErrorsGreatherThenTriggerUpperLimit());
		System.out.println("super.currentStatistics.getMaxPeriodInformed = "
				+ super.currentStatistics.getMaxPeriodInformed());
		System.out.println(">>>> super.stabilityCounter -> "
				+ this.stabilityCounter);

		if (super.currentStatistics.getMaxPeriodInformed() <= 0) {
			System.out
					.println("Nenhum agente completou ciclo ainda - aguardando");
			return;
		}

		double vies = super.currentStatistics.getMaxErrorInformed() - lastError;

		double newRate = simulationClockControl.getSimulationClockRate();
		boolean flag = true;

		// ultrapassou limite - executado no max um ciclo a cada conjunto igual
		// ao necessario para iniciar otimizacao
		// if (!this.usedStrongCorrection &&
		if (super.currentStatistics
				.getNumberOfErrorsGreatherThenTriggerUpperLimit() > 0
				&& counterStrongCorrection == 0) {
			newRate = simulationClockControl.getSimulationClockRate()
					/ (super.currentStatistics.getMaxErrorInformed()
							- PropertiesLoaderImpl.ERROR_TRIGGER + 1);
			if (newRate < (optimizeSimulationTimeRate(Math
					.sqrt(this.stabilityCounter - 1))))
				newRate = optimizeSimulationTimeRate(Math
						.sqrt(this.stabilityCounter - 1));
			flag = false;
			// this.usedStrongCorrection = true;
			counterStrongCorrection = PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE;
			this.stabilityCounter = this.stabilityCounter - 1;
			System.out.println("TP1 -passou limite max de erro");

		}
		// com erro mais no limite
		if (super.currentStatistics.getNumberOfErrorsGreatherThenTriggerPoint() > 0
				&& flag) {
			if (this.stabilityCounter >= 0) {
				System.out.println("TP2 "
						+ this.stabilityCounter
						+ " "
						+ this.currentStatistics
								.getNumberOfErrorsGreatherThenTriggerPoint());
				this.stabilityCounter = -1;
			}

			if (this.stabilityCounter < 0) {
				this.stabilityCounter = this.stabilityCounter - 1;
				System.out.println("TP3 "
						+ this.stabilityCounter
						+ " "
						+ this.currentStatistics
								.getNumberOfErrorsGreatherThenTriggerPoint());
			}

			flag = false;

			// }

			if (vies > 0) {
				newRate = optimizeSimulationTimeRate((-1)
						* Math.pow(this.stabilityCounter, 2));
				System.out.println(newRate + " (vies > 0)");
			}
			if (vies < 0) {
				newRate = optimizeSimulationTimeRate((-1)
						* Math.sqrt((-1) * this.stabilityCounter));
				System.out.println(newRate + "  (vies < 0)");
			}
			if (vies == 0) {
				newRate = optimizeSimulationTimeRate(this.stabilityCounter);
				System.out.println(newRate + " (vies == 0) ");
				
			}
System.out.println("TP4");
		}

		if (super.currentStatistics.getNumberOfErrorsGreatherThenTriggerPoint() == 0) {
			if (this.stabilityCounter < 0)
				this.stabilityCounter = 0;
			this.stabilityCounter = this.stabilityCounter + 1;
			System.out.println("PropertiesLoaderImpl.AUTOMATIC_CONTROL = "
					+ PropertiesLoaderImpl.AUTOMATIC_CONTROL);
			System.out.println("super.stabilityCounter = "
					+ this.stabilityCounter);
			if (PropertiesLoaderImpl.AUTOMATIC_CONTROL) {
				if (this.stabilityCounter > PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE) {
					if (vies > 0)
						newRate = optimizeSimulationTimeRate(RISE_REDICTION* Math
								.sqrt(this.stabilityCounter-PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE));
					if (vies < 0)
						newRate = optimizeSimulationTimeRate(RISE_REDICTION* Math.pow(this.stabilityCounter - PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE, 2));
					if (vies == 0)
						newRate = optimizeSimulationTimeRate(RISE_REDICTION*(stabilityCounter - PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE));
					System.out.println("TP5");
				}
			}
		}

		simulationClockControl.setSimulationClockRate(newRate);
		System.out.println("newRate  " + newRate);

		System.out.println("super.currentStatistics.getMaxPeriodInformed = "
				+ super.currentStatistics.getMaxPeriodInformed());

		if (super.currentStatistics.getMaxPeriodInformed() >= averagePeriod) {
			this.reset((long) (super.currentStatistics.getMaxPeriodInformed() / newRate));
			System.out.println("TP-7 new period for verification = "
					+ super.currentStatistics.getMaxPeriodInformed() + " / "
					+ newRate + " = "
					+ (long) super.currentStatistics.getMaxPeriodInformed()
					/ newRate);
			averagePeriod = (super.currentStatistics.getMaxPeriodInformed()
					+ periodOld + periodOldOld) / 3;
			periodOldOld = periodOld;
			periodOld = super.currentStatistics.getMaxPeriodInformed();
		} else {
			if (super.currentStatistics.getMaxPeriodInformed() > 0) {
				averagePeriod = (super.currentStatistics.getMaxPeriodInformed()
						+ periodOld + periodOldOld) / 3;
				this.reset((long) (averagePeriod / newRate));
				System.out.println("TP-8 new period for verification = "
						+ averagePeriod + " / " + newRate + " = "
						+ (long) averagePeriod / newRate);
				periodOldOld = periodOld;
				periodOld = super.currentStatistics.getMaxPeriodInformed();
			}
		}

		// counterStrongCorrection = 0;
		if (counterStrongCorrection > 0) {
			counterStrongCorrection--;
			System.out.println("counterStrongCorrection -> "
					+ counterStrongCorrection);
		}
		lastError = super.currentStatistics.getMaxErrorInformed();
		System.out.println("lastError -> " + lastError);
	}

	private double optimizeSimulationTimeRate(double value) {
		// negative counter counter has as consequence a more fast simulation
		double signal = Math.signum(value);
		double absValue = Math.abs(value);
		double simulationClockRate = 0.001;;
		if ((absValue * PropertiesLoaderImpl.OPTIMIZATION_RATE) > MAX_VAR_RATE){
			simulationClockRate = simulationClockControl.getSimulationClockRate() * (1 + (signal*(MAX_VAR_RATE * PropertiesLoaderImpl.OPTIMIZATION_RATE)));
		}
		if  ((absValue * PropertiesLoaderImpl.OPTIMIZATION_RATE) <= MAX_VAR_RATE){
			simulationClockRate = simulationClockControl.getSimulationClockRate() * (1 + ((value * PropertiesLoaderImpl.OPTIMIZATION_RATE)));
		}
		
		if (simulationClockRate < 0.001)
			simulationClockRate = 0.001;
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("DEBUG " + this.getClass()
					+ " new simulation rate = " + simulationClockRate);
		return simulationClockRate;

	}

}
