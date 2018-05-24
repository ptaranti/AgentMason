package agentMason.globalRepresentation.remoteAccess;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.vividsolutions.jts.geom.Coordinate;

import agentMason.globalRepresentation.AgentPosition;
import agentMason.messageExchange.TupleSpaceHandlerImp;
import agentMason.messageExchange.remoteAccess.RMITupleSpaceHandlerServer;
import agentMason.simulationClock.remoteAccess.RMISimulationTimeServer;
import agentMason.simulationClock.remoteAccess.RMITardinessCollectorServer;

public class RMIVirtualSpaceWriteOperationsClientTest {
	
	RMIVirtualSpaceWriteOperationsClient  rMIVirtualSpaceWriteOperationsClient;
	
	TupleSpaceHandlerImp tupleSpaceHandler;
	AgentPosition agentPosition1;
	AgentPosition agentPosition2;	
	AgentPosition agentPosition3;
	AgentPosition agentPosition4;
	
	@Before public void intitializeEnvironment(){
	tupleSpaceHandler = TupleSpaceHandlerImp.getInstance();
	tupleSpaceHandler.start();
	RMISimulationTimeServer.startRMISimulationTimeServer();
	RMITardinessCollectorServer.startRMITardinessCollectorServer();
	RMIVirtualSpaceWriteOperationsServer.startRMIVirtualSpaceWriteOperationsServer();
	RMIVirtualSpaceReadOperationsServer.startRMIVirtualSpaceReadOperationsServer();
	RMITupleSpaceHandlerServer.startRMITupleSpaceHandlerServer();
	
	agentPosition1 = new AgentPosition(new Coordinate(0,0), 10, "ship01", 250, 25);
	agentPosition2 = new AgentPosition(new Coordinate(1,1), 20, "ship01", 250, 25);
	agentPosition3 = new AgentPosition(new Coordinate(2,2), 30, "ship02", 250, 25);
	agentPosition4 = new AgentPosition(new Coordinate(3,3), 40, "ship02", 250, 25);
	
	}

	@Test
	public final void testRMIVirtualSpaceWriteOperationsClient() {
		rMIVirtualSpaceWriteOperationsClient = new RMIVirtualSpaceWriteOperationsClient();
	}
	
	@Test
	public final void testCreateAgentPosition() {
		rMIVirtualSpaceWriteOperationsClient.createAgentPosition(agentPosition1);
	}
	
	@Test
	public final void testUpdateAgentPosition() {
		rMIVirtualSpaceWriteOperationsClient.createAgentPosition(agentPosition3);
		rMIVirtualSpaceWriteOperationsClient.updateAgentPosition(agentPosition4);
	}



	@Test
	public final void testDeleteAgentPosition() {
		rMIVirtualSpaceWriteOperationsClient.deleteAgentPosition(agentPosition1);
	}

	@Test
	public final void testGetSurfacePicture() {
		rMIVirtualSpaceWriteOperationsClient.getSurfacePicture(41);
	}

}
