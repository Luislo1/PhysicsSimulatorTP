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
		JSONObject info = super.getInfo();
		JSONObject data = new JSONObject();
		info.put("data", data);
		return info;		
	}

	@Override
	public void fillInData(JSONObject data) {
	}

}
