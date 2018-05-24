package agentMason.globalRepresentation.remoteAccess;

import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class GlobalRepresentationConnectorFactoryProducer {

	public static GlobalRepresentationConnectorAbstractFactory getGlobalRepresentationConnector() {
		
		if(PropertiesLoaderImpl.DEBUG) System.out.println(GlobalRepresentationConnectorFactoryProducer.class + " PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE received with value = "+PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE);
		
		if (PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE) {
			return new LocalVirtualSpaceOperationsConnectorFactory();
		}
		if (!PropertiesLoaderImpl.IS_MAIN_SIMULATION_NODE) {
			return new RMIVirtualSpaceOperationsConnectorFactory();
		}
		return null;
	}
}