package shipSimulation.messages;

import java.io.Serializable;

import agentMason.globalRepresentation.AgentPosition;

public class DetectionMessage implements Serializable{
	
		private AgentPosition origin;
		private AgentPosition target;
		
		
		
		public DetectionMessage(AgentPosition origin, AgentPosition target) {
			super();
			this.origin = origin;
			this.target = target;
			
			
		}
		public AgentPosition getOrigin() {
			return origin;
		}
		public void setOrigin(AgentPosition origin) {
			this.origin = origin;
		}
		public AgentPosition getTarget() {
			return target;
		}
		public void setTarget(AgentPosition target) {
			this.target = target;
		}
		
		}
