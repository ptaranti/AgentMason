package agentMason.simulationClock.simulationRateControl;


import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import jade.content.ContentManager;
import jade.content.lang.Codec;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;

public class TimeHandlerAgent extends Agent {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	long timeRateControl = PropertiesLoaderImpl.TIME_RATE_CONTROL;
	

	private ContentManager manager = (ContentManager) getContentManager();
	// This agent "speaks" the SL language
	private Codec codec = new SLCodec();

	// This agent "knows" the PlatDominiumOntology()
	// private Ontology ontology = PlatDominiumOntology.getInstance();

	protected void setup() {
		
		
		manager.registerLanguage(codec);
		//manager.registerOntology(ontology);
		
		if(PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE){
		addBehaviour(FactoryHandleTimeRateBehavior.getTickerBehaviour(this, timeRateControl));
		}
		//else addBehaviour(new SlaveTimeRateBehaviour(this, timeRateControl));
		
		if(PropertiesLoaderImpl.DEBUG) System.out.println(this.getName() +" alive!!!!!!!!!!\nStarting the simulation time");
		//SimulationClock.getInstance().setSimulationStarted(true);
	}

}
