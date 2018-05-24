package shipSimulation;

import agentMason.core.AgentMason;
import agentMason.globalRepresentation.AgentPosition;
import agentMason.simulationClock.SimulationClock;

import com.vividsolutions.jts.geom.Coordinate;

import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import util.Attitude;
import util.Awareness;
import util.Forces;
import util.GeometryExtra;

public class UnitState extends SimState {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	AgentMason agentMason;
	
	// this is the knowledge of the agent about the universe
	public Continuous2D yard = new Continuous2D(1.0, 300, 300);

	Coordinate destiny;
	AgentPosition agentPosition;
	String unitName;
	Forces myForce;
	Awareness myAwareness;
	Attitude myAttitude;
	Double maxSpeed = 20.0/60;
	Double minSpeed = 5.0/60;

	public UnitState(long seed, AgentMason agentMason) {
		super(seed);
		this.unitName = agentMason.getLocalName();
		this.myForce = Forces.UNKNOW;
		this.myAwareness = Awareness.WHITE;
		this.myAttitude = Attitude.NEUTRAL;
		this.destiny = GeometryExtra.getRamdonPosition();
		Coordinate tempCoordinate = GeometryExtra.getRamdonPosition();
		this.agentPosition = new AgentPosition(tempCoordinate, 0, unitName,
				GeometryExtra.getCourseFromToRad(tempCoordinate, destiny),
				minSpeed + (maxSpeed - minSpeed) * Math.random());
		System.out.println(this);
	}

	public void start() {

		super.start();

		// clear the yard
		yard.clear();
		
		//the position is received from the global view
		//yard.setObjectLocation(agentPosition, GeometryExtra
		//		.coordinateToDouble2D(this.agentPosition.getCoordinates()));
		
		
		// clear budies
		// buddies.clear();

		// add the sensor
		SensorSheduledAction sensorSheduledAction = new SensorSheduledAction(
				this);
		// schedule.scheduleRepeating(sensorSheduledAction);
		
		double initialSimulationTime = SimulationClock.getInstance().getSimulationTime();
		schedule.scheduleRepeating(initialSimulationTime, sensorSheduledAction, 60 * 1000);
		
		schedule.scheduleRepeating(new ReceiveMessageSheduledAction(), 60 * 1000);

		// add the actions to be scheduled
		AgentMovementSheduledAction agentMovement = new AgentMovementSheduledAction();
		schedule.scheduleRepeating(initialSimulationTime, agentMovement, 60 * 1000); // one step each
		// 60 sec of
		// simulation
		// time

	}

	public static void main(String[] args) {

		doLoop(UnitState.class, args);
		System.exit(0);
	}

	public void setForces(Forces forces) {
		this.myForce = forces;
	}

	public String toString() {
		return "Model " + unitName + " from the force " + this.myForce
				+ " is saling in wareness " + this.myAwareness + " at positon "
				+ this.agentPosition.getCoordinates() + " with course "
				+ Math.toDegrees(this.agentPosition.getCurseRad());
	}

}
