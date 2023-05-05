package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingAroundBodyGreaterMass implements ForceLaws {

	private double G, C;
	
	public MovingAroundBodyGreaterMass(double G, double C) {
		if (G <= 0) {
			throw new IllegalArgumentException("Invalid parameters to create Moving around body with greater mass law: G must be a positive value");
		}
		if (C < 0 || C >= 1)
			throw new IllegalArgumentException("Invalid parameters to create Moving around body with greater mass law: C must be a value between 0 and 1");
		this.G = G;
		this.C = C;
	}
	
	@Override
	public void apply(List<Body> bs) { //Me da pereza hacerlo
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
		return "Moving around the body with greater mass with G="+ G + " and C="+ C;
	}
}
