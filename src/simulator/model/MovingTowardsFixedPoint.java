package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	double g;
	Vector2D c;
	
	
	public MovingTowardsFixedPoint(Vector2D c, double g)  { 
		if (g <= 0 || c == null) 
			throw new IllegalArgumentException("Invalid parameters to create MTFP law");
		this.g = g;
		this.c = c;
	}
	
	@Override
	public void apply(List<Body> bs) {
		Vector2D d = new Vector2D();
		for (Body bodyi : bs) {
			d = c.minus(bodyi.getPosition()).direction();
			bodyi.addForce(d.scale(bodyi.getMass() * g));
		}
	}

}
