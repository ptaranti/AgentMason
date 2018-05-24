package agentMason.simulationClock.simulationRateControl;


import agentMason.simulationClock.simulationRateControl.controlApproach.*;
import agentMason.util.propertiesAccess.*;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class FactoryHandleTimeRateBehavior {
	
	
	    public static TickerBehaviour getTickerBehaviour (Agent a, long period)  {   
	        if( PropertiesLoaderImpl.APPROACH == null ) return null;   
	        else if( PropertiesLoaderImpl.APPROACH.equals("linear") ) return new HandleTimeRateBehaviorLinear(a, period);
	        else if( PropertiesLoaderImpl.APPROACH.equals("linear2") ) return new HandleTimeRateBehaviorLinear2(a, period);
	        else if( PropertiesLoaderImpl.APPROACH.equals("linear3") ) return new HandleTimeRateBehaviorLinear3(a, period);
		    else if( PropertiesLoaderImpl.APPROACH.equals("sqr1") ) return new HandleTimeRateBehaviorSqr01(a, period);
	        else if( PropertiesLoaderImpl.APPROACH.equals("sqr2") ) return new HandleTimeRateBehaviorSqr02(a, period); 
	        else return null;   
	      
	}   

}
