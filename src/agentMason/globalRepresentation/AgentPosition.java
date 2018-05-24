package agentMason.globalRepresentation;

import java.io.Serializable;

import com.vividsolutions.jts.geom.Coordinate;

public class AgentPosition implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	Coordinate coordinates;
	long simulationTime;
	String agentId;
	double courseRad;
	double speed;

	public AgentPosition(Coordinate coordinates, long simulationTime,
			String agentId, double courseRad, double speed) {
		super();
		this.coordinates = coordinates;
		this.simulationTime = simulationTime;
		this.agentId = agentId;
		this.courseRad = courseRad;
		this.speed = speed;
	}

	
	public AgentPosition clone(){
		
		return (new AgentPosition(new Coordinate(this.coordinates.x,this.coordinates.y), this.simulationTime, new String(this.agentId), this.courseRad, this.speed));
		
	}
	
	
	public Coordinate getCoordinates() {
		return coordinates;
	}

	public void updateCoordinates(long newSimulationTime) {
		
		long deltaTimeSeconds = (newSimulationTime - this.simulationTime);
		if (deltaTimeSeconds <= 0)
			return; // no time update and avoiding 0.0 divisor
		double deltatimeHours = deltaTimeSeconds/3600000;
		this.simulationTime = newSimulationTime;

		double deltaSpace = (this.speed) * deltatimeHours; // speed in knots
		
		Coordinate transportVector = new Coordinate();

		if (this.courseRad <= (Math.PI / 2)) {
			transportVector.y = deltaSpace * Math.cos(this.courseRad);
			transportVector.x = deltaSpace * Math.sin(this.courseRad);
		} else if (this.courseRad <= (Math.PI)) {
			this.courseRad = Math.PI - this.courseRad;
			transportVector.y = (-1) * deltaSpace * Math.cos(this.courseRad);
			transportVector.x = deltaSpace * Math.sin(this.courseRad);
		} else if (this.courseRad <= (Math.PI * 3 / 2)) {
			this.courseRad = (Math.PI * 3 / 2) - this.courseRad;
			transportVector.y = (-1) * deltaSpace * Math.sin(this.courseRad);
			transportVector.x = (-1) * deltaSpace * Math.cos(this.courseRad);
		} else if (this.courseRad <= (Math.PI * 2)) {
			this.courseRad = (Math.PI * 2) - this.courseRad;
			transportVector.y = deltaSpace * Math.cos(this.courseRad);
			transportVector.x = (-1) * deltaSpace * Math.sin(this.courseRad);
		}

		this.coordinates = new Coordinate(coordinates.x + transportVector.x, coordinates.y + transportVector.y);

	}

	public Coordinate getUpdatedCoordinates(long newSimulationTime) {

		updateCoordinates(newSimulationTime);
		return this.coordinates;

	}

	public void setCoordinates(Coordinate coordinates) {
		this.coordinates = coordinates;
	}

	public long getSimulationTime() {
		return simulationTime;
	}

	public void setSimulationTime(long simulationTime) {
		this.simulationTime = simulationTime;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public double getCurseRad() {
		return courseRad;
	}

	public void setCurseRad(double course) {
		this.courseRad = course;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public String toString(){
		return 
		this.agentId + " >> simulationTime:" +simulationTime+ " position:" + this.coordinates + " course:" + Math.toDegrees(courseRad)  + " speed:" + speed;
		
	
	}

}
