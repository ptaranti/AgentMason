package agentMason.simulationClock;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import agentMason.simulationClock.remoteAccess.SimulationClockConnectorFactoryProducer;
import agentMason.simulationClock.remoteAccess.SimulationTime;
import agentMason.simulationClock.remoteAccess.SimulationTimeDTO;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class SimulationClock implements SimulationTime {

	/**
	 * Counter for simulation time, in milliseconds
	 */

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static Lock readLock = rwl.readLock();
	private Lock writeLock = rwl.writeLock();

	// private SimulationClockControl simulationClockControl;
	private long simulationTime;
	private boolean killSimulation;
	private boolean simulationStarted;
	private double simualtionClockRate;

	/**
	 * Singleton
	 */
	private static SimulationClock INSTANCE;

	private SimulationClock() {


		 this.simulationTime = 1;

	}

	/**
	 * @return Singleton to simulation clock
	 * @throws RemoteException
	 */
	public static SimulationClock getInstance() {
		readLock.lock();
		try {
			if (INSTANCE != null)
				return INSTANCE;
			else {
				INSTANCE = new SimulationClock();
				return INSTANCE;
			}
		} finally {
			readLock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agentMason.simulationClock.SimulationClock#getSimulationTime()
	 */
	public long getSimulationTime() {

		readLock.lock();
		try {
			//System.out.println("SimulationTime asked localy: " + this.simulationTime + " rate:" +simualtionClockRate);
			return this.simulationTime;

		} finally {
			readLock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agentMason.simulationClock.SimulationClock#setSimulationTime(long)
	 */
	public void setSimulationTime(long simulationTime) {

		writeLock.lock();
		try {

			this.simulationTime = simulationTime;

		} finally {
			writeLock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * agentMason.simulationClock.SimulationClock#setKillSimulation(boolean)
	 */
	public void setKillSimulation(boolean killSimulation) {
		writeLock.lock();
		try {

			this.killSimulation = killSimulation;
		} finally {
			writeLock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agentMason.simulationClock.SimulationClock#isKillSimulation()
	 */
	public boolean isKillSimulation() {
		readLock.lock();
		try {
			return killSimulation;
		} finally {
			readLock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * agentMason.simulationClock.SimulationClock#setSimulationStarted(boolean)
	 */
	public void setSimulationStarted(boolean simulationStarted) {

		{
			writeLock.lock();
			try {
				StatisticCollector.getInstance().reset();
				this.simulationStarted = simulationStarted;
			} finally {
				writeLock.unlock();
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agentMason.simulationClock.SimulationClock#getSimulationStarted()
	 */
	public boolean isSimulationStarted() {

		{
			readLock.lock();
			try {
				return this.simulationStarted;
			} finally {
				readLock.unlock();
			}
		}

	}

	public SimulationTimeDTO getSimulationTimeDTO(Statistics otherNodeStatistics) {
		StatisticCollector.getInstance().insertDatafromExternalNode(otherNodeStatistics);
		return new SimulationTimeDTO(getSimulationTime(),
				getSimualtionClockRate(), isKillSimulation());
	}

	public double getSimualtionClockRate() {
		readLock.lock();
		try {
			return this.simualtionClockRate;
		} finally {
			readLock.unlock();
		}
	}

	public void setSimualtionClockRate(double simualtionClockRate) {

		{
			writeLock.lock();
			try {
				this.simualtionClockRate = simualtionClockRate;
			} finally {
				writeLock.unlock();
			}
		}

	}



}
