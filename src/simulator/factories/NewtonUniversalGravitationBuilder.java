package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {

	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Newton Universal Gravitation force law");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		double G = 6.67E-11;
		if (data.has("G"))
			G = data.getDouble("G");
		return new NewtonUniversalGravitation(G);
	}
	@Override
	public JSONObject getInfo() {
		JSONObject info = super.getInfo(); //TODO 
		JSONArray ja = new JSONArray();
		JSONObject jo1 = new JSONObject();
		jo1.put("G", "the gravitational constant (a number)");
		ja.put(jo1);
		info.put("data", ja);
		return info;		
	}
}
