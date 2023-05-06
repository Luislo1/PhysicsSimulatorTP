package simulator.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class MassLosingTextAreaModel implements SimulatorObserver {

	private Map<String, Double> _massLoss;
	private List<Body> _bodies;

	MassLosingTextAreaModel(Controller ctrl) {
		_massLoss = new HashMap<>();
		_bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}

	public List<Body> getList() {
		return _bodies;
	}

	public Map<String, Double> getMap() {
		return _massLoss;
	}

	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				Double newPos = b.getPosition().magnitude();
				Double sumPos = _massLoss.get(b.getId() + b.getgId());
				_massLoss.put(b.getId() + b.getgId(), sumPos + newPos);
			}
		}

	}

	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_bodies.clear();
		_massLoss.clear();
		// showMassLoss(); Cannot do this anymore, you must press SHOW when you reset
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (Map.Entry<String, BodiesGroup> entry : groups.entrySet()) {
			BodiesGroup value = entry.getValue();
			for (Body b : value) {
				_bodies.add(b);
				_massLoss.put(b.getId() + b.getgId(), 0.0);
			}
		}

	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_bodies.add(b);
		_massLoss.put(b.getId() + b.getgId(), 0.0);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

}
