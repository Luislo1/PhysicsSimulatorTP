package simulator.view;

import java.util.List;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.ForceLaws;

public class MovingAroundBodyGreaterMass implements ForceLaws {
	private double g;
	private Vector2D c;
	
	public MovingAroundBodyGreaterMass(Vector2D c, double g)  { 
		if (g <= 0 || c == null) 
			throw new IllegalArgumentException("Invalid parameters to create MTFP law");
		this.g = g;
		this.c = c;
	}

	@Override
	public void apply(List<Body> bs) {
		
		
	}

}
