package shipSimulation;

import agentMason.core.AgentMason;

public class ShipAgent extends AgentMason{
	
	

	public ShipAgent( ){
		
		super();
		
	}
	
	protected void setup() {
		super.setup();
	
		
	}

	@Override
	protected void modelStateLoader() {
		state = new UnitState(System.currentTimeMillis(), this);
		System.out.println(this.state.getClass().getName());
		
	}
	
}
