package agentMason.globalRepresentation;


import agentMason.core.SimulationBehaviour;
import agentMason.simulationClock.SimulationClock;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;


public class SpatialManagerAgent extends Agent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void setup() {



	
		this.addBehaviour( (Behaviour) new VirtualSpaceUpdaterBehaviour(this, PropertiesLoaderImpl.STEPTIME_SPATIAL_UPDATE));

		if (PropertiesLoaderImpl.DEBUG)
			System.out.println(this.getName()
					+ " alive!!!!!!!!!!\nStarting the simulation time");
	}
	
	


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



}