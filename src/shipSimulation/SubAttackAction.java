package shipSimulation;

import java.util.Iterator;

import agentMason.globalRepresentation.AgentPosition;
import agentMason.messageExchange.MessageTuple;
import agentMason.messageExchange.TupleSpaceHandler;
import agentMason.messageExchange.remoteAccess.TupleSpaceHandlerConnectorFactoryProducer;
import shipSimulation.messages.AttackMessage;
import sim.engine.SimState;
import sim.engine.Steppable;
import sim.field.continuous.Continuous2D;
import sim.util.Bag;
import util.Attitude;
import util.GeometryExtra;

public class SubAttackAction implements Steppable {

	TupleSpaceHandler tupleSpaceHandler = TupleSpaceHandlerConnectorFactoryProducer
			.getTupleSpaceHandlerConnector().getTupleSpaceHandlerConnection();

	public SubAttackAction() {

	}

	public void step(SimState state) {

		SubmarineState subState = (SubmarineState) state;
		Continuous2D yard = subState.yard;

		System.out.println("SUBMARINE VERIFING TARGETS");

		if (subState.torpedo <= 0) {
			System.out.println("Without Torpedos");
			return;
		}

		Bag availableTargets = yard.getNeighborsWithinDistance(GeometryExtra
				.coordinateToDouble2D(subState.agentPosition.getCoordinates()),
				subState.torpedoRange);
		if (availableTargets.numObjs < 1) {
			System.out.println("SUBMARINE HAVE NO TARGUETS");
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
			System.out.print("SUBMARINE HAVE TARGUET:   ");
			System.out.println(targetAgentPosition);
			double distanceFromTarget = targetAgentPosition.getCoordinates()
					.distance(subState.agentPosition.getCoordinates());
			if (targetAgentPosition.getAgentId() == (subState.unitName)
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

			if (Math.random() > 0.4) {

				damageLikehood = 1;
				System.out.println("TARGET DESTROYED");
			} else
				System.out.println("TARGET MISSED");

			// AttackMessage(String attaker, String target, double result)
			AttackMessage attackMessage = new AttackMessage(subState.unitName,
					nearestAgentPosition.getAgentId(), damageLikehood);
			// MessageTuple(String sender, String receiver, Class<?>
			// messageType, Object message,long simuationTime)
			MessageTuple messageTuple = new MessageTuple(subState.unitName,
					nearestAgentPosition.getAgentId(),
					AttackMessage.class.getName(), (Object) attackMessage,
					subState.agentPosition.getSimulationTime());

			tupleSpaceHandler.sendMessage(messageTuple);
		}

		subState.torpedo--;
		System.out.println(subState.torpedo + " Torpedos still avaiable");
		System.out.println("\n\n\n\n");

	}
}
