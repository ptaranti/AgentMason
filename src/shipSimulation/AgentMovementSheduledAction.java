package shipSimulation;

import agentMason.simulationClock.SimulationClock;
import agentMason.simulationClock.remoteAccess.SimulationTime;

import com.vividsolutions.jts.geom.Coordinate;

import sim.engine.SimState;
import sim.engine.Steppable;
import util.GeometryExtra;
import util.PropertiesLoaderImpl;

public class AgentMovementSheduledAction implements Steppable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	long lasttime = 0;
	long timenow;

	protected static SimulationTime simulationTimeConnection = SimulationClock
			.getInstance();

	@Override
	public void step(SimState state) {

		// System.out.println("pppppppppppp   AgentMovementSheduledAction");

		UnitState unitState = (UnitState) state;

		unitState.agentPosition.setCurseRad(GeometryExtra.getCourseFromToRad(
				unitState.agentPosition.getCoordinates(), unitState.destiny));

		this.timenow = simulationTimeConnection.getSimulationTime();
		double deltaTime = timenow + this.lasttime;

		double deltaTimeHour = deltaTime / 3600000; // (1000 * 60 * 60); // time
													// in hours

		double curseRad = unitState.agentPosition.getCurseRad();
		double deltaSpace = unitState.agentPosition.getSpeed() * deltaTimeHour
				/ 60; // speed in knots

		Coordinate transportVector = new Coordinate();

		if (curseRad <= (Math.PI / 2)) {
			transportVector.y = deltaSpace * Math.cos(curseRad);
			transportVector.x = deltaSpace * Math.sin(curseRad);
		} else if (curseRad <= (Math.PI)) {
			curseRad = Math.PI - curseRad;
			transportVector.y = (-1) * deltaSpace * Math.cos(curseRad);
			transportVector.x = deltaSpace * Math.sin(curseRad);
		} else if (curseRad <= (Math.PI * 3 / 2)) {
			curseRad = (Math.PI * 3 / 2) - curseRad;
			transportVector.y = (-1) * deltaSpace * Math.sin(curseRad);
			transportVector.x = (-1) * deltaSpace * Math.cos(curseRad);
		} else if (curseRad <= (Math.PI * 2)) {
			curseRad = (Math.PI * 2) - curseRad;
			transportVector.y = deltaSpace * Math.cos(curseRad);
			transportVector.x = (-1) * deltaSpace * Math.sin(curseRad);
		}

		Coordinate newPosition = new Coordinate(
				(unitState.agentPosition.getCoordinates().x + transportVector.x),
				(unitState.agentPosition.getCoordinates().y + transportVector.y));

		unitState.agentPosition.setCoordinates(newPosition);

		//new course if outside patrol area
		/*if (newPosition.x >= PropertiesLoaderImpl.EAST_LIMIT
				|| newPosition.x <= PropertiesLoaderImpl.WEST_LIMIT
				|| newPosition.y >= PropertiesLoaderImpl.NORTH_LIMIT
				|| newPosition.y <= PropertiesLoaderImpl.SOUTH_LIMIT) {
			unitState.destiny = GeometryExtra.getRamdonPosition();
			unitState.agentPosition.setCurseRad(GeometryExtra
					.getCourseFromToRad(
							unitState.agentPosition.getCoordinates(),
							unitState.destiny));

		}*/

		 if(newPosition.distance(unitState.destiny) < deltaSpace*2 ){
			unitState.destiny = GeometryExtra.getRamdonPosition();
			unitState.agentPosition.setCurseRad(GeometryExtra
					.getCourseFromToRad(
							unitState.agentPosition.getCoordinates(),
							unitState.destiny));
		}
		
		
		System.out.println(unitState.agentPosition);

		this.lasttime = this.timenow;

	}

}
