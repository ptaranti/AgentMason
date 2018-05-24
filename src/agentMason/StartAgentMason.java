package agentMason;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.tools.rma.rma;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.ControllerException;

import java.util.List;

import shipSimulation.SubmarineAgent;
import util.StringUtils;
import agentMason.globalRepresentation.GISAccess;
import agentMason.globalRepresentation.remoteAccess.RMIVirtualSpaceReadOperationsServer;
import agentMason.globalRepresentation.remoteAccess.RMIVirtualSpaceWriteOperationsServer;
import agentMason.messageExchange.TupleSpaceHandlerImp;
import agentMason.messageExchange.remoteAccess.RMITupleSpaceHandlerServer;
import agentMason.simulationClock.remoteAccess.RMISimulationTimeServer;
import agentMason.simulationClock.remoteAccess.RMITardinessCollectorServer;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class StartAgentMason {

	private static TupleSpaceHandlerImp tupleSpaceHandler;
	private static ContainerController myMain;

	public static StartAgentMason singleton = null;

	private static Runtime rt;

	static rma remoteAgentManager;

	public static StartAgentMason getInstance() {
		if (singleton == null) {
			singleton = new StartAgentMason();
		}
		return singleton;
	}

	public StartAgentMason() {
		rt = Runtime.instance();
		try {
			createSimulatedEnvironment(rt);
		} catch (ControllerException e) {
			e.printStackTrace();
		}
	}

	public static void createSimulatedEnvironment(Runtime rt)
			throws ControllerException {

		// cleaning the database
		if (PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE) {
			GISAccess.dropDatabaseTables();
			GISAccess.createDatabaseTables();
		}

		// instanciando MainConteiner (JADE)
		Profile m = new ProfileImpl(true);
		m.setParameter("detect-main", "false"); // to avoid a bug in jade 3.5
		m.setParameter("local-host", "127.0.0.1");
		m.setParameter("local-port", PropertiesLoaderImpl.JADE_PORT);
		m.setParameter("port", PropertiesLoaderImpl.JADE_PORT);
		m.setParameter("nomtp", "true");
		myMain = rt.createMainContainer(m);

		AgentController controlAgent;

		// RMA -Remot Monitoring Agent - interface grafica
		if (PropertiesLoaderImpl.RMA) {
			AgentController myRMA = myMain.createNewAgent("RMA",
					"jade.tools.rma.rma", new Object[0]);
			myRMA.start();
		}

		// variable for computing time of charge
		long timeOfCharge = System.currentTimeMillis();

		// System.out.println(util.PropertiesLoaderImpl.UNITS);
		List<String> agentsInThisNode = util.PropertiesLoaderImpl.UNITS;
		
		for (String agentName : agentsInThisNode) {
				if (StringUtils.hasText(agentName)){
				controlAgent = myMain.createNewAgent(agentName,
						"shipSimulation.ShipAgent", new Object[0]);
				controlAgent.start();
				System.out.println("NUR DE NAVOIS "+ agentsInThisNode.size());				
				}
			}
		
		
		/// creating the submarine

		if (PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE){
		controlAgent = myMain.createNewAgent("SubIKL35",
				"shipSimulation.SubmarineAgent", new Object[0]);
		controlAgent.start();
		
		controlAgent = myMain.createNewAgent("SubIKL22",
				"shipSimulation.SubmarineAgent", new Object[0]);
		controlAgent.start();
		
		}

		System.out.println("simulation agents intantiated");

		// load the simulation clock
		
		if (PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE || true) { //changed to always charge

			controlAgent = myMain
					.createNewAgent(
							"timeHandlerAgent",
							"agentMason.simulationClock.simulationRateControl.TimeHandlerAgent",
							new Object[0]);
			controlAgent.start();

			System.out
					.println("The simulation is starting to charge the simulation time advance agent");

			controlAgent = myMain
					.createNewAgent(
							"clockAgent",
							"agentMason.simulationClock.simulationTimeAdvance.ClockAgent",
							new Object[0]);
			controlAgent.start();

			System.out
					.println("The simulation ended to charge time controll agent");
		}
		// load the simulation clock
		
		if (PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE) { //changed to always charge
			
			
		System.out
					.println("The simulation is starting to charge the simulation virtual space manager agent");

			controlAgent = myMain
					.createNewAgent(
							"spatialManagerAgent",
							"agentMason.globalRepresentation.SpatialManagerAgent",
							new Object[0]);
			controlAgent.start();

	

			System.out
					.println("The simulation ended to charge simulation virtual space manager agent");
		}

		System.out.println("The simulation was charged in "
				+ ((System.currentTimeMillis() - timeOfCharge) / 1000)
				+ " seconds");
	}

	public Runtime getRt() {
		return rt;
	}

	public static void main(String[] args) {
		if (PropertiesLoaderImpl.DEBUG)
			System.out.println("debug = " + PropertiesLoaderImpl.DEBUG);
		StartAgentMason.getInstance();

		if (PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE) {
			try {
				tupleSpaceHandler = TupleSpaceHandlerImp.getInstance();
				tupleSpaceHandler.start();
				RMISimulationTimeServer.startRMISimulationTimeServer();
				RMITardinessCollectorServer.startRMITardinessCollectorServer();
				RMIVirtualSpaceWriteOperationsServer
						.startRMIVirtualSpaceWriteOperationsServer();
				RMIVirtualSpaceReadOperationsServer
						.startRMIVirtualSpaceReadOperationsServer();
				RMITupleSpaceHandlerServer.startRMITupleSpaceHandlerServer();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void stopHeating2() {
		// TODO Auto-generated method stub

	}

}
