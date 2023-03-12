package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body {
	
	public MovingBody(String id, String gid, Vector2D p, Vector2D v, double m)  {
		super(id, gid, v, p, m);
	}

	@Override
	void advance(double dt) {
		Vector2D a;
		if (m == 0) {
			a = new Vector2D();
		}
		else
			a = f.scale((1/m));
		
		p = p.plus(v.scale(dt));
		p = p.plus(a.scale(0.5 * dt*dt));
		v = v.plus(a.scale(dt));
	}

}
