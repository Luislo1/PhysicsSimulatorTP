package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.MovingAroundBodyGreaterMass;
import simulator.model.NewtonUniversalGravitation;

public class MovingAroundBodyGreaterMassBuilder extends Builder<ForceLaws> {

	public MovingAroundBodyGreaterMassBuilder() {
		super("mabgm", "Moving around the body with greater mass");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		double G = 6.67E-11;
		double C = 0.25;
		if (data.has("G"))
			G = data.getDouble("G");
		if (data.has("C"))
			C = data.getDouble("C");
		return new MovingAroundBodyGreaterMass(G, C);
	}
	
	@Override
	public void fillInData(JSONObject data) {
		data.put("G", "the gravitational constant (a number)").put("C", "the rotation factor (a number)");
	}
}
