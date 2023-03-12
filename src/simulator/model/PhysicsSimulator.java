package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {

	private double dt = 0;
	private double currentTime = 0;
	private ForceLaws forces;
	private Map<String, BodiesGroup> map;
	private List<String> identifiers;
	
	public PhysicsSimulator(double dt, ForceLaws forces) {
		if (dt < 0 || forces == null)
			throw new IllegalArgumentException("Invalid parameters to create Physics Simulator");
		this.dt = dt;
		this.forces = forces;
		map = new HashMap<String, BodiesGroup>();
		identifiers = new ArrayList<String>();
	}
	public void advance() {
		if (dt < 0)
			throw new IllegalArgumentException("Delta time must be a positive value");
		
		for (BodiesGroup bG : map.values()) {
			bG.advance(dt);
		}
		currentTime += dt;
	}
	
	public void addGroup(String id) {
		if (!map.isEmpty() && map.containsKey(id)) 
			throw new IllegalArgumentException("The Physics Simulator already contains a group with this ID");
		
		map.put(id, new BodiesGroup(id, forces));
		identifiers.add(id);
	}
	
	public void addBody(Body b) {
		if (!map.containsKey(b.getgId())) 
			throw new IllegalArgumentException("Invalid ID doesn't match with any in the Physics Simulator");
		
		map.get(b.getgId()).addBody(b);
	}
	
	public void setForceLaws(String id, ForceLaws fl) {
		if (!map.containsKey(id)) 
			throw new IllegalArgumentException("Invalid ID");
		
		map.get(id).setForceLaws(fl);
	}
	
	public JSONObject getState() { 
		JSONObject jo = new JSONObject();
		jo.put("time", currentTime);
		JSONArray ja = new JSONArray();
		for (String ids : identifiers) {
			ja.put(map.get(ids).getState());
		}
		jo.put("groups", ja);
		return jo;
	}
	
	public String toString() {
		return getState().toString();
	}
	
	public void reset() {
		map.clear();
		identifiers.clear();
		currentTime =0; 
	}
}
