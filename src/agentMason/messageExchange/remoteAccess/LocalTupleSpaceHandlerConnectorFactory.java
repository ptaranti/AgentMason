package agentMason.messageExchange.remoteAccess;

import agentMason.messageExchange.TupleSpaceHandler;
import agentMason.messageExchange.TupleSpaceHandlerImp;

public class LocalTupleSpaceHandlerConnectorFactory extends
		TupleSpaceHandlerConnectorAbstractFactory {

	@Override
	public TupleSpaceHandler getTupleSpaceHandlerConnection() {
		return TupleSpaceHandlerImp.getInstance();
		
	}

}



