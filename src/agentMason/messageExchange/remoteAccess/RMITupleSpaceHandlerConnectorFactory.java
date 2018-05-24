package agentMason.messageExchange.remoteAccess;

import agentMason.messageExchange.TupleSpaceHandler;

public class RMITupleSpaceHandlerConnectorFactory extends
		TupleSpaceHandlerConnectorAbstractFactory {

	@Override
	public TupleSpaceHandler getTupleSpaceHandlerConnection() {
		return (TupleSpaceHandler) new RMITupleSpaceHandlerClient();
	}

}


