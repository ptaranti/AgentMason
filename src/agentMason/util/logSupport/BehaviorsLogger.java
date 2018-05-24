package agentMason.util.logSupport;

import jade.core.Agent;
import jade.core.behaviours.Behaviour;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.SimulationClockControl;

public class BehaviorsLogger {

	protected SimulationClock simulationClock = SimulationClock.getInstance();
	protected SimulationClockControl simulationClockControl = SimulationClockControl.getInstance();


	private File fileRegister;
	private FileWriter writerRegister;
	private PrintWriter outRegister;

	private static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	private static Lock readLock = rwl.readLock();
	private Lock writeLock = rwl.writeLock();

	private static BehaviorsLogger INSTANCE = new BehaviorsLogger();

	public static BehaviorsLogger getInstance() {
		readLock.lock();
		try {
			return INSTANCE;
		} finally {
			readLock.unlock();
		}
	}

	private BehaviorsLogger() {
		if (fileRegister == null)
			fileRegister = new File("BehaviourRegister.csv");
		if (writerRegister == null)
			try {
				writerRegister = new FileWriter("BehaviourRegister.csv", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("erro na abertura de BehaviourRegister.csv");
				e.printStackTrace();
			}

		if (outRegister == null) {
			outRegister = new PrintWriter(writerRegister, true);
		}
	}

	public void executeLogBehaviour(String agentLocalName, Behaviour b, String event,
			long realTime, double now, double error, double delay) {
		writeLock.lock();
		//counter++;
		//if (counter % logStep == 0) {
			try {
				outRegister.println("\"" + agentLocalName + "\";\""
						+ b.getBehaviourName() + "\";" + realTime + ";" + now
						+ ";" + error + ";" + delay + ";");
			} finally {
				writeLock.unlock();
			}
	//	}
	}

}
