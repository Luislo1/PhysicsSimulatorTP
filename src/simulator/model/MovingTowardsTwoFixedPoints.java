package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsTwoFixedPoints implements ForceLaws {
	double g1;
	Vector2D c1;
	double g2;
	Vector2D c2;
	
	
	public MovingTowardsTwoFixedPoints(Vector2D c1, Vector2D c2,  double g1, double g2)  { 
		if (g1 <= 0 || g2 <= 0 || c1 == null || c2 == null) 
			throw new IllegalArgumentException("Invalid parameters to create MTFP law");
		this.g1 = g1;
		this.c1 = c1;
		this.g2 = g2;
		this.c2 = c2;
	}
	
	@Override
	public void apply(List<Body> bs) {
		Vector2D d1 = new Vector2D();
		Vector2D d2 = new Vector2D();
		Vector2D gd1 = new Vector2D();
		Vector2D gd2 = new Vector2D();
		
		for (Body bodyi : bs) {
			d1 = c1.minus(bodyi.getPosition()).direction();
			d2 = c2.minus(bodyi.getPosition()).direction();
			gd1 = d1.scale(g1);
			gd2 = d2.scale(g2);
			
			bodyi.addForce((gd1.plus(gd2)).scale(bodyi.getMass()));
		}
	}
	
	public String toString() {
		return "Moving towards "+c1+" and "+c2+" with constant acceleration "+g1+ " and "+g2;
	}

}
