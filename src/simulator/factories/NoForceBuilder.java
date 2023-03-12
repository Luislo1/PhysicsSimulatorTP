package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {

	public NoForceBuilder() {
		super("nf", "No force law");
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		return new NoForce();
	}
	@Override
	public JSONObject getInfo() {
		JSONObject info = getInfo(); //TODO 
		//JSONArray ja = new JSONArray();
		//ja.put
		//info.put("data", "G": "the gravitational constant (a number)");
		return getInfo();		
	}

}
