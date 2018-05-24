package agentMason.simulationClock.simulationRateControl.controlApproach;

import java.util.LinkedList;
import java.util.Queue;

import agentMason.simulationClock.StatisticCollector;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.core.Agent;

/**
 * @author taranti incluido tratamento brusco de erros alem do limite
 */
public class HandleTimeRateBehaviorLinear3 extends HandleTimeRateBehaviour {

	/**
	 * 
	 */

	private final double MAX_VAR_RATE = 0.05; // percentagem

	private static final long serialVersionUID = 1L;
	// private static boolean usedStrongCorrection = false;
	private static int counterStrongCorrection = 0;
	private static double lastError = 0;
	Queue<Double> periodFIFO = new LinkedList<Double>();
	private double averagePeriod = 0;
	private long stabilityCounter = 0;
	private long contador = 1;

	private long timeChecker = 0;
	private long timeCheckerOld = 0;

	public HandleTimeRateBehaviorLinear3(Agent a, long period) {
		super(a, period);

	}

	@Override
	void errorControl() {
		timeChecker = System.currentTimeMillis();
		/*System.out
				.println("real time in milliseconds since last verification: "
						+ (timeChecker - timeCheckerOld));*/
		timeCheckerOld = timeChecker;

		contador++;

		if (contador <= 2) {

			StatisticCollector.getInstance().reset();

			System.out.println("contador aguardando 3 passe");
			return;
		}
		// vies

		/*System.out.println("\n\nError Control is being performed for the "
				+ this.contador + "th time");

		System.out.println("initial LC is: >> "
				+ simulationClockControl.getSimulationClockRate());

		System.out.println("super.currentStatistics.getMaxErrorInformed() -> "
				+ super.currentStatistics.getMaxErrorInformed());

		System.out
				.println("super.currentStatistics.getNumberOfErrorsGreatherThenTriggerUpperLimit()  -> "
						+ super.currentStatistics
								.getNumberOfErrorsGreatherThenTriggerUpperLimit());

		System.out
				.println("super.currentStatistics.getNumberOfErrorsGreatherThenTriggerPoint()  -> "
						+ super.currentStatistics
								.getNumberOfErrorsGreatherThenTriggerPoint());

		System.out.println("super.currentStatistics.getMaxPeriodInformed = "
				+ super.currentStatistics.getMaxPeriodInformed());  */

		double newRate = simulationClockControl.getSimulationClockRate();

		if (super.currentStatistics.getMaxPeriodInformed() <= 0) {
			System.out
					.println("Nothing to do - waiting an execution - current rate: "
							+ newRate);
			return;
		}

		double vies = super.currentStatistics.getMaxErrorInformed() - lastError;
		System.out.println("The current error vies is " + Math.signum(vies));

		boolean flag = true;

		// LIMITE MAX ULTRAPASSADO

		if (super.currentStatistics.getMaxErrorInformed() >= PropertiesLoaderImpl.ERROR_TRIGGER_AGGRESSIVE_CORRECTION
				&& counterStrongCorrection == 0) {

			System.out
					.println(" Atention ---- MAX LIMIT REACHED ----");

			newRate = simulationClockControl.getSimulationClockRate()
					* (1 - ((super.currentStatistics.getMaxErrorInformed() - PropertiesLoaderImpl.ERROR_TRIGGER_AGGRESSIVE_CORRECTION) / (super.currentStatistics
							.getMaxErrorInformed() * PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE)));

			flag = false;
			// this.usedStrongCorrection = true;
			counterStrongCorrection = PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE;
			this.stabilityCounter = 0;
			// System.out.println("Maximum Error Limit passed ");

		}

		// ERRO DENTRO DO LIMITE
		if (super.currentStatistics.getMaxErrorInformed() < PropertiesLoaderImpl.ERROR_TRIGGER_AGGRESSIVE_CORRECTION
				&& super.currentStatistics.getMaxErrorInformed() >= PropertiesLoaderImpl.ERROR_TRIGGER
				&& flag) {

			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Erro no limite");

			if (this.stabilityCounter < 0)
				this.stabilityCounter--;

			if (this.stabilityCounter >= 0)
				this.stabilityCounter = -1;

			flag = false;

			if (vies > 0) {
				newRate = optimizeSimulationTimeRate((-1)
						* PropertiesLoaderImpl.OPTIMIZATION_RATE_DOWN
						* Math.pow(this.stabilityCounter, 2));
			}
			if (vies < 0) {
				newRate = optimizeSimulationTimeRate((-1)
						* PropertiesLoaderImpl.OPTIMIZATION_RATE_DOWN
						* Math.sqrt((-1) * this.stabilityCounter));
			}
			if (vies == 0) {
				newRate = optimizeSimulationTimeRate(PropertiesLoaderImpl.OPTIMIZATION_RATE_DOWN
						* this.stabilityCounter);

			}
		}

		// ERROS ABAIXO DO LIMITE
		if (super.currentStatistics.getMaxErrorInformed() < PropertiesLoaderImpl.ERROR_TRIGGER) {
			if (this.stabilityCounter < 0)
				this.stabilityCounter = 0;

			this.stabilityCounter++;

			if (PropertiesLoaderImpl.AUTOMATIC_CONTROL) {
				if (this.stabilityCounter > PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE) {
					if (vies > 0)
						newRate = optimizeSimulationTimeRate(PropertiesLoaderImpl.OPTIMIZATION_RATE_UP
								* Math.sqrt(this.stabilityCounter
										- PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE));
					if (vies < 0)
						newRate = optimizeSimulationTimeRate(PropertiesLoaderImpl.OPTIMIZATION_RATE_UP
								* Math.pow(
										this.stabilityCounter
												- PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE,
										2));
					if (vies == 0)
						newRate = optimizeSimulationTimeRate(PropertiesLoaderImpl.OPTIMIZATION_RATE_UP
								* (stabilityCounter - PropertiesLoaderImpl.STABLES_CYCLES_TO_OPTIMIZE));
				}
			}
		}

		// AJUSTANDO RATE
		simulationClockControl.setSimulationClockRate(newRate);
		System.out.println("newRate  " + newRate);

		// AJUSTANDO PERIODO DE VERIFICACAO
		this.newPeriod(newRate);

		// counterStrongCorrection = 0;
		if (counterStrongCorrection > 0) {
			counterStrongCorrection--;
			System.out.println("counterStrongCorrection -> "
					+ counterStrongCorrection);
		}

		// AJUSTANDO LASTERROR PARA PROXIMO CALCULO DE VIES
		lastError = super.currentStatistics.getMaxErrorInformed();

		System.out.println("current stabilityCounter -> "
				+ this.stabilityCounter);

	}

