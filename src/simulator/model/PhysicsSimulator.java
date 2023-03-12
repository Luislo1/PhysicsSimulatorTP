package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver> {

	private double dt = 0;
	private double currentTime = 0;
	private ForceLaws forces;
	private Map<String, BodiesGroup> map, mapRO;
	private List<String> identifiers;
	private List<SimulatorObserver> observers;

	public PhysicsSimulator(double dt, ForceLaws forces) {
		if (dt <= 0 || forces == null)
			throw new IllegalArgumentException("Invalid parameters to create Physics Simulator");
		this.dt = dt;
		this.forces = forces;
		map = new HashMap<String, BodiesGroup>();
		mapRO = Collections.unmodifiableMap(map);
		identifiers = new ArrayList<String>();
		observers = new ArrayList<SimulatorObserver>();
	}

	public void advance() {
		if (dt <= 0)
			throw new IllegalArgumentException("Delta time must be a positive value");

		for (BodiesGroup bG : map.values()) {
			bG.advance(dt);
		}
		currentTime += dt;

		for (SimulatorObserver sO : observers) {
			sO.onAdvance(map, currentTime);
		}
	}

	public void addGroup(String id) {
		if (!map.isEmpty() && map.containsKey(id))
			throw new IllegalArgumentException("The Physics Simulator already contains a group with this ID");

		BodiesGroup bG = new BodiesGroup(id, forces);
		map.put(id, bG);
		identifiers.add(id);

		for (SimulatorObserver sO : observers) {
			sO.onGroupAdded(map, bG);
		}

	}

	public void addBody(Body b) {
		if (!map.containsKey(b.getgId()))
			throw new IllegalArgumentException("Invalid ID doesn't match with any in the Physics Simulator");

		map.get(b.getgId()).addBody(b);
		for (SimulatorObserver sO : observers) {
			sO.onBodyAdded(map, b);
		}
	}

	public void setForceLaws(String id, ForceLaws fl) {
		if (!map.containsKey(id))
			throw new IllegalArgumentException("Invalid ID");

		map.get(id).setForceLaws(fl);

		for (SimulatorObserver sO : observers) {
			sO.onForceLawsChanged(map.get(id));
		}
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

	public void setDeltaTime(double dt) { // <= 0 as 0 is considered negative number.
		if (dt <= 0)
			throw new IllegalArgumentException("Delta time must be a positive value");
		this.dt = dt;
		for (SimulatorObserver sO : observers) {
			sO.onDeltaTimeChanged(dt);
		}
	}

	public String toString() {
		return getState().toString();
	}

	public void reset() {
		map.clear();
		identifiers.clear();
		currentTime = 0;
		for (SimulatorObserver sO : observers) {
			sO.onReset(map, currentTime, dt);
		}
	}

	@Override
	public void addObserver(SimulatorObserver o) {
		if (!observers.contains(o)) {
			observers.add(o);
		}
		o.onRegister(map, currentTime, dt);
	}

	@Override
	public void removeObserver(SimulatorObserver o) {
		observers.remove(o);
	}
}
