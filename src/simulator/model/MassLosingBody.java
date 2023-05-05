package simulator.model;

import simulator.misc.Vector2D;

public class MassLosingBody extends MovingBody {
	
	private double _lf;

	public MassLosingBody(String id, String gid, Vector2D p, Vector2D v, double m, double lf)  {
		super(id, gid, v, p, m);
		if (lf < 0 || lf > 1)
			throw new IllegalArgumentException("...");
		_lf = lf; // mass = mass * (1-_lf);
	}

	@Override
	void advance(double dt) {
		super.advance(dt);
		m = m * (1- _lf);
	}

}
