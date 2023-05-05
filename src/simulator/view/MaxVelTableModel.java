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
public class MaxVelTableModel extends AbstractTableModel implements SimulatorObserver {

	private String[] _header = { "Body", "Group", "Max Vel" };
	List<Body> _bodies;
	Map<Body, Double> _vel;

	MaxVelTableModel(Controller ctrl) {
		_bodies = new ArrayList<>();
		_vel = new HashMap<>();
		ctrl.addObserver(this);
	}

	public String getColumnName(int col) {
		return _header[col];
	}

	@Override
	public int getRowCount() {
		return _vel.entrySet().size();
	}

	@Override
	public int getColumnCount() {
		return _header.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		Body b = _bodies.get(rowIndex);
		switch (columnIndex) {
		case 0:
			s = b.getId();
			break;
		case 1:
			s = b.getgId();
			break;
		case 2:
			s = String.valueOf(_vel.get(b));
			break;
		default:
			break;
		}
		return s;
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
		_bodies.clear();
	}

	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (BodiesGroup bg : groups.values()) {
			for (Body b : bg) {
				_vel.put(b, b.getVelocity().magnitude());
				_bodies.add(b);
			}
		}
	}

	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
	}

	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		_vel.put(b, b.getVelocity().magnitude());
		_bodies.add(b);
	}

	@Override
	public void onDeltaTimeChanged(double dt) {
	}

	@Override
	public void onForceLawsChanged(BodiesGroup g) {
	}

}
