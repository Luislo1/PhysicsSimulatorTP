package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;
import simulator.model.MovingTowardsTwoFixedPoints;

public class MovingTowardsTwoFixedPointsBuilder extends Builder<ForceLaws> {

	public MovingTowardsTwoFixedPointsBuilder() {
		super("mt2fp", "Moving towards two fixed points force law");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		Vector2D c1 = new Vector2D();
		Vector2D c2 = new Vector2D();
		Double g1 = 9.81;
		Double g2 = 9.81;
		if (data.has("c1")) {
			JSONArray cA1 = data.getJSONArray("c1");
			if (cA1.length() != 2)
				throw new IllegalArgumentException("Invalid amount of vector point parameters");
			c1 = new Vector2D(cA1.getDouble(0), cA1.getDouble(1));
		}
		if (data.has("c2")) {
			JSONArray cA2 = data.getJSONArray("c2");
			if (cA2.length() != 2)
				throw new IllegalArgumentException("Invalid amount of vector point parameters");
			c1 = new Vector2D(cA2.getDouble(0), cA2.getDouble(1));
		}
		if (data.has("g1")) {
			g1 = data.getDouble("g1");
		}
		if (data.has("g2")) {
			g2 = data.getDouble("g2");
		}
		return new MovingTowardsTwoFixedPoints(c1, c2, g1, g2); // default values c1 = [0,0] g = 9.81.
	}

	@Override
	public void fillInData(JSONObject data) {
		data.put("c1", "the first point towards which bodies move (e.g., [100.0, 50.0])")
				.put("c2", "the second point towards which bodies move (e.g., [100.0, 50.0])")
				.put("g1", "the length of the first acceleration vector (a number)")
				.put("g2", "the length of the second acceleration vector (a number)");
	}

}
