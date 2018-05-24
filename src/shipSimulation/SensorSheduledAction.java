package shipSimulation;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Hashtable;
import java.util.Set;

import agentMason.globalRepresentation.AgentPosition;
import agentMason.globalRepresentation.VirtualSpaceReadOperations;
import agentMason.globalRepresentation.VirtualSpaceWriteOperations;
import agentMason.globalRepresentation.remoteAccess.GlobalRepresentationConnectorFactoryProducer;
import agentMason.globalRepresentation.remoteAccess.RMIVirtualSpaceReadOperations;
import agentMason.globalRepresentation.remoteAccess.RMIVirtualSpaceWriteOperations;
import agentMason.util.propertiesAccess.PropertiesLoaderImpl;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.continuous.Continuous2D;
import sim.util.Double2D;

public class SensorSheduledAction implements Steppable {

	int range = 5; //degrees =~60NM*5

	VirtualSpaceReadOperations virtualSpaceReadOperations = GlobalRepresentationConnectorFactoryProducer
			.getGlobalRepresentationConnector()
			.getVirtualSpaceReadOperationsConnection();
	
	VirtualSpaceWriteOperations virtualSpaceWriteOperations = GlobalRepresentationConnectorFactoryProducer.getGlobalRepresentationConnector().getVirtualSpaceWriteOperationsConnection();
	
	Hashtable<String, AgentPosition> spatialKnowledge = new Hashtable<String, AgentPosition>();

	public SensorSheduledAction(UnitState shipState){
		super();
		System.out.println(shipState.agentPosition);
		virtualSpaceWriteOperations.createAgentPosition(shipState.agentPosition);
	}
	
	
	@Override
	public void step(SimState state) {

		UnitState unitState = (UnitState) state;
		Continuous2D yard = unitState.yard;
		
	
		virtualSpaceWriteOperations.updateAgentPosition(unitState.agentPosition);
		
		this.spatialKnowledge = virtualSpaceReadOperations.getAgentsIntoRange(
				unitState.agentPosition, this.range);
		
		
	

	//	System.out.println("pppppppppppp   SensorSheduledAction");

		// the code bellow is very simple, since it do not consider the
		// stochastic behavior of the detection , the attenuation and other
				// influences.

		yard.clear();

		Set<String> keys = spatialKnowledge.keySet();
		for (String key : keys) {
			AgentPosition ap = spatialKnowledge.get(key);
			System.out.println( "unit "+ unitState.unitName + " has detected " + ap);
			yard.setObjectLocation(ap,
					new Double2D(ap.getCoordinates().x, ap.getCoordinates().y));
		}
		
		

		unitState.yard = yard;

	}

}
