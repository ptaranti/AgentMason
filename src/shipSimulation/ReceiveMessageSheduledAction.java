package shipSimulation;

import java.util.List;


import agentMason.globalRepresentation.VirtualSpaceWriteOperations;
import agentMason.globalRepresentation.remoteAccess.GlobalRepresentationConnectorFactoryProducer;
import agentMason.messageExchange.MessageTuple;
import agentMason.messageExchange.TupleSpaceHandler;
import agentMason.messageExchange.remoteAccess.TupleSpaceHandlerConnectorFactoryProducer;
import agentMason.simulationClock.SimulationClock;
import shipSimulation.messages.AttackMessage;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.continuous.Continuous2D;

public class ReceiveMessageSheduledAction implements Steppable {

	static TupleSpaceHandler tupleSpaceHandler = TupleSpaceHandlerConnectorFactoryProducer
			.getTupleSpaceHandlerConnector().getTupleSpaceHandlerConnection();
	static VirtualSpaceWriteOperations virtualSpaceWriteOperationsConector =   GlobalRepresentationConnectorFactoryProducer.getGlobalRepresentationConnector().getVirtualSpaceWriteOperationsConnection();
	static SimulationClock simulationClock = SimulationClock.getInstance();
	UnitState unitState;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void step(SimState state) {

		this.unitState = (UnitState) state;
		//Continuous2D yard = unitState.yard;

		List<MessageTuple> allMessages = tupleSpaceHandler.getAllMessages(null,
				unitState.unitName, AttackMessage.class.getName(),
				simulationClock.getSimulationTime());
		if(allMessages == null) return;
		
		
		for (MessageTuple msg : allMessages) {
			if (msg.getMessageClassName() == AttackMessage.class.getName()) {
				processAttackMessage(msg);
				return;
			}
		}

	}

	private void processAttackMessage(MessageTuple msg) {

		AttackMessage attackMessage = (AttackMessage) msg.getMessage();
		
		System.out.println("RED ALERT  " + attackMessage);
		if( attackMessage.getResult() == 1 ) {
			virtualSpaceWriteOperationsConector.deleteAgentPosition(unitState.agentPosition);
		this.unitState.agentMason.doDelete();
		System.out.println(unitState.unitName + "is sinking -- SOS --SOS --SOS ");
		}
		
	}


}
