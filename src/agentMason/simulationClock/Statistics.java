package agentMason.simulationClock;

import java.io.Serializable;

public class Statistics implements Serializable {

	protected long maxPeriodInformed;
	protected double maxErrorInformed;
	protected double errorSum;
	protected long numberOfInformations;
	protected long numberOfErrorsGreatherThenTriggerPoint;
	protected long numberOfErrorsGreatherThenTriggerUpperLimit;


	
	public Statistics(){
		
	}
	
	public Statistics(long maxPeriodInformed, double maxErrorInformed,
			double errorSum, long numberOfInformations,
			long numberOfErrorsGreatherThenTriggerPoint,
			long numberOfErrorsGreatherThenTriggerUpperLimit) {
		super();
		this.maxPeriodInformed = maxPeriodInformed;
		this.maxErrorInformed = maxErrorInformed;
		this.errorSum = errorSum;
		this.numberOfInformations = numberOfInformations;
		this.numberOfErrorsGreatherThenTriggerPoint = numberOfErrorsGreatherThenTriggerPoint;
		this.numberOfErrorsGreatherThenTriggerUpperLimit = numberOfErrorsGreatherThenTriggerUpperLimit;
		
	}


	public long getMaxPeriodInformed() {
		return maxPeriodInformed;
	}


	public double getMaxErrorInformed() {
		return maxErrorInformed;
	}


	public double getErrorSum() {
		return errorSum;
	}


	public long getNumberOfInformations() {
		return numberOfInformations;
	}


	public double getNumberOfErrorsGreatherThenTriggerPoint() {
		return numberOfErrorsGreatherThenTriggerPoint;
	}


	public double getNumberOfErrorsGreatherThenTriggerUpperLimit() {
		return numberOfErrorsGreatherThenTriggerUpperLimit;
	}

}