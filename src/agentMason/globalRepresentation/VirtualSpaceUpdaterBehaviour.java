package agentMason.globalRepresentation;

import jade.core.Agent;
import agentMason.core.SimulationBehaviour;
import agentMason.simulationClock.SimulationClock;


public class VirtualSpaceUpdaterBehaviour extends SimulationBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	VirtualSpaceWriteOperations virtualSpaceWriteOperations = VirtualSpaceWriteOperationsImp.getInstance();
	VirtualSpaceReadOperationsImp virtualSpaceEstimated = VirtualSpaceReadOperationsImp.getInstance();
	SimulationClock simulationTimeConnection = SimulationClock.getInstance();
	

	public VirtualSpaceUpdaterBehaviour(Agent a, long simulatedTimePeriod) {
		super(a);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onTick() {
		
		virtualSpaceEstimated.updateVirtualSpaceEstimated(this.simulationTimeConnection.getSimulationTime());
				
	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}		
}


	
