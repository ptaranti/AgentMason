package agentMason.globalRepresentation.remoteAccess.test;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;
import java.util.Set;

import sim.util.Double2D;

import com.vividsolutions.jts.geom.Coordinate;

import agentMason.globalRepresentation.AgentPosition;
import agentMason.globalRepresentation.VirtualSpaceReadOperations;
import agentMason.globalRepresentation.VirtualSpaceWriteOperations;
import agentMason.globalRepresentation.remoteAccess.GlobalRepresentationConnectorFactoryProducer;
import agentMason.globalRepresentation.remoteAccess.RMIVirtualSpaceReadOperations;
import agentMason.globalRepresentation.remoteAccess.RMIVirtualSpaceReadOperationsClient;
import agentMason.globalRepresentation.remoteAccess.RMIVirtualSpaceWriteOperations;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class RemoteAccessClientGlobalRepresentationTest {

	long oldSimulationTime = 0;
	private static final String HOST = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_IP;
	private static final int PORT = PropertiesLoaderImpl.MAIN_SIMULATION_NODE_PORT;
	private static Registry registry;
	static AgentPosition agentPosition11;
	static AgentPosition agentPosition12;
	static AgentPosition agentPosition21;
	static AgentPosition agentPosition22;

	public static void main(String[] args) throws Exception {

		agentPosition11 = new AgentPosition(new Coordinate(0, 0), 10, "ship01",
				250, 25);
		agentPosition12 = new AgentPosition(new Coordinate(0, 0), 40, "ship01",
				250, 25);
		agentPosition21 = new AgentPosition(new Coordinate(2, 2), 30, "ship02",
				250, 25);
		agentPosition22 = new AgentPosition(new Coordinate(2, 2), 39, "ship02",
				250, 25);

		registry = LocateRegistry.getRegistry(HOST, PORT);

		System.out.println(registry.toString() + " " + registry);

		RMIVirtualSpaceReadOperations remoteRMIVirtualSpaceReadOperations = (RMIVirtualSpaceReadOperations) registry
				.lookup(RMIVirtualSpaceReadOperations.class.getSimpleName());

		RMIVirtualSpaceWriteOperations remoteRMIVirtualSpaceWriteOperations = (RMIVirtualSpaceWriteOperations) registry
				.lookup(RMIVirtualSpaceWriteOperations.class.getSimpleName());

		remoteRMIVirtualSpaceWriteOperations
				.createAgentPosition(agentPosition11);
		remoteRMIVirtualSpaceWriteOperations
				.createAgentPosition(agentPosition21);
		remoteRMIVirtualSpaceWriteOperations
				.updateAgentPosition(agentPosition12);
		remoteRMIVirtualSpaceWriteOperations
				.updateAgentPosition(agentPosition22);

		System.out.println(remoteRMIVirtualSpaceWriteOperations
				.getSurfacePicture(0));

		System.out.println(remoteRMIVirtualSpaceWriteOperations
				.getSurfacePicture(1000));

		System.out.println(agentPosition12);
		System.out.println(agentPosition22);
		System.out.println("distance from ship1 to 2:  "
				+ agentPosition12.getCoordinates().distance(
						agentPosition22.getCoordinates()));

		Hashtable<String, AgentPosition> agentsIntoRange = remoteRMIVirtualSpaceReadOperations
				.getAgentsIntoRange(agentPosition12, 180.0);

		Set<String> keys = agentsIntoRange.keySet();

		for (String key : keys) {
			AgentPosition ap = agentsIntoRange.get(key);
			System.out.println("unit " + agentPosition12.getAgentId()
					+ " has detected " + ap);
		}

		// ////////////////////
		VirtualSpaceReadOperations virtualSpaceReadOperations = GlobalRepresentationConnectorFactoryProducer
				.getGlobalRepresentationConnector()
				.getVirtualSpaceReadOperationsConnection();

		VirtualSpaceWriteOperations virtualSpaceWriteOperations = GlobalRepresentationConnectorFactoryProducer
				.getGlobalRepresentationConnector()
				.getVirtualSpaceWriteOperationsConnection();

		agentsIntoRange = virtualSpaceReadOperations.getAgentsIntoRange(
				agentPosition12, 180.0);

		keys = agentsIntoRange.keySet();
		for (String key : keys) {
			AgentPosition ap = agentsIntoRange.get(key);
			System.out.println("SECON unit " + agentPosition12.getAgentId()
					+ " has detected " + ap);

		}
		// /////////////////////

		VirtualSpaceReadOperations virtualSpaceReadOperations2 = (VirtualSpaceReadOperations) new RMIVirtualSpaceReadOperationsClient();
		agentsIntoRange = virtualSpaceReadOperations2.getAgentsIntoRange(
				agentPosition12, 180.0);

		keys = agentsIntoRange.keySet();
		for (String key : keys) {
			AgentPosition ap = agentsIntoRange.get(key);
			System.out.println("Third unit " + agentPosition12.getAgentId()
					+ " has detected " + ap);

		}
		// //////////////////////
		long time = System.currentTimeMillis();
		long finalTime = time + 1000;
		long i = 1;
		do {
			i++;
			remoteRMIVirtualSpaceWriteOperations
					.updateAgentPosition(agentPosition12);
			System.out.println("Executando...");
		} while (System.currentTimeMillis() < finalTime);

		remoteRMIVirtualSpaceWriteOperations
				.deleteAgentPosition(agentPosition22);
		System.out.println(i / 10 + "informations per second");

	}

}
