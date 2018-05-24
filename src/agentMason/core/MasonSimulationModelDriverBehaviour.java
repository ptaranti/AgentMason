package agentMason.core;



public class MasonSimulationModelDriverBehaviour extends
		SimulationBehaviour {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AgentMason agentMason;
	//private long lastSimulationTime = 0;

	public MasonSimulationModelDriverBehaviour(AgentMason a) {
		super(a);
		//System.out.println("DEV scheduler from  " + myAgent.getLocalName() + "was initialized");
		agentMason = a;
		agentMason.state.setJob(1);
		agentMason.state.start();

	}

	@Override
	protected void onTick() {

		long simulationTime = 0;
		
		simulationTime = simulationClock.getSimulationTime();
	
		System.out.println("\n\n\n"+ myAgent + " XXXXXXXXXXXXXXXXXXXXXXX  caling mason schedule - the simulaion clock is " + simulationTime);
		System.out.println("super.simulatedTimePeriod = "+super.simulatedTimePeriod);
		
		double discretScheduleTime = agentMason.state.schedule.getTime();
		if (discretScheduleTime > simulationTime ) discretScheduleTime =0;
		System.out.println(agentMason.getLocalName() +" discret schedulle with time "+ discretScheduleTime);
		while (simulationTime > discretScheduleTime) {
			agentMason.state.schedule.step(agentMason.state);
			discretScheduleTime = agentMason.state.schedule.getTime();
			System.out.println(agentMason.getLocalName() +" discret schedulle with time "+ discretScheduleTime);
			
		}
		//lastSimulationTime = simulationTime;

	}

	@Override
	public boolean done() {
		// TODO Auto-generated method stub
		return false;
	}

}
