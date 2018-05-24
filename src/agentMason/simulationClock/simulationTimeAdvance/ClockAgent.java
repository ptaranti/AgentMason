


package agentMason.simulationClock.simulationTimeAdvance;

import java.rmi.RemoteException;

import agentMason.simulationClock.SimulationClock;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.core.Agent;


public class ClockAgent extends Agent {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	long TimeRateControl = (long)(PropertiesLoaderImpl.TIME_RATE_CONTROL*PropertiesLoaderImpl.ERROR_TRIGGER)/2;
	

	//private ContentManager manager = (ContentManager) getContentManager();
	// This agent "speaks" the SL language
	//private Codec codec = new SLCodec();

	// This agent "knows" the PlatDominiumOntology()
	// private Ontology ontology = PlatDominiumOntology.getInstance();

	protected void setup() {
		


		if(PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE)addBehaviour(new HandleClockBehaviour(this, TimeRateControl));
		else addBehaviour(new SlaveNodeClockBehaviour(this, TimeRateControl));
		
		if(PropertiesLoaderImpl.DEBUG) System.out.println(this.getName() +" alive!!!!!!!!!!\nStarting the simulation time");
		
	
			SimulationClock.getInstance().setSimulationStarted(true);
	
	}

}