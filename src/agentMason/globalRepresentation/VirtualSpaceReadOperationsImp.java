package agentMason.globalRepresentation;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.vividsolutions.jts.geom.Coordinate;

public class VirtualSpaceReadOperationsImp implements
		VirtualSpaceReadOperations {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	long currentSimulationTime;

	static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	static Lock readLock = rwl.readLock();
	Lock writeLock = rwl.writeLock();

	Hashtable<String, AgentPosition> spatial2Dstate = new Hashtable<String, AgentPosition>();


	private static VirtualSpaceReadOperationsImp singleton = new VirtualSpaceReadOperationsImp();

	public static VirtualSpaceReadOperationsImp getInstance() {
		readLock.lock();
		try {
			return singleton;
		} finally {
			readLock.unlock();
		}
	}

	private VirtualSpaceReadOperationsImp() {
		super();
		writeLock.lock();
		try {
			this.spatial2Dstate = VirtualSpaceWriteOperationsImp.getInstance().getSurfacePicture();
		} finally {
			writeLock.unlock();
		}
	}



	public void updateVirtualSpaceEstimated(long currentSimulationTime) {
		writeLock.lock();
		try {
			this.spatial2Dstate = VirtualSpaceWriteOperationsImp.getInstance().getSurfacePicture(currentSimulationTime);
		} finally {
			writeLock.unlock();
		}
	}

	
	
	@Override
	public AgentPosition getAgentPosition(String agentID) {
		readLock.lock();
		try {
			return singleton.spatial2Dstate.get(agentID);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public Hashtable<String, AgentPosition> getAgentsIntoRange(
			AgentPosition agentPosition, double range) {
		readLock.lock();
		try {
			Set<String> agentIDs = this.spatial2Dstate.keySet();
			//System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRR"+ agentIDs);
			Hashtable<String, AgentPosition> agentsIntoRange = new Hashtable<String, AgentPosition>();
			Coordinate centerOfSearch = agentPosition.getCoordinates();
			for (String agentID : agentIDs) {
				AgentPosition otherAgentPosition = this.spatial2Dstate
						.get(agentID);
				if (centerOfSearch
						.distance(otherAgentPosition.getCoordinates()) <= range)
					agentsIntoRange.put(agentID, otherAgentPosition);
			}
			return agentsIntoRange;
		} finally {
			readLock.unlock();
		}
	}

	
	
	
//estimated positions considering speed and course
	public Hashtable<String, AgentPosition> getSurfacePicture() {

		readLock.lock();
		try {
			Set<String> agentIDs = this.spatial2Dstate.keySet();
			Hashtable<String, AgentPosition> lastInformedSurfacePicture = new Hashtable<String, AgentPosition>();
			for (String agentID : agentIDs) {
				AgentPosition currentPosition = this.spatial2Dstate
						.get(agentID).clone();
				lastInformedSurfacePicture.put(currentPosition.getAgentId(),
						currentPosition);
			}
			return lastInformedSurfacePicture;
		} finally {
			readLock.unlock();
		}

	}
	
	
	
	public String toString() {
		String string = new String();
		readLock.lock();
		try {
			Enumeration<String> nomesAvatares = this.spatial2Dstate.keys();
			while (nomesAvatares.hasMoreElements()) {
				String key = nomesAvatares.nextElement();
				string = string
						+ ("agent " + key + " spatial state = "
								+ spatial2Dstate.get(key).toString() + "\n");
			}
		} finally {
			readLock.unlock();
		}
		return string;

	}




}
