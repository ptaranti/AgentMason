
package agentMason.util.logSupport;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



import java.rmi.RemoteException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.SimulationClockControl;

public class StatisticsLogger {

	protected SimulationClock simulationClock = SimulationClock.getInstance();
	protected SimulationClockControl simulationClockControl = SimulationClockControl.getInstance();


	private File fileRegister;
	private FileWriter writerRegister;
	private PrintWriter outRegister;

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static Lock readLock = rwl.readLock();
	private Lock writeLock = rwl.writeLock();

	private static StatisticsLogger  INSTANCE = new StatisticsLogger();

	public static StatisticsLogger getInstance()  {
		readLock.lock();
		try {
			return INSTANCE;
		} finally {
			readLock.unlock();
		}
	}

	private StatisticsLogger() {
		if (fileRegister == null)
			fileRegister = new File("Statistics.csv");
		if (writerRegister == null)
			try {
				writerRegister = new FileWriter("Statistics.csv",
						true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out
				.println("erro na abertura de Statistics.csv");
				e.printStackTrace();
			}

			if (outRegister == null) {
				outRegister = new PrintWriter(writerRegister, true);
			}
	}


	
	public void logStatistics(long maxPeriodInformed, double maxErrorInformed,
			double errorSum, long numberOfInformations,
			long numberOfErrorsGreatherThenTriggerPoint,
			long numberOfErrorsGreatherThenTriggerUpperLimit) {
		writeLock.lock();
		try {
			outRegister.println(System.currentTimeMillis() + "; "
					+ SimulationClock.getInstance().getSimulationTime()
					+ "; "
					+ SimulationClockControl.getInstance().getSimulationClockRate()
					+ "; " + maxPeriodInformed + "; " + maxErrorInformed + "; "
					+ errorSum + "; " + numberOfInformations + "; "
					+ numberOfErrorsGreatherThenTriggerPoint + "; "
					+ numberOfErrorsGreatherThenTriggerUpperLimit);

		} finally {
			writeLock.unlock();
		}
	}


}
