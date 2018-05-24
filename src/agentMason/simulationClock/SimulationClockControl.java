/**
 * 
 */
package agentMason.simulationClock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import agentMason.util.propertiesAccess.PropertiesLoaderImpl;




/**
 * @author taranti
 * 
 */
public class SimulationClockControl {
	
	
private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
private static Lock readLock = rwl.readLock();
private Lock writeLock = rwl.writeLock();

	/**
	 * Rate between simulation clock and real clock (S:R)
	 */
	private double simulationClockRate = PropertiesLoaderImpl.INITIAL_SIMULATION_TIME_RATE;


	/**
	 * Singleton
	 */
	private static SimulationClockControl INSTANCE = new SimulationClockControl();

	private SimulationClockControl() {
	}

	/**
	 * @return Singleton to simulation clock
	 */
	public static SimulationClockControl getInstance() {
		readLock.lock();
		try {
			return INSTANCE;
		} finally {
			readLock.unlock();
		}
	}

	public double getSimulationClockRate() {
		readLock.lock();
		try {
			return simulationClockRate;
		} finally {
			readLock.unlock();
		}
		
	}

	public void setSimulationClockRate(double simulationClockRate) {
		writeLock.lock();
		try {
			this.simulationClockRate = simulationClockRate;
		} finally {
			writeLock.unlock();
		}
		
	}


	



}
