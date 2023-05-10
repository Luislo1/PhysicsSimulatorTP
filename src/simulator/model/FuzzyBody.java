package simulator.model;

import simulator.misc.Vector2D;

public class FuzzyBody extends Body{
	private double pr;
	private double d;
	private double n;

	public FuzzyBody(String id, String gid, Vector2D v, Vector2D p, double m, double pr, double d, double n) {
		super(id, gid, v, p, m);
		if(pr < 0 || pr > 1) // Igual con d y con n.
			throw new IllegalArgumentException("One of the parameters of your FuzzyBody is invalid");
		this.pr = pr;
		this.d = d;
		this.n = n;
	}

	@Override
	void advance(double dt) {
		
		
	}
	
	@Override
	void addForce(Vector2D fNew) { //package protected
		//f = f.scale(d);
		// Overridear para que la fuerza en este body se aplique de manera distinta a como se aplica en el resto.
	}

}
