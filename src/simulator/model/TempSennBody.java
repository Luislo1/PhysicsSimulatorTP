package simulator.model;

import simulator.misc.Vector2D;

public class TempSennBody extends Body {

	public TempSennBody(String id, Vector2D v, Vector2D p, double m, double minT, double maxT, double redF, double incF) {
		// minT(minimum temperature) and maxT(maximum temperature), both must be positive.
		// redF(temperature reduction factor) and incF(temperature increase factor), both are between 0 and 1, both included.
		super(id, gid, v, p, m); // TODO de donde saco el gid??
		if (minT <= 0 || maxT <= 0 || redF < 0 || redF > 1 || incF < 0 || incF > 1) 
			throw new IllegalArgumentException("Invalid parameters to create MTFP law");
		this.g = g;
		this.c = c;
		
	}

	@Override
	void advance(double dt) {
		// TODO Auto-generated method stub
		
	}

}