	private double optimizeSimulationTimeRate(double value) {

		System.out
				.println("starting optimizeSimulationTimeRate(double value) with value = "
						+ value);
		// negative counter counter has as consequence a more fast simulation
		double signal = Math.signum(value);
		// double absValue = Math.abs(value);
		double simulationClockRate = 0.001;

		System.out.println("value " + value + " and  MAX_VAR_RATE  "
				+ MAX_VAR_RATE);

		if ((value * value) > (MAX_VAR_RATE * MAX_VAR_RATE)) {
			simulationClockRate = simulationClockControl
					.getSimulationClockRate() * (1 + (signal * (MAX_VAR_RATE)));
			System.out.println("tp1 - adopted maximum variation rate");
		}

		if ((value * value) <= (MAX_VAR_RATE * MAX_VAR_RATE)) {
			simulationClockRate = simulationClockControl
					.getSimulationClockRate() * (1 + value);
			System.out.println("tp2");
		}

		if (simulationClockRate < 0.001) {
			System.out.println("tp3");
			simulationClockRate = 0.001;
		}

		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("DEBUG " + this.getClass()
					+ " new simulation rate = " + simulationClockRate);

		return simulationClockRate;

	}

	private void newPeriod(double newRate) {

		System.out.println("call to method newPeriod(double newRate)");

		if (super.currentStatistics.getMaxPeriodInformed() <= 0) {
			System.out.println("ACHEI!!!!");

			return; // sem nenhum ciclo encerrado
		}

		if (this.periodFIFO.size() < PropertiesLoaderImpl.CYCLES_FOR_HISTORY)
			periodFIFO.add((double) super.currentStatistics
					.getMaxPeriodInformed());
		else {
			this.periodFIFO.remove();
			this.periodFIFO.add((double) super.currentStatistics
					.getMaxPeriodInformed());
		}

		double sum = 0;
		for (double a : this.periodFIFO) {
			sum = sum + a;
		}

		this.averagePeriod = sum / this.periodFIFO.size();

		if (super.currentStatistics.getMaxPeriodInformed() >= this.averagePeriod) {

			this.reset((long) (super.currentStatistics.getMaxPeriodInformed() / newRate));
			System.out.println("Period for verification increasing = "
					+ super.currentStatistics.getMaxPeriodInformed() + " / "
					+ newRate + " = "
					+ (long) super.currentStatistics.getMaxPeriodInformed()
					/ newRate);
		} else {
			this.reset((long) (averagePeriod / newRate));
			System.out.println("Period for verification reducing = "
					+ averagePeriod + " / " + newRate + " = "
					+ (long) averagePeriod / newRate);

		}

		this.reset((long) PropertiesLoaderImpl.TIME_RATE_CONTROL);

		// System.out.println(">>>>  " + this.periodFIFO.toString());

	}

}
