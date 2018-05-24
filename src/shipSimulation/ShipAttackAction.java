package shipSimulation;

import java.util.Iterator;

import agentMason.globalRepresentation.AgentPosition;
import agentMason.messageExchange.MessageTuple;
import agentMason.messageExchange.TupleSpaceHandler;
import agentMason.messageExchange.remoteAccess.TupleSpaceHandlerConnectorFactoryProducer;
import shipSimulation.messages.AttackMessage;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.util.Bag;

import util.GeometryExtra;

public class ShipAttackAction  implements Steppable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	double weponeryRange = 10;
	
	TupleSpaceHandler tupleSpaceHandler = TupleSpaceHandlerConnectorFactoryProducer
			.getTupleSpaceHandlerConnector().getTupleSpaceHandlerConnection();

	public ShipAttackAction() {

	}

	public void step(SimState state) {

		UnitState unitState = (UnitState) state;
		
		

		System.out.println("SUBMARINE VERIFING TARGETS");


		Bag availableTargets = unitState.yard.getNeighborsWithinDistance(GeometryExtra
				.coordinateToDouble2D(unitState.agentPosition.getCoordinates()),
				weponeryRange);
		if (availableTargets.numObjs < 1) {
			System.out.println(unitState.unitName + " HAVE NO TARGUETS");
			return;
		}

		// System.out.println(yard.allObjects.toString());

		AgentPosition nearestAgentPosition = null;
		double nearDistance = 60;
		for (Iterator availableTargetsIterator = availableTargets.iterator(); availableTargetsIterator
				.hasNext();) {

			// System.out.println(availableTargetsIterator.next().getClass().getName());

			AgentPosition targetAgentPosition = (AgentPosition) availableTargetsIterator
					.next();
			if (targetAgentPosition.getAgentId() == "HE")
				return;
			System.out.print(unitState.unitName +" HAVE TARGUET:   ");
			System.out.println(targetAgentPosition);
			double distanceFromTarget = targetAgentPosition.getCoordinates()
					.distance(unitState.agentPosition.getCoordinates());
			if (targetAgentPosition.getAgentId() == (unitState.unitName)
					&& distanceFromTarget < nearDistance) {
				nearestAgentPosition = targetAgentPosition;
				nearDistance = distanceFromTarget;
			}
		}

		if (nearDistance < 10 && nearestAgentPosition != null) {

			System.out
					.println("\n\n\n\n SUBMARINE ATTACKING - TORPEDO ON TARGET "
							+ nearestAgentPosition.getAgentId());

			double damageLikehood = Math.random();

			if (Math.random() > 0.6) {

				damageLikehood = 1;
				System.out.println("TARGET DESTROYED");
			} else
				System.out.println("TARGET MISSED");

			// AttackMessage(String attaker, String target, double result)
			AttackMessage attackMessage = new AttackMessage(unitState.unitName,
					nearestAgentPosition.getAgentId(), damageLikehood);
			// MessageTuple(String sender, String receiver, Class<?>
			// messageType, Object message,long simuationTime)
			MessageTuple messageTuple = new MessageTuple(unitState.unitName,
					nearestAgentPosition.getAgentId(),
					AttackMessage.class.getName(), (Object) attackMessage,
					unitState.agentPosition.getSimulationTime());

			tupleSpaceHandler.sendMessage(messageTuple);
		}

		//subState.torpedo--;
		//System.out.println(subState.torpedo + " Torpedos still avaiable");
		System.out.println("\n\n\n\n");

	}
}