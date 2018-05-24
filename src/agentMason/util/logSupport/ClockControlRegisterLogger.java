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

public class ClockControlRegisterLogger {

	protected SimulationClock simulationClock = SimulationClock.getInstance();
	protected SimulationClockControl simulationClockControl = SimulationClockControl.getInstance();



	private File fileRegister;
	private FileWriter writerRegister;
	private PrintWriter outRegister;

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static Lock readLock = rwl.readLock();
	private Lock writeLock = rwl.writeLock();

	private static ClockControlRegisterLogger  INSTANCE = new ClockControlRegisterLogger();

	public static ClockControlRegisterLogger getInstance()  {
		readLock.lock();
		try {
			return INSTANCE;
		} finally {
			readLock.unlock();
		}
	}

	private ClockControlRegisterLogger() {
		if (fileRegister == null)
			fileRegister = new File("ClockControlRegister.csv");
		if (writerRegister == null)
			try {
				writerRegister = new FileWriter("ClockControlRegister.csv",
						true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out
				.println("erro na abertura de ClockControlRegister.csv");
				e.printStackTrace();
			}

			if (outRegister == null) {
				outRegister = new PrintWriter(writerRegister, true);
			}
	}

	public void executeLogClockControl(double getMaxErrorInformed) {
		writeLock.lock();
		try {
			outRegister.println(System.currentTimeMillis() + "; "
					+ simulationClock.getSimulationTime() + ";"
					+ simulationClockControl.getSimulationClockRate() + ";"
					+ getMaxErrorInformed + ";");
		}  finally {
			writeLock.unlock();
		}
	}

}
