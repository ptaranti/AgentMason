package agentMason.messageExchange.remoteAccess;

import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class TupleSpaceHandlerConnectorFactoryProducer {

	
	
	
	public static TupleSpaceHandlerConnectorAbstractFactory getTupleSpaceHandlerConnector() {
		
		if(PropertiesLoaderImpl.DEBUG) System.out.println(TupleSpaceHandlerConnectorFactoryProducer.class + " PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE received with value = "+PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE);
		
		if (PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE) {
			return new LocalTupleSpaceHandlerConnectorFactory();
		}
		if (!PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE) {
			return new RMITupleSpaceHandlerConnectorFactory();
		}
		return null;
	}
}




	
