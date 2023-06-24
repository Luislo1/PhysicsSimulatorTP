package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

	private double G;
	
	public NewtonUniversalGravitation(double G) {
		if (G <= 0) {
			throw new IllegalArgumentException("Invalid parameters to create Newton Gravitation Law: G must be a positive value");
		}
		this.G = G;
	}
	
	@Override
	public void apply(List<Body> bs) { //Force applied by each j on i. j != i 
		double newF = 0;
		double distance = 0;
		Vector2D direction = new Vector2D();
		for (Body bodyi : bs) {
			for (Body bodyj : bs) {
				newF = 0;
			 if (bodyi != bodyj) {
				 direction = bodyj.getPosition().minus(bodyi.getPosition()).direction();
				 if ((distance = (Math.pow(bodyj.getPosition().distanceTo(bodyi.getPosition()), 2))) > 0) {
					 newF = G*((bodyj.getMass() * bodyi.getMass())/distance);
				 }
				 bodyi.addForce(direction.scale(newF));
			 }
			}
		}
	}
	public String toString() {
		return "Newton's Universal Gravitation with G="+ G;
	}
}
