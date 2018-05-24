package shipSimulation;

import agentMason.core.AgentMason;
import agentMason.simulationClock.SimulationClock;
import util.Attitude;
import util.Awareness;
import util.Forces;

public class SubmarineState extends UnitState {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int deepFeet = 200;
	int torpedo = 16;
	double torpedoRange = 60; //(distance in  NM)
	double maxSpeedOnDeeep = 10.0/60;
	

	public SubmarineState(long seed, AgentMason agentMason) {
		super(seed, agentMason);
		this.maxSpeed = 18.0;
		this.minSpeed = 4.0;
		this.myForce = Forces.RED;
		this.myAttitude = Attitude.ATTACK;
		this.myAwareness = Awareness.YELLOW;
		System.out.println(this);
		
		
	}


	public void start(){
		
		super.start();
		
		double initialSimulationTime = SimulationClock.getInstance().getSimulationTime();
		
		//add the atack
		SubAttackAction subAttackAction = new SubAttackAction();
		schedule.scheduleRepeating(initialSimulationTime, subAttackAction, 60*1000);
		//schedule.reset();
		
	    System.out.println("STARTING SUBMARINE");
	    System.out.println(schedule.toString());
}
	
}
