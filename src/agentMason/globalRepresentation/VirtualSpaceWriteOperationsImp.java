package agentMason.globalRepresentation;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.vividsolutions.jts.geom.Coordinate;

public class VirtualSpaceWriteOperationsImp implements VirtualSpaceWriteOperations, VirtualSpaceReadOperations {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
	static Lock readLock = rwl.readLock();
	Lock writeLock = rwl.writeLock();

	Hashtable<String, AgentPosition> spatial2Dstate = new Hashtable<String, AgentPosition>();

	private static VirtualSpaceWriteOperationsImp singleton = new VirtualSpaceWriteOperationsImp();

	public static VirtualSpaceWriteOperationsImp getInstance() {
		readLock.lock();
		try {
			return singleton;
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public void createAgentPosition(AgentPosition agentPosition) {
		writeLock.lock();
		try {
		//updateAgentPosition(agentPosition);
		singleton.spatial2Dstate.put(agentPosition.getAgentId(),
					agentPosition);
		GISAccess.insertAgentPosition(agentPosition);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void updateAgentPosition(AgentPosition agentPosition) {
		writeLock.lock();
		try {
			singleton.spatial2Dstate.put(agentPosition.getAgentId(),
					agentPosition);
			//System.out.println("++++++++++++++++++  >>" + agentPosition );
			GISAccess.updateAgentPosition(agentPosition);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void deleteAgentPosition(AgentPosition agentPosition) {
		writeLock.lock();
		try {
			singleton.spatial2Dstate.remove(agentPosition.getAgentId());
			GISAccess.deleteAgentPosition(agentPosition);
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

	public Hashtable<String, AgentPosition> getSurfacePicture(
			long currentSimulationTime) {

		writeLock.lock();
		try {
			Set<String> agentIDs = this.spatial2Dstate.keySet();
			Hashtable<String, AgentPosition> currentPictureSpatial2Dstate = new Hashtable<String, AgentPosition>();
			for (String agentID : agentIDs) {
				AgentPosition currentPosition = this.spatial2Dstate
						.get(agentID).clone();
				currentPosition.updateCoordinates(currentSimulationTime);
				currentPictureSpatial2Dstate.put(currentPosition.getAgentId(),
						currentPosition);
			}
			return currentPictureSpatial2Dstate;
		} finally {
			writeLock.unlock();
		}

	}

	

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
