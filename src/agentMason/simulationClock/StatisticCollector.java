package agentMason.simulationClock;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import agentMason.simulationClock.remoteAccess.TardinessCollector;
import agentMason.util.logSupport.StatisticsLogger;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;



/**
 * @author taranti Statistic is a class that only colect information to be used
 *         in time control activity
 */
public class StatisticCollector implements TardinessCollector {

	private double triggerPoint;
	private Statistics statistics;
	private double triggerUpperLimit;

	private static StatisticCollector statisticCollector;

	private File fileRegister;
	private FileWriter writerRegister;
	private PrintWriter outRegister;

	private StatisticCollector()  {
		this.statistics = new Statistics();
		this.statistics.maxPeriodInformed = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		this.statistics.maxErrorInformed = 0;
		this.statistics.errorSum = 0;
		this.statistics.numberOfInformations = 0;
		this.statistics.numberOfErrorsGreatherThenTriggerPoint = 0;
		this.statistics.numberOfErrorsGreatherThenTriggerUpperLimit = 0;
		this.triggerPoint = PropertiesLoaderImpl.ERROR_TRIGGER;
		this.triggerUpperLimit = PropertiesLoaderImpl.MAX_ERROR_TRIGGER;
	}

	public static StatisticCollector getInstance() {
		if (statisticCollector == null)	statisticCollector = new StatisticCollector();
			
		return statisticCollector;
	}

	
	public boolean sendTardiness(double tardiness) {

		this.statistics.maxPeriodInformed  = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		if (this.statistics.maxErrorInformed < tardiness)
			this.statistics.maxErrorInformed = tardiness;
		this.statistics.errorSum = this.statistics.errorSum + tardiness;
		this.statistics.numberOfInformations++;
		if (this.triggerPoint < tardiness)
			this.statistics.numberOfErrorsGreatherThenTriggerPoint++;
		if (this.triggerUpperLimit < tardiness)
			this.statistics.numberOfErrorsGreatherThenTriggerUpperLimit++;
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("data inserted  " + this.statistics.maxPeriodInformed + " " + tardiness);
		return true;
	}
	
	
	public void insertData(long period, double error) {

		this.statistics.maxPeriodInformed  = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		if (this.statistics.maxErrorInformed < error)
			this.statistics.maxErrorInformed = error;
		this.statistics.errorSum = this.statistics.errorSum + error;
		this.statistics.numberOfInformations++;
		if (this.triggerPoint < error)
			this.statistics.numberOfErrorsGreatherThenTriggerPoint++;
		if (this.triggerUpperLimit < error)
			this.statistics.numberOfErrorsGreatherThenTriggerUpperLimit++;
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("data inserted  " + period + " " + error);
	}

	/* (non-Javadoc)
	 * @see agentMason.simulationClock.StatisticCollector#insertData(double, boolean)
	 */
	public void insertData(double error, boolean isPartOfSimulationModel ) {

		this.statistics.maxPeriodInformed  = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		
		if (this.statistics.maxErrorInformed < error)
			this.statistics.maxErrorInformed = error;
		this.statistics.errorSum = this.statistics.errorSum + error;
		this.statistics.numberOfInformations++;
		if (this.triggerPoint < error)
			this.statistics.numberOfErrorsGreatherThenTriggerPoint++;
		if (this.triggerUpperLimit < error && isPartOfSimulationModel)
			this.statistics.numberOfErrorsGreatherThenTriggerUpperLimit++;
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("error inserted  " +  error);
	}
	
	/* (non-Javadoc)
	 * @see agentMason.simulationClock.StatisticCollector#recoverData()
	 */
	public Statistics recoverData() {
		Statistics statistics = new Statistics(this.statistics.maxPeriodInformed,
				this.statistics.maxErrorInformed, this.statistics.errorSum,
				this.statistics.numberOfInformations,
				this.statistics.numberOfErrorsGreatherThenTriggerPoint,
				this.statistics.numberOfErrorsGreatherThenTriggerUpperLimit);

		if (PropertiesLoaderImpl.FILE_LOG)
			StatisticsLogger.getInstance().logStatistics(this.statistics.maxPeriodInformed,
					this.statistics.maxErrorInformed, this.statistics.errorSum,
					this.statistics.numberOfInformations,
					this.statistics.numberOfErrorsGreatherThenTriggerPoint,
					this.statistics.numberOfErrorsGreatherThenTriggerUpperLimit);
		this.reset();

		return statistics;
	}

	/* (non-Javadoc)
	 * @see agentMason.simulationClock.StatisticCollector#reset()
	 */
	public void reset() {
		this.statistics.maxPeriodInformed  = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		this.statistics.maxErrorInformed = 0;
		this.statistics.errorSum = 0;
		this.statistics.numberOfInformations = 0;
		this.statistics.numberOfErrorsGreatherThenTriggerPoint = 0;
		this.statistics.numberOfErrorsGreatherThenTriggerUpperLimit = 0;

	}

	

	/* (non-Javadoc)
	 * @see agentMason.simulationClock.StatisticCollector#insertData(double, boolean)
	 */
	public void insertDatafromExternalNode(Statistics otherNodeStatistics) {

		this.statistics.maxPeriodInformed  = 1000 * (long) PropertiesLoaderImpl.DEFAULT_SLICE_TIME;
		
		if (this.statistics.maxErrorInformed < otherNodeStatistics.maxErrorInformed)
			this.statistics.maxErrorInformed = otherNodeStatistics.maxErrorInformed;
		this.statistics.errorSum = this.statistics.errorSum + otherNodeStatistics.errorSum;
		this.statistics.numberOfInformations = this.statistics.numberOfInformations + otherNodeStatistics.numberOfInformations;
		this.statistics.numberOfErrorsGreatherThenTriggerPoint = this.statistics.numberOfErrorsGreatherThenTriggerPoint + otherNodeStatistics.numberOfErrorsGreatherThenTriggerPoint;
		this.statistics.numberOfErrorsGreatherThenTriggerUpperLimit = this.statistics.numberOfErrorsGreatherThenTriggerUpperLimit + otherNodeStatistics.numberOfErrorsGreatherThenTriggerUpperLimit;
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("otherNodeStatistics:  \n" +  otherNodeStatistics);
	}


}


