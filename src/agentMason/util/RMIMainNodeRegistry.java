

package agentMason.util;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import agentMason.util.propertiesAccess.PropertiesLoaderImpl;

public class RMIMainNodeRegistry {
	
	private static Registry registry;
	private static RMIMainNodeRegistry singleton;
	
	
	private RMIMainNodeRegistry() throws RemoteException{
		
		if(registry == null) registry = java.rmi.registry.LocateRegistry.createRegistry(PropertiesLoaderImpl.MAIN_SIMULATION_NODE_PORT);
	}
	
	public static RMIMainNodeRegistry getInstance() throws RemoteException{
		
		if(singleton == null) return new RMIMainNodeRegistry();
		else return singleton;
		
	}
	
    public void registerObject(String name, Remote remoteObj)
	        throws RemoteException, AlreadyBoundException {
	        registry.bind(name, remoteObj);
	        System.out.println("Registered: " + name + " -> " +
	            remoteObj.getClass().getName() + "[" + remoteObj + "]");
	    }
}
