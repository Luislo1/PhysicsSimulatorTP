package simulator.model;

import simulator.misc.Vector2D;

public class TempSennBody extends Body {

	public TempSennBody(String id, Vector2D v, Vector2D p, double m, double minT, double maxT, double redF, double incF) {
		// minT(minimum temperature) and maxT(maximum temperature), both must be positive.
		// redF(temperature reduction factor) and incF(temperature increase factor), both are between 0 and 1.
		super(id, gid, v, p, m);
		// TODO Auto-generated constructor stub
	}

	@Override
	void advance(double dt) {
		// TODO Auto-generated method stub
		
	}

}
