package shipSimulation;

import agentMason.core.AgentMason;

public class SubmarineAgent extends AgentMason {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SubmarineAgent(){
		super();

	}
	
	protected void setup() {
		super.setup();

		
	}

	@Override
	protected void modelStateLoader() {
		this.state = new SubmarineState(System.currentTimeMillis(), this);
		System.out.println(this.state.getClass().getName());
		
	}

}
