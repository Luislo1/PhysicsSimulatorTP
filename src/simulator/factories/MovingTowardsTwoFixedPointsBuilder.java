package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;
import simulator.model.MovingTowardsTwoFixedPoints;

public class MovingTowardsTwoFixedPointsBuilder extends Builder<ForceLaws> {

	public MovingTowardsTwoFixedPointsBuilder() {
		super("mt2fp", " Moving towards two fixed points");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void fillInData(JSONObject data) {
		data.put("c1", "the first point towards bodies move (a json list of 2 numbers, e.g., [100.0,50.0])")
		.put("c2", "the second point towards bodies move (a json list of 2 numbers, e.g., [100.0,50.0])")
		.put("g1", "the length of the first acceleration vector (a number)")
		.put("g2", "the length of the second acceleration vector (a number)");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		Vector2D c1 = new Vector2D();
		Vector2D c2 = new Vector2D();
		Double g1 = 9.81; // TODO con que valores lo has inicializado?
		Double g2 = 9.81;
		if(data.has("c1")) {
			JSONArray c = data.getJSONArray("c1");
			if (c.length() != 2)
				throw new IllegalArgumentException("Invalid amount of vector point parameters");
			c1 = new Vector2D(c.getDouble(0), c.getDouble(1));
		}
		if(data.has("c2")) {
			JSONArray c = data.getJSONArray("c2");
			if (c.length() != 2)
				throw new IllegalArgumentException("Invalid amount of vector point parameters");
			c2 = new Vector2D(c.getDouble(0), c.getDouble(1));
		}
		
		if (data.has("g1")) {
			g1 = data.getDouble("g1");
		}
		if (data.has("g2")) {
			g2 = data.getDouble("g2");
		}

		return new MovingTowardsTwoFixedPoints(c1, c2, g1, g2); // default values c1 = [0, 0], c2 = [0, 0], g1 = 9.81, g2 = 9.82.
	}

}
