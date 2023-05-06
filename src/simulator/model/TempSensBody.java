package simulator.model;

import simulator.misc.Vector2D;

public class TempSensBody extends MovingBody {

	private boolean moving = true;
	private double temperature = 0.0, minT, maxT, redF, incF;

	public TempSensBody(String id, String gid, Vector2D p, Vector2D v, double m, double minT, double maxT, double redF,
			double incF) {
		super(id, gid, p, v, m);
		this.minT = minT;
		this.maxT = maxT;
		this.redF = redF;
		this.incF = incF;
	}

	@Override
	void advance(double dt) {
		if (moving) {
			Vector2D prevP = p;
			super.advance(dt);
			temperature += incF * p.distanceTo(prevP);
			if (temperature > maxT) {
				moving = false;
			}
		} else {
			temperature = temperature * (1 - redF);
			if (temperature < minT) {
				moving = true;
			}
		}
	}

}
