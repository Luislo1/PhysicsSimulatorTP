package simulator.launcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class MaxVel implements SimulatorObserver {

	Map<Body, Double> _vel;
	
	public MaxVel() {
		_vel = new HashMap<>();
	}
	
	public void report() {
		for (Entry<Body, Double> e : _vel.entrySet()) {
			System.out.println(e.getKey().getId() + " -> " + e.getValue());
		}
	}
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for (BodiesGroup bg : groups.values()) {
			for (Body b : bg) {
				double currentVelocity = b.getVelocity().magnitude();
				double maxVelocity = _vel.get(b);
				if (currentVelocity > maxVelocity) {
					_vel.put(b, currentVelocity);
				}
			}
		}
		
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {	
		_vel.clear();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (BodiesGroup bg : groups.values() ) {
			for (Body b  : bg) {
				_vel.put(b, b.getVelocity().magnitude());
			}
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_vel.put(b, b.getVelocity().magnitude());
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {	
	}

}
