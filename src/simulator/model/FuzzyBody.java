package simulator.model;

import simulator.misc.Vector2D;

public class FuzzyBody extends MovingBody {

	private boolean fuzzy = false;
	private double pr, d;
	private int n, counter = 0;
	private Vector2D initialVelocity = new Vector2D();

	public FuzzyBody(String id, String gid, Vector2D p, Vector2D v, double m, double pr, double d, int n) {
		super(id, gid, v, p, m);
		initialVelocity = v;
		this.pr = pr;
		this.d = d;
		this.n = n;
	}

	@Override
	void advance(double dt) {
		if (!fuzzy) {
			super.advance(dt);
			if (Math.random() < pr) {
				v = initialVelocity;
				fuzzy = true;
				counter = n;
			}
		} else {
			super.advance(dt);
			if (Math.random() < pr) {
				v = v.rotate(1);
			}
			counter--;
			if (counter <= 0) {
				fuzzy = false;
			}

		}
	}

	@Override
	void addForce(Vector2D fNew) {
		if (fuzzy && Math.random() < pr)
			f = f.scale(d);
		super.addForce(fNew);
	}

}
