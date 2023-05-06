package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;
import simulator.model.TempSensBody;

public class TempSensBodyBuilder extends Builder<Body> {

	public TempSensBodyBuilder() {
		super("tempsens", "Temp Sens body");
	}

	@Override
	protected Body createInstance(JSONObject data) {
		String id = null;
		String gid = null;
		JSONArray p = null;
		JSONArray v = null;
		Double m = 0.0, minT = 1e5, maxT = 10e5, redF = 0.3, incF = 10e-5;

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
		if (minT < 0 || maxT < 0)
			throw new IllegalArgumentException("Temperature values must be positive");
		if (redF > 1 || redF < 0) 
			throw new IllegalArgumentException("Temperature reduction factor must be between 0 and 1");
		if (incF > 1 || incF < 0) 
			throw new IllegalArgumentException("Temperature increase factor must be between 0 and 1");
		
		
		return new TempSensBody(id, gid, p1, v1, m, minT, maxT, redF, incF);
	}

	@Override
	public void fillInData(JSONObject data) {

	}

}
