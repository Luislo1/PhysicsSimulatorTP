package simulator.model;

import org.json.JSONObject;

import simulator.misc.Vector2D;

public abstract class Body {
	protected String id;
	protected String gid;
	protected Vector2D v;
	protected Vector2D f;
	protected Vector2D p;
	protected double m;
	
	Body(String id, String gid, Vector2D v, Vector2D p, double m)  {
		if (id == null || gid == null || v == null || p == null) {
			throw new IllegalArgumentException("One of the parameters of your Body is invalid");
		}
		
		if (id.trim().length()<=0 || gid.trim().length()<= 0) {
			throw new IllegalArgumentException("The ID's must not contain spaces");
		}
		if (m <= 0) {
			throw new IllegalArgumentException("The mass of your body can't be a negative value");
		}
		this.id = id;
		this.gid = gid;
		this.v = v;
		this.p = p;
		this.m = m;
		f = new Vector2D();
	}
	public String getId() {
		return id;
	}
	public String getgId() {
		return gid;
	}
	public Vector2D getVelocity() {
		return v;
	}
	public Vector2D getPosition() {
		return p;
	}
	public Vector2D getForce() {
		return f;
	}
	public double getMass() {
		return m;
	}
	void addForce(Vector2D fNew) { //package protected
		f = f.plus(fNew);
	}
	void resetForce() {
		f = new Vector2D();
	}
	abstract void advance(double dt);
	
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("id", id);
		jo.put("m", m);
		jo.put("p", p.asJSONArray());
		jo.put("v", v.asJSONArray());
		jo.put("f", f.asJSONArray());
		return jo;

	}
	public String toString() {
		return getState().toString();
	}
	@Override
	public boolean equals(Object o) {
		Body b = (Body) o;
		return this.id.equals(b.id);
	}
}
