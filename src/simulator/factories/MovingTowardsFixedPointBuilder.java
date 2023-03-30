package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder() {
		super("mtfp", "Moving towards fixed point force law");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		Vector2D c1 = new Vector2D();
		Double g = 9.81;
		if (data.has("c")) {
			JSONArray c  = data.getJSONArray("c");
			if (c.length() != 2)
				throw new IllegalArgumentException("Invalid amount of vector point parameters");
			c1 = new Vector2D(c.getDouble(0), c.getDouble(1));
		}
		if (data.has("g")) {
			g = data.getDouble("g");
		}
		return new MovingTowardsFixedPoint(c1, g); //default values c1 = [0,0] g = 9.81. 
	}

	@Override
	public JSONObject getInfo() {
		JSONObject info = super.getInfo(); //TODO 
		JSONArray ja = new JSONArray();
		JSONObject jo1 = new JSONObject();
		jo1.put("c", "the point towards which bodies move (e.g., [100.0, 50.0])");
		ja.put(jo1);
		JSONObject jo2 = new JSONObject();
		jo2.put("g","the length of the acceleration vector (a number)");
		ja.put(jo2);
		info.put("data", ja);
		return info;	
	}
	
}
