package simulator.model;

import java.util.HashMap;
import java.util.Map;

import simulator.control.Controller;

public class NorthTurns implements SimulatorObserver {

	private Map<String, Integer> _numberOfTurns;
	private Map<String, Boolean> _pointingNorth;

	public NorthTurns(Controller ctrl) {
		ctrl.addObserver(this);
		_numberOfTurns = new HashMap<>();
		_pointingNorth = new HashMap<>();
	}

	public void printStatistics() {
		System.out.println("Turn to North Statistics:");
		for (Map.Entry<String, Integer> entry : _numberOfTurns.entrySet())
			System.out.println(entry.getKey() + " => " + String.valueOf(entry.getValue()));
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				String id = b.getId() + ":" + b.getgId();
				Double velY = b.getVelocity().getY();
				if (velY > 0 && _pointingNorth.get(id)) {
					_pointingNorth.put(id, false);
					int newAmount = _numberOfTurns.get(id);
					newAmount++;
					_numberOfTurns.put(id, newAmount);
				} else if (velY < 0) {
					_pointingNorth.put(id, true);
				}
			}
		}
	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_numberOfTurns.clear();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				String id = b.getId() + ":" + b.getgId();
				boolean pointsNorth = false;
				_numberOfTurns.put(id, 0);
				if (b.getVelocity().getY() < 0) {
					pointsNorth = true;
				}
				_pointingNorth.put(id, pointsNorth);
			}
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		String id = b.getId() + ":" + b.getgId();
		boolean pointsNorth = false;
		_numberOfTurns.put(id, 0);
		if (b.getVelocity().getY() < 0) {
			pointsNorth = true;
		}
		_pointingNorth.put(id, pointsNorth);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

}
