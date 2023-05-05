package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MassLosingBody;
import simulator.model.MovingBody;

public class MassLosingBodyBuilder extends Builder<Body> {

	public MassLosingBodyBuilder() {
		super("ml_body", "Mass losing body");
	}

	@Override
	protected Body createInstance(JSONObject data) {
		String id = null;
		String gid = null;
		JSONArray p = null;
		JSONArray v = null;
		Double m = 0.0;
		double fl = 0.2;

		if (data.has("id")) {
			id = data.getString("id");
		}
		if (id == null)
			throw new IllegalArgumentException("Missing id parameter");

		if (data.has("gid")) {
			gid = data.getString("gid");
		}
		if (gid == null)
			throw new IllegalArgumentException("Missing group id parameter");
		if (data.has("p")) {
			p = data.getJSONArray("p");
		}
		if (p == null)
			throw new IllegalArgumentException("Missing vector position");
		if (p.length() != 2)
			throw new IllegalArgumentException("Invalid amount of vector position parameters");
		Vector2D p1 = new Vector2D(p.getDouble(0), p.getDouble(1));
		if (data.has("v")) {
			v = data.getJSONArray("v");
		}
		if (v == null)
			throw new IllegalArgumentException("Missing vector velocity");
		if (v.length() != 2)
			throw new IllegalArgumentException("Invalid amount of vector velocity parameters");
		Vector2D v1 = new Vector2D(v.getDouble(0), v.getDouble(1));
		if (data.has("m")) {
			m = data.getDouble("m");
		}
		if (m == 0.0)
			throw new IllegalArgumentException("Missing mass parameter");
		
		if (data.has("fl"))
			fl = data.getDouble("fl");
		return new MassLosingBody(id, gid, p1, v1, m, fl);
	}

	@Override
	public void fillInData(JSONObject data) {

	}

}
