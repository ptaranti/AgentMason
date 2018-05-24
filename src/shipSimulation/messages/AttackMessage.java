package shipSimulation.messages;

import java.io.Serializable;

public class AttackMessage implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attaker;
	private String target;
	private double result;
	

	public AttackMessage(String attaker, String target, double result) {
		super();
		this.attaker = attaker;
		this.target = target;
		this.result = result;
	}
	public String getAttaker() {
		return attaker;
	}
	public void setAttaker(String attaker) {
		this.attaker = attaker;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
	
	public String toString(){
		return attaker + "lunched weapon on " + target + " with " + result*100 + "% of reach target";
	}
	
}
