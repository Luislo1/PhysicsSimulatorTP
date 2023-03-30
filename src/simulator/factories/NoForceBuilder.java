package simulator.factories;

import org.json.JSONArray;
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
		JSONObject info = super.getInfo(); //TODO  check that it works properly
		JSONArray ja = new JSONArray();
		info.put("data", ja);
		return info;		
	}

}
